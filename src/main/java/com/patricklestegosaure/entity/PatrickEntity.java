package com.patricklestegosaure.entity;

import java.util.List;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;

public class PatrickEntity extends PathfinderMob {
	public static final double COMPANION_MAX_HEALTH = 1024.0D;
	private static final float MAX_DAMAGE_PER_HIT = 4.0F;
	private static final double FOLLOW_SEARCH_DISTANCE = 128.0D;
	private static final double TELEPORT_TO_PLAYER_DISTANCE_SQR = 3600.0D;

	public PatrickEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, COMPANION_MAX_HEALTH)
				.add(Attributes.MOVEMENT_SPEED, 0.28)
				.add(Attributes.ATTACK_DAMAGE, 5.0)
				.add(Attributes.FOLLOW_RANGE, FOLLOW_SEARCH_DISTANCE);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.1D, true));
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player.class, 8.0F));
	}

	@Override
	protected void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);
		this.refreshCompanionStats();

		if (this.tickCount % 10 != 0) {
			return;
		}

		if (this.tickCount % 40 == 0 && this.getHealth() < this.getMaxHealth()) {
			this.heal(20.0F);
		}

		LivingEntity boss = findNearestBoss(level);

		if (boss != null) {
			this.setTarget(boss);
			return;
		}

		ServerPlayer player = findNearestPlayer(level);

		if (player != null) {
			double distance = this.distanceToSqr(player);

			if (distance > TELEPORT_TO_PLAYER_DISTANCE_SQR) {
				this.teleportTo(player.getX() + 2.0D, player.getY(), player.getZ() + 2.0D);
				this.getNavigation().stop();
			} else if (distance > 36.0D) {
				this.getNavigation().moveTo(player, 1.12D);
			}
		}
	}

	@Override
	public boolean canAttack(LivingEntity target) {
		return (target instanceof ThierryEntity || target instanceof PascalEntity) && super.canAttack(target);
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
		this.refreshCompanionStats();
		float appliedDamage = Math.min(amount, MAX_DAMAGE_PER_HIT);

		if (this.getHealth() - appliedDamage <= 1.0F) {
			this.setHealth(this.getMaxHealth());
		}

		return super.hurtServer(level, damageSource, appliedDamage);
	}

	public void refreshCompanionStats() {
		AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);

		if (maxHealth != null && maxHealth.getBaseValue() < COMPANION_MAX_HEALTH) {
			maxHealth.setBaseValue(COMPANION_MAX_HEALTH);
			this.setHealth(this.getMaxHealth());
		}
	}

	private LivingEntity findNearestBoss(ServerLevel level) {
		LivingEntity nearest = null;
		double nearestDistance = Double.MAX_VALUE;

		List<? extends ThierryEntity> thierrys = level.getEntities(
				EntityTypeTest.forClass(ThierryEntity.class),
				entity -> entity.isAlive() && !entity.isFriendly() && this.distanceToSqr(entity) < 900.0D
		);

		for (ThierryEntity thierry : thierrys) {
			double distance = this.distanceToSqr(thierry);

			if (distance < nearestDistance) {
				nearest = thierry;
				nearestDistance = distance;
			}
		}

		List<? extends PascalEntity> pascals = level.getEntities(
				EntityTypeTest.forClass(PascalEntity.class),
				entity -> entity.isAlive() && this.distanceToSqr(entity) < 900.0D
		);

		for (PascalEntity pascal : pascals) {
			double distance = this.distanceToSqr(pascal);

			if (distance < nearestDistance) {
				nearest = pascal;
				nearestDistance = distance;
			}
		}

		return nearest;
	}

	private ServerPlayer findNearestPlayer(ServerLevel level) {
		ServerPlayer nearest = null;
		double nearestDistance = FOLLOW_SEARCH_DISTANCE * FOLLOW_SEARCH_DISTANCE;

		for (ServerPlayer player : level.players()) {
			double distance = this.distanceToSqr(player);

			if (distance < nearestDistance) {
				nearest = player;
				nearestDistance = distance;
			}
		}

		return nearest;
	}
}
