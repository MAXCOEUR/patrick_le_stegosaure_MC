package com.patricklestegosaure.entity;

import java.util.List;

import com.patricklestegosaure.world.PatrickWorldEvents;

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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ThierryEntity extends PathfinderMob {
	private static final double HOSTILE_MAX_HEALTH = 420.0D;
	private static final double FRIENDLY_MAX_HEALTH = 1024.0D;
	private static final float FRIENDLY_MAX_DAMAGE_PER_HIT = 4.0F;
	private static final double PHASE_2_HEALTH = 0.66D;
	private static final double PHASE_3_HEALTH = 0.33D;

	private final ServerBossEvent bossEvent;
	private boolean friendly;
	private int phase = 1;
	private int roarCooldown = 120;
	private int slamCooldown = 80;
	private int chargeCooldown = 60;

	public ThierryEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
		this.bossEvent = new ServerBossEvent(
				this.getUUID(),
				Component.literal("Thierry"),
				BossEvent.BossBarColor.RED,
				BossEvent.BossBarOverlay.PROGRESS
		);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, HOSTILE_MAX_HEALTH)
				.add(Attributes.MOVEMENT_SPEED, 0.28)
				.add(Attributes.ATTACK_DAMAGE, 9.0)
				.add(Attributes.FOLLOW_RANGE, 48.0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.05D, true));
		this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 12.0F));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PatrickEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PascalEntity.class, true));
	}

	@Override
	public boolean canAttack(LivingEntity target) {
		if (this.friendly) {
			return target instanceof PascalEntity && super.canAttack(target);
		}

		return !(target instanceof PouetEntity) && !(target instanceof SaucisseEntity) && super.canAttack(target);
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);

		if (!this.friendly) {
			this.bossEvent.addPlayer(player);
		}
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossEvent.removePlayer(player);
	}

	@Override
	protected void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);

		if (this.friendly) {
			this.refreshFriendlyStats();

			if (this.tickCount % 40 == 0 && this.getHealth() < this.getMaxHealth()) {
				this.heal(20.0F);
			}

			this.bossEvent.removeAllPlayers();
		} else {
			this.refreshHostilePhase(level);
			this.tickHostileAbilities(level);
			this.bossEvent.setProgress(Math.max(0.0F, this.getHealth() / this.getMaxHealth()));
		}
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
		if (this.friendly) {
			this.refreshFriendlyStats();
			float appliedDamage = Math.min(amount, FRIENDLY_MAX_DAMAGE_PER_HIT);

			if (this.getHealth() - appliedDamage <= 1.0F) {
				this.setHealth(this.getMaxHealth());
			}

			return super.hurtServer(level, damageSource, appliedDamage);
		}

		return super.hurtServer(level, damageSource, amount);
	}

	@Override
	public void die(DamageSource damageSource) {
		super.die(damageSource);
		this.bossEvent.removeAllPlayers();

		if (!this.friendly && this.level() instanceof ServerLevel serverLevel) {
			PatrickWorldEvents.onThierryDefeated(serverLevel);
		}
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("friendly", this.friendly);
		output.putInt("phase", this.phase);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		this.phase = input.getIntOr("phase", 1);
		this.setFriendly(input.getBooleanOr("friendly", false));
	}

	public boolean isFriendly() {
		return friendly;
	}

	public void setFriendly(boolean friendly) {
		this.friendly = friendly;
		this.bossEvent.setVisible(!friendly);

		if (friendly) {
			this.refreshFriendlyStats();
		}
	}

	private void refreshFriendlyStats() {
		AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);

		if (maxHealth != null && maxHealth.getBaseValue() < FRIENDLY_MAX_HEALTH) {
			maxHealth.setBaseValue(FRIENDLY_MAX_HEALTH);
			this.setHealth(this.getMaxHealth());
		}
	}

	private void refreshHostilePhase(ServerLevel level) {
		AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);

		if (maxHealth != null && maxHealth.getBaseValue() < HOSTILE_MAX_HEALTH) {
			maxHealth.setBaseValue(HOSTILE_MAX_HEALTH);
			this.setHealth(this.getMaxHealth());
		}

		int nextPhase = this.determinePhase();

		if (nextPhase > this.phase) {
			this.phase = nextPhase;
			this.announcePhase(level);
		} else if (this.phase < 1 || this.phase > 3) {
			this.phase = nextPhase;
		}

		this.applyHostilePhaseStats();
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

	private void applyHostilePhaseStats() {
		if (this.phase == 3) {
			setAttributeBase(Attributes.MOVEMENT_SPEED, 0.36D);
			setAttributeBase(Attributes.ATTACK_DAMAGE, 15.0D);
			this.bossEvent.setName(Component.literal("Thierry - Rage finale"));
			this.bossEvent.setColor(BossEvent.BossBarColor.PURPLE);
		} else if (this.phase == 2) {
			setAttributeBase(Attributes.MOVEMENT_SPEED, 0.32D);
			setAttributeBase(Attributes.ATTACK_DAMAGE, 12.0D);
			this.bossEvent.setName(Component.literal("Thierry - Furieux"));
			this.bossEvent.setColor(BossEvent.BossBarColor.RED);
		} else {
			setAttributeBase(Attributes.MOVEMENT_SPEED, 0.28D);
			setAttributeBase(Attributes.ATTACK_DAMAGE, 9.0D);
			this.bossEvent.setName(Component.literal("Thierry"));
			this.bossEvent.setColor(BossEvent.BossBarColor.RED);
		}
	}

	private void tickHostileAbilities(ServerLevel level) {
		if (this.phase >= 2 && --this.roarCooldown <= 0) {
			this.castRoar(level);
			this.roarCooldown = this.phase == 3 ? 100 : 140;
		}

		if (this.phase >= 2 && --this.slamCooldown <= 0) {
			this.castGroundSlam(level);
			this.slamCooldown = this.phase == 3 ? 70 : 110;
		}

		if (this.phase >= 3 && --this.chargeCooldown <= 0) {
			this.castCharge(level);
			this.chargeCooldown = 75;
		}
	}

	private void castRoar(ServerLevel level) {
		level.playSound(null, this.blockPosition(), SoundEvents.RAVAGER_ROAR, SoundSource.HOSTILE, 1.2F, this.phase == 3 ? 0.7F : 0.9F);
		level.sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY() + 2.0D, this.getZ(), 30, 2.0D, 1.2D, 2.0D, 0.05D);

		for (LivingEntity target : getNearbyTargets(level, 10.0D)) {
			target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, this.phase == 3 ? 100 : 70, 0), this);
			target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, this.phase == 3 ? 80 : 50, this.phase == 3 ? 1 : 0), this);
		}
	}

	private void castGroundSlam(ServerLevel level) {
		level.playSound(null, this.blockPosition(), SoundEvents.IRON_GOLEM_ATTACK, SoundSource.HOSTILE, 1.0F, 0.75F);
		level.sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY() + 0.2D, this.getZ(), 55, 4.0D, 0.25D, 4.0D, 0.08D);
		level.sendParticles(ParticleTypes.SWEEP_ATTACK, this.getX(), this.getY() + 1.2D, this.getZ(), 8, 3.0D, 0.6D, 3.0D, 0.02D);

		for (LivingEntity target : getNearbyTargets(level, this.phase == 3 ? 8.0D : 6.5D)) {
			target.hurtServer(level, level.damageSources().mobAttack(this), this.phase == 3 ? 10.0F : 7.0F);
			Vec3 away = target.position().subtract(this.position());

			if (away.lengthSqr() > 0.01D) {
				Vec3 push = away.normalize().scale(this.phase == 3 ? 1.1D : 0.8D);
				target.push(push.x, 0.35D, push.z);
			}
		}
	}

	private void castCharge(ServerLevel level) {
		LivingEntity target = this.getTarget();

		if (target == null || !target.isAlive() || !this.canAttack(target)) {
			target = getNearbyTargets(level, 24.0D).stream().findFirst().orElse(null);
		}

		if (target == null) {
			return;
		}

		Vec3 direction = target.position().subtract(this.position());

		if (direction.lengthSqr() <= 0.01D) {
			return;
		}

		Vec3 dash = direction.normalize().scale(1.35D);
		this.setDeltaMovement(dash.x, 0.12D, dash.z);
		this.hurtMarked = true;
		level.playSound(null, this.blockPosition(), SoundEvents.RAVAGER_ATTACK, SoundSource.HOSTILE, 1.1F, 1.0F);
		level.sendParticles(ParticleTypes.DAMAGE_INDICATOR, this.getX(), this.getY() + 1.0D, this.getZ(), 18, 1.0D, 0.8D, 1.0D, 0.08D);
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
				? "Thierry passe en rage finale !"
				: "Thierry devient furieux !";

		for (ServerPlayer player : level.players()) {
			player.sendSystemMessage(Component.literal(message));
		}

		level.playSound(null, this.blockPosition(), SoundEvents.WITHER_HURT, SoundSource.HOSTILE, 1.0F, this.phase == 3 ? 0.7F : 0.9F);
		level.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + 1.5D, this.getZ(), 12, 1.5D, 1.0D, 1.5D, 0.02D);
	}

	private void setAttributeBase(net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, double value) {
		AttributeInstance instance = this.getAttribute(attribute);

		if (instance != null && instance.getBaseValue() != value) {
			instance.setBaseValue(value);
		}
	}
}
