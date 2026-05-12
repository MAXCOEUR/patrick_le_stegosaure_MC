package com.patricklestegosaure.registry;

import com.patricklestegosaure.PatrickLeStegosaure;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public final class ModDimensions {
	public static final ResourceKey<Level> PATRICK_WORLD = ResourceKey.create(
			Registries.DIMENSION,
			id("patrick_world")
	);

	public static final ResourceKey<Level> PATRICK_HOME = ResourceKey.create(
			Registries.DIMENSION,
			id("patrick_home")
	);

	public static final ResourceKey<DimensionType> PATRICK_WORLD_TYPE = ResourceKey.create(
			Registries.DIMENSION_TYPE,
			id("patrick_world")
	);

	public static final ResourceKey<DimensionType> PATRICK_HOME_TYPE = ResourceKey.create(
			Registries.DIMENSION_TYPE,
			id("patrick_home")
	);

	private ModDimensions() {
	}

	public static void register() {
	}

	private static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(PatrickLeStegosaure.MOD_ID, path);
	}
}
