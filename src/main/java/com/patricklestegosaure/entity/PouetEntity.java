package com.patricklestegosaure.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class PouetEntity extends PathfinderMob {
	public PouetEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10.0)
				.add(Attributes.MOVEMENT_SPEED, 0.20);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player.class, 8.0F));
	}
}
