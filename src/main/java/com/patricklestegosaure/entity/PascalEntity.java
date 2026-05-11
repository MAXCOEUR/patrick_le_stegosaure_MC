package com.patricklestegosaure.entity;

import java.util.List;

import com.patricklestegosaure.world.PatrickWorldEvents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PascalEntity extends PathfinderMob {
	private static final double BOSS_MAX_HEALTH = 760.0D;
	private static final double PHASE_2_HEALTH = 0.70D;
	private static final double PHASE_3_HEALTH = 0.35D;
	private static final int MAX_GUARDS = 6;

	private final ServerBossEvent bossEvent;
	private int phase = 1;
	private int curseCooldown = 90;
	private int trapCooldown = 120;
	private int meteorCooldown = 100;
	private int guardCooldown = 40;

	public PascalEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
		this.bossEvent = new ServerBossEvent(
				this.getUUID(),
				Component.literal("Pascal"),
				BossEvent.BossBarColor.YELLOW,
				BossEvent.BossBarOverlay.PROGRESS
		);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, BOSS_MAX_HEALTH)
				.add(Attributes.MOVEMENT_SPEED, 0.26)
				.add(Attributes.ATTACK_DAMAGE, 11.0)
				.add(Attributes.FOLLOW_RANGE, 54.0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.05D, true));
		this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.75D));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 12.0F));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PatrickEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, ThierryEntity.class, true));
	}

	@Override
	public boolean canAttack(LivingEntity target) {
		return !(target instanceof PouetEntity)
				&& !(target instanceof SaucisseEntity)
				&& !(target instanceof Fox)
				&& super.canAttack(target);
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossEvent.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossEvent.removePlayer(player);
	}

	@Override
	protected void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);
		this.refreshPhase(level);
		this.tickBossAbilities(level);
		this.bossEvent.setProgress(Math.max(0.0F, this.getHealth() / this.getMaxHealth()));
	}

	@Override
	public void die(DamageSource damageSource) {
		super.die(damageSource);
		this.bossEvent.removeAllPlayers();

		if (this.level() instanceof ServerLevel serverLevel) {
			PatrickWorldEvents.onPascalDefeated(serverLevel);
		}
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putInt("phase", this.phase);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		this.phase = input.getIntOr("phase", 1);
	}

	private void refreshPhase(ServerLevel level) {
		AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);

		if (maxHealth != null && maxHealth.getBaseValue() < BOSS_MAX_HEALTH) {
			maxHealth.setBaseValue(BOSS_MAX_HEALTH);
			this.setHealth(this.getMaxHealth());
		}

		int nextPhase = this.determinePhase();

		if (nextPhase > this.phase) {
			this.phase = nextPhase;
			this.announcePhase(level);
			this.castCastleTrap(level);
			this.summonGuards(level, this.phase == 3 ? 3 : 2);
		} else if (this.phase < 1 || this.phase > 3) {
			this.phase = nextPhase;
		}

		this.applyPhaseStats();
	}

	private int determinePhase() {
		float progress = this.getHealth() / this.getMaxHealth();

		if (progress <= PHASE_3_HEALTH) {
			return 3;
		}

		if (progress <= PHASE_2_HEALTH) {
			return 2;
		}

		return 1;
	}

	private void applyPhaseStats() {
		if (this.phase == 3) {
			setAttributeBase(Attributes.MOVEMENT_SPEED, 0.33D);
			setAttributeBase(Attributes.ATTACK_DAMAGE, 18.0D);
			this.bossEvent.setName(Component.literal("Pascal - Roi de la meteorite"));
			this.bossEvent.setColor(BossEvent.BossBarColor.PURPLE);
		} else if (this.phase == 2) {
			setAttributeBase(Attributes.MOVEMENT_SPEED, 0.29D);
			setAttributeBase(Attributes.ATTACK_DAMAGE, 14.0D);
			this.bossEvent.setName(Component.literal("Pascal - Chateau verrouille"));
			this.bossEvent.setColor(BossEvent.BossBarColor.YELLOW);
		} else {
			setAttributeBase(Attributes.MOVEMENT_SPEED, 0.26D);
			setAttributeBase(Attributes.ATTACK_DAMAGE, 11.0D);
			this.bossEvent.setName(Component.literal("Pascal"));
			this.bossEvent.setColor(BossEvent.BossBarColor.YELLOW);
		}
	}

	private void tickBossAbilities(ServerLevel level) {
		if (this.phase >= 2 && --this.curseCooldown <= 0) {
			this.castCastleCurse(level);
			this.curseCooldown = this.phase == 3 ? 90 : 130;
		}

		if (this.phase >= 2 && --this.trapCooldown <= 0) {
			this.castCastleTrap(level);
			this.trapCooldown = this.phase == 3 ? 95 : 150;
		}

		if (this.phase >= 2 && --this.guardCooldown <= 0) {
			this.summonGuards(level, this.phase == 3 ? 2 : 1);
			this.guardCooldown = this.phase == 3 ? 160 : 220;
		}

		if (this.phase >= 3 && --this.meteorCooldown <= 0) {
			this.castMeteorStrike(level);
			this.meteorCooldown = 85;
		}
	}

	private void castCastleCurse(ServerLevel level) {
		level.playSound(null, this.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.HOSTILE, 1.0F, 0.85F);
		level.sendParticles(ParticleTypes.WITCH, this.getX(), this.getY() + 1.7D, this.getZ(), 45, 3.0D, 1.2D, 3.0D, 0.08D);

		for (LivingEntity target : getNearbyTargets(level, 14.0D)) {
			target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, this.phase == 3 ? 80 : 50, 0), this);
			target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, this.phase == 3 ? 100 : 70, this.phase == 3 ? 1 : 0), this);
		}
	}

	private void castCastleTrap(ServerLevel level) {
		level.playSound(null, this.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.HOSTILE, 0.9F, 0.8F);

		int trapCount = this.phase == 3 ? 5 : 3;

		for (int trap = 0; trap < trapCount; trap++) {
			int xOffset = this.getRandom().nextInt(19) - 9;
			int zOffset = this.getRandom().nextInt(19) - 9;
			BlockPos center = this.blockPosition().offset(xOffset, -1, zOffset);

			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					if (Math.abs(x) + Math.abs(z) > 1) {
						continue;
					}

					BlockPos pos = center.offset(x, 0, z);

					if (canBecomeTrap(level, pos)) {
						level.setBlock(pos, Blocks.MAGMA_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
						level.sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 8, 0.25D, 0.1D, 0.25D, 0.03D);
					}
				}
			}
		}
	}

	private void castMeteorStrike(ServerLevel level) {
		LivingEntity target = this.getBestTarget(level, 28.0D);

		if (target == null) {
			return;
		}

		level.playSound(null, target.blockPosition(), SoundEvents.WITHER_SHOOT, SoundSource.HOSTILE, 1.0F, 0.7F);
		level.sendParticles(ParticleTypes.SMOKE, target.getX(), target.getY() + 2.5D, target.getZ(), 35, 1.2D, 1.2D, 1.2D, 0.08D);
		level.sendParticles(ParticleTypes.FLAME, target.getX(), target.getY() + 1.0D, target.getZ(), 30, 1.0D, 0.7D, 1.0D, 0.08D);
		level.explode(this, target.getX(), target.getY(), target.getZ(), 1.6F, false, Level.ExplosionInteraction.NONE);
		target.igniteForSeconds(3.0F);
	}

	private void summonGuards(ServerLevel level, int amount) {
		if (countGuards(level) >= MAX_GUARDS) {
			return;
		}

		LivingEntity target = this.getBestTarget(level, 32.0D);

		for (int index = 0; index < amount && countGuards(level) < MAX_GUARDS; index++) {
			BlockPos pos = this.blockPosition().offset(this.getRandom().nextInt(13) - 6, 0, this.getRandom().nextInt(13) - 6);
			Zombie guard = EntityType.ZOMBIE.spawn(level, pos, net.minecraft.world.entity.EntitySpawnReason.EVENT);

			if (guard != null) {
				guard.setCustomName(Component.literal("Garde de Pascal"));
				guard.setCustomNameVisible(true);
				guard.setPersistenceRequired();
				guard.addEffect(new MobEffectInstance(MobEffects.GLOWING, 160, 0), this);

				if (target != null) {
					guard.setTarget(target);
				}
			}
		}

		level.playSound(null, this.blockPosition(), SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 0.8F, 1.15F);
	}

	private boolean canBecomeTrap(ServerLevel level, BlockPos pos) {
		return level.getBlockState(pos.above()).isAir()
				&& (level.getBlockState(pos).is(Blocks.SMOOTH_STONE)
				|| level.getBlockState(pos).is(Blocks.STONE_BRICKS)
				|| level.getBlockState(pos).is(Blocks.GRASS_BLOCK)
				|| level.getBlockState(pos).is(Blocks.DIRT));
	}

	private int countGuards(ServerLevel level) {
		return level.getEntities(
				EntityTypeTest.forClass(Zombie.class),
				new AABB(this.blockPosition()).inflate(34.0D),
				guard -> guard.isAlive() && guard.hasCustomName() && "Garde de Pascal".equals(guard.getCustomName().getString())
		).size();
	}

	private LivingEntity getBestTarget(ServerLevel level, double radius) {
		LivingEntity target = this.getTarget();

		if (target != null && target.isAlive() && this.canAttack(target) && this.distanceToSqr(target) <= radius * radius) {
			return target;
		}

		List<LivingEntity> targets = getNearbyTargets(level, radius);

		if (targets.isEmpty()) {
			return null;
		}

		return targets.stream()
				.min((first, second) -> Double.compare(this.distanceToSqr(first), this.distanceToSqr(second)))
				.orElse(null);
	}

	private List<LivingEntity> getNearbyTargets(ServerLevel level, double radius) {
		return level.getEntities(
				EntityTypeTest.forClass(LivingEntity.class),
				new AABB(this.blockPosition()).inflate(radius),
				target -> target != this && target.isAlive() && this.canAttack(target)
		);
	}

	private void announcePhase(ServerLevel level) {
		String message = this.phase == 3
				? "Pascal appelle la meteorite du niveau 2 !"
				: "Pascal verrouille le chateau !";

		for (ServerPlayer player : level.players()) {
			player.sendSystemMessage(Component.literal(message));
		}

		level.playSound(null, this.blockPosition(), SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.HOSTILE, 1.0F, this.phase == 3 ? 0.65F : 0.85F);
		level.sendParticles(ParticleTypes.ENCHANTED_HIT, this.getX(), this.getY() + 1.5D, this.getZ(), 30, 2.0D, 1.0D, 2.0D, 0.06D);
	}

	private void setAttributeBase(net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, double value) {
		AttributeInstance instance = this.getAttribute(attribute);

		if (instance != null && instance.getBaseValue() != value) {
			instance.setBaseValue(value);
		}
	}
}
