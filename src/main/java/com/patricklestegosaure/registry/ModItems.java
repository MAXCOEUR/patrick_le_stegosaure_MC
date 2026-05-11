package com.patricklestegosaure.registry;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.item.PatrickKeyItem;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

public final class ModItems {
	public static final Item PATRICK_SPAWN_EGG = register(
			"patrick_spawn_egg",
			new SpawnEggItem(new Item.Properties()
					.spawnEgg(ModEntityTypes.PATRICK)
					.setId(itemKey("patrick_spawn_egg")))
	);

	public static final Item THIERRY_SPAWN_EGG = register(
			"thierry_spawn_egg",
			new SpawnEggItem(new Item.Properties()
					.spawnEgg(ModEntityTypes.THIERRY)
					.setId(itemKey("thierry_spawn_egg")))
	);

	public static final Item SAUCISSE_SPAWN_EGG = spawnEgg("saucisse_spawn_egg", ModEntityTypes.SAUCISSE);
	public static final Item POUET_SPAWN_EGG = spawnEgg("pouet_spawn_egg", ModEntityTypes.POUET);
	public static final Item PASCAL_SPAWN_EGG = spawnEgg("pascal_spawn_egg", ModEntityTypes.PASCAL);
	public static final Item PAMOUK_SPAWN_EGG = spawnEgg("pamouk_spawn_egg", ModEntityTypes.PAMOUK);
	public static final Item KALASH_SPAWN_EGG = spawnEgg("kalash_spawn_egg", ModEntityTypes.KALASH);
	public static final Item BRIGITTE_SPAWN_EGG = spawnEgg("brigitte_spawn_egg", ModEntityTypes.BRIGITTE);

	public static final Item PATRICK_KEY = register(
			"patrick_key",
			new PatrickKeyItem(new Item.Properties().setId(itemKey("patrick_key")))
	);

	public static final Item PATRICK_EPISODE_2_KEY = register(
			"patrick_episode_2_key",
			new PatrickKeyItem(new Item.Properties().setId(itemKey("patrick_episode_2_key")))
	);

	public static final CreativeModeTab PATRICK_TAB = Registry.register(
			BuiltInRegistries.CREATIVE_MODE_TAB,
			id("patrick_tab"),
			CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
					.title(Component.translatable("itemGroup.patrick-le-stegosaure"))
					.icon(() -> new ItemStack(PATRICK_SPAWN_EGG))
					.displayItems((parameters, output) -> {
						output.accept(ModBlocks.PATRICK_PORTAL_FRAME);
						output.accept(PATRICK_KEY);
						output.accept(PATRICK_EPISODE_2_KEY);
						output.accept(PATRICK_SPAWN_EGG);
						output.accept(THIERRY_SPAWN_EGG);
						output.accept(SAUCISSE_SPAWN_EGG);
						output.accept(POUET_SPAWN_EGG);
						output.accept(PASCAL_SPAWN_EGG);
						output.accept(PAMOUK_SPAWN_EGG);
						output.accept(KALASH_SPAWN_EGG);
						output.accept(BRIGITTE_SPAWN_EGG);
					})
					.build()
	);

	private ModItems() {
	}

	public static void register() {
	}

	private static Item register(String path, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, id(path), item);
	}

	private static Item spawnEgg(String path, net.minecraft.world.entity.EntityType<?> entityType) {
		return register(path, new SpawnEggItem(new Item.Properties()
				.spawnEgg(entityType)
				.setId(itemKey(path))));
	}

	private static ResourceKey<Item> itemKey(String path) {
		return ResourceKey.create(Registries.ITEM, id(path));
	}

	private static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(PatrickLeStegosaure.MOD_ID, path);
	}
}
