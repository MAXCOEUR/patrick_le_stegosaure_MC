package com.patricklestegosaure.registry;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.entity.BrigitteEntity;
import com.patricklestegosaure.entity.KalashEntity;
import com.patricklestegosaure.entity.PamoukEntity;
import com.patricklestegosaure.entity.PascalEntity;
import com.patricklestegosaure.entity.PatrickEntity;
import com.patricklestegosaure.entity.PouetEntity;
import com.patricklestegosaure.entity.SaucisseEntity;
import com.patricklestegosaure.entity.ThierryEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntityTypes {
	public static final EntityType<PatrickEntity> PATRICK = register(
			"patrick",
			EntityType.Builder.of(PatrickEntity::new, MobCategory.CREATURE)
					.sized(3.0F, 4.0F)
					.clientTrackingRange(16)
					.build(entityKey("patrick"))
	);

	public static final EntityType<ThierryEntity> THIERRY = register(
			"thierry",
			EntityType.Builder.of(ThierryEntity::new, MobCategory.MONSTER)
					.sized(4.0F, 6.0F)
					.clientTrackingRange(24)
					.notInPeaceful()
					.build(entityKey("thierry"))
	);

	public static final EntityType<SaucisseEntity> SAUCISSE = register(
			"saucisse",
			EntityType.Builder.of(SaucisseEntity::new, MobCategory.CREATURE)
					.sized(0.25F, 0.18F)
					.clientTrackingRange(8)
					.build(entityKey("saucisse"))
	);

	public static final EntityType<PouetEntity> POUET = register(
			"pouet",
			EntityType.Builder.of(PouetEntity::new, MobCategory.CREATURE)
					.sized(0.75F, 1.1F)
					.clientTrackingRange(10)
					.build(entityKey("pouet"))
	);

	public static final EntityType<PascalEntity> PASCAL = register(
			"pascal",
			EntityType.Builder.of(PascalEntity::new, MobCategory.CREATURE)
					.sized(3.5F, 3.2F)
					.clientTrackingRange(20)
					.build(entityKey("pascal"))
	);

	public static final EntityType<PamoukEntity> PAMOUK = register(
			"pamouk",
			EntityType.Builder.of(PamoukEntity::new, MobCategory.CREATURE)
					.sized(3.0F, 1.8F)
					.clientTrackingRange(16)
					.build(entityKey("pamouk"))
	);

	public static final EntityType<KalashEntity> KALASH = register(
			"kalash",
			EntityType.Builder.of(KalashEntity::new, MobCategory.CREATURE)
					.sized(0.7F, 0.55F)
					.clientTrackingRange(8)
					.build(entityKey("kalash"))
	);

	public static final EntityType<BrigitteEntity> BRIGITTE = register(
			"brigitte",
			EntityType.Builder.of(BrigitteEntity::new, MobCategory.CREATURE)
					.sized(0.8F, 1.95F)
					.clientTrackingRange(10)
					.build(entityKey("brigitte"))
	);

	private ModEntityTypes() {
	}

	public static void register() {
		FabricDefaultAttributeRegistry.register(PATRICK, PatrickEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(THIERRY, ThierryEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(SAUCISSE, SaucisseEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(POUET, PouetEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(PASCAL, PascalEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(PAMOUK, PamoukEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(KALASH, KalashEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(BRIGITTE, BrigitteEntity.createAttributes());
	}

	private static <T extends EntityType<?>> T register(String path, T entityType) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, id(path), entityType);
	}

	private static ResourceKey<EntityType<?>> entityKey(String path) {
		return ResourceKey.create(Registries.ENTITY_TYPE, id(path));
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(PatrickLeStegosaure.MOD_ID, path);
	}
}
