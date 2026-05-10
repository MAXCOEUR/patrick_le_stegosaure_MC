package com.patricklestegosaure.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class BrigitteEntity extends PathfinderMob {
	public BrigitteEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
		this.setNoAi(true);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 18.0)
				.add(Attributes.MOVEMENT_SPEED, 0.12);
	}

	@Override
	protected void registerGoals() {
	}
}
