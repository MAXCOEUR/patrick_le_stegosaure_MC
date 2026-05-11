package com.patricklestegosaure.registry;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.block.PatrickPortalBlock;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public final class ModBlocks {
	public static final Block PATRICK_PORTAL_FRAME = registerWithItem(
			"patrick_portal_frame",
			new Block(BlockBehaviour.Properties.of()
					.mapColor(MapColor.COLOR_GREEN)
					.strength(3.5F, 6.0F)
					.sound(SoundType.STONE)
					.setId(blockKey("patrick_portal_frame")))
	);

	public static final Block PATRICK_PORTAL = registerBlock(
			"patrick_portal",
			new PatrickPortalBlock(BlockBehaviour.Properties.of()
					.mapColor(MapColor.COLOR_LIGHT_BLUE)
					.noCollision()
					.noOcclusion()
					.lightLevel(state -> 11)
					.strength(-1.0F, 3600000.0F)
					.noLootTable()
					.setId(blockKey("patrick_portal")))
	);

	private ModBlocks() {
	}

	public static void register() {
	}

	private static Block registerWithItem(String path, Block block) {
		Block registered = registerBlock(path, block);
		Registry.register(
				BuiltInRegistries.ITEM,
				id(path),
				new BlockItem(registered, new Item.Properties().setId(itemKey(path)))
		);
		return registered;
	}

	private static Block registerBlock(String path, Block block) {
		return Registry.register(BuiltInRegistries.BLOCK, id(path), block);
	}

	private static ResourceKey<Block> blockKey(String path) {
		return ResourceKey.create(Registries.BLOCK, id(path));
	}

	private static ResourceKey<Item> itemKey(String path) {
		return ResourceKey.create(Registries.ITEM, id(path));
	}

	private static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(PatrickLeStegosaure.MOD_ID, path);
	}
}
