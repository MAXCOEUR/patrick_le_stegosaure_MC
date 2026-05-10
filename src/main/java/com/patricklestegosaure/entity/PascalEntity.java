package com.patricklestegosaure.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class PascalEntity extends PathfinderMob {
	public PascalEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
		super(entityType, level);
		this.setNoAi(true);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 42.0)
				.add(Attributes.MOVEMENT_SPEED, 0.24);
	}

	@Override
	protected void registerGoals() {
	}
}
