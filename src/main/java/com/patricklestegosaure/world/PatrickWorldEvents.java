package com.patricklestegosaure.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.patricklestegosaure.block.PatrickPortalBlock;
import com.patricklestegosaure.block.PatrickPortalBlock.PortalMode;
import com.patricklestegosaure.entity.PatrickEntity;
import com.patricklestegosaure.entity.PascalEntity;
import com.patricklestegosaure.entity.PouetEntity;
import com.patricklestegosaure.entity.SaucisseEntity;
import com.patricklestegosaure.entity.ThierryEntity;
import com.patricklestegosaure.registry.ModBlocks;
import com.patricklestegosaure.registry.ModDimensions;
import com.patricklestegosaure.registry.ModEntityTypes;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class PatrickWorldEvents {
	public static final int HUB_Y = 72;
	public static final BlockPos HUB_ARRIVAL = new BlockPos(0, HUB_Y, 0);
	public static final BlockPos RETURN_PORTAL_BASE = new BlockPos(-1, HUB_Y, 5);
	public static final BlockPos PATRICK_SPAWN = new BlockPos(-4, HUB_Y, -2);
	public static final BlockPos SAUCISSE_SPAWN = new BlockPos(4, HUB_Y, -2);
	public static final BlockPos ARENA_CENTER = new BlockPos(0, HUB_Y, 50);
	public static final BlockPos THIERRY_SPAWN = new BlockPos(0, HUB_Y, 45);
	public static final BlockPos POUET_CAGE = new BlockPos(0, HUB_Y, 58);
	public static final BlockPos POUET_FREED = new BlockPos(3, HUB_Y, 58);
	public static final BlockPos EPISODE2_ARRIVAL = new BlockPos(160, HUB_Y, 0);
	public static final BlockPos EPISODE2_PATRICK = new BlockPos(156, HUB_Y, 0);
	public static final BlockPos METEORITE_CENTER = new BlockPos(160, HUB_Y, 25);
	public static final BlockPos CASTLE_CENTER = new BlockPos(160, HUB_Y, 82);
	public static final BlockPos PASCAL_SPAWN = new BlockPos(160, HUB_Y, 82);
	public static final BlockPos FRIENDLY_THIERRY_SPAWN = new BlockPos(153, HUB_Y, 82);
	public static final BlockPos FOX_CAGE = new BlockPos(166, HUB_Y, 88);
	public static final BlockPos FOX_FREED = new BlockPos(170, HUB_Y, 88);
	public static final BlockPos HOME_ARRIVAL = new BlockPos(0, HUB_Y, 0);
	public static final BlockPos HOME_PORTAL_BASE = new BlockPos(-1, HUB_Y, -8);
	public static final BlockPos HOME_HOUSE_CENTER = new BlockPos(0, HUB_Y, 18);
	public static final BlockPos HOME_BBQ = new BlockPos(-14, HUB_Y, 8);
	public static final BlockPos HOME_LEVEL_BOARD = new BlockPos(10, HUB_Y, 18);
	public static final BlockPos HOME_PATRICK = new BlockPos(-6, HUB_Y, 6);
	public static final BlockPos HOME_SAUCISSE = new BlockPos(-3, HUB_Y, 5);
	public static final BlockPos HOME_POUET = new BlockPos(3, HUB_Y, 5);
	public static final BlockPos HOME_THIERRY = new BlockPos(8, HUB_Y, 4);
	public static final BlockPos HOME_FOX = new BlockPos(12, HUB_Y, 5);

	private static final AABB ARENA_TRIGGER = new AABB(-12, HUB_Y - 4, 38, 12, HUB_Y + 8, 64);
	private static final AABB EPISODE2_AREA = new AABB(130, HUB_Y - 8, -20, 190, HUB_Y + 12, 112);
	private static final AABB METEORITE_TRIGGER = new AABB(150, HUB_Y - 4, 15, 170, HUB_Y + 8, 35);
	private static final AABB CASTLE_TRIGGER = new AABB(146, HUB_Y - 4, 68, 174, HUB_Y + 8, 96);
	private static final AABB HOME_BOARD_AREA = new AABB(8, HUB_Y - 1, 15, 14, HUB_Y + 4, 22);
	private static final Map<Item, Item> BBQ_RECIPES = Map.of(
			Items.BEEF, Items.COOKED_BEEF,
			Items.PORKCHOP, Items.COOKED_PORKCHOP,
			Items.CHICKEN, Items.COOKED_CHICKEN,
			Items.MUTTON, Items.COOKED_MUTTON,
			Items.RABBIT, Items.COOKED_RABBIT,
			Items.COD, Items.COOKED_COD,
			Items.SALMON, Items.COOKED_SALMON,
			Items.POTATO, Items.BAKED_POTATO
	);

	private PatrickWorldEvents() {
	}

	public static void register() {
		ServerTickEvents.END_LEVEL_TICK.register(PatrickWorldEvents::tickLevel);
		UseBlockCallback.EVENT.register(PatrickWorldEvents::onUseBlock);
	}

	public static void ensureHub(ServerLevel level) {
		if (!level.dimension().equals(ModDimensions.PATRICK_WORLD)) {
			return;
		}

		PatrickWorldState state = PatrickWorldState.get(level.getServer());
		level.getChunkAt(HUB_ARRIVAL);
		buildHub(level);
		ensureFriendlyActors(level, state);
		state.setHubBuilt(true);
	}

	public static void ensureEpisode2(ServerLevel level) {
		if (!level.dimension().equals(ModDimensions.PATRICK_WORLD)) {
			return;
		}

		PatrickWorldState state = PatrickWorldState.get(level.getServer());
		level.getChunkAt(EPISODE2_ARRIVAL);
		buildEpisode2(level, state);
		state.setEpisode2Built(true);
	}

	public static void ensureHome(ServerLevel level) {
		if (!level.dimension().equals(ModDimensions.PATRICK_HOME)) {
			return;
		}

		PatrickWorldState state = PatrickWorldState.get(level.getServer());
		level.getChunkAt(HOME_ARRIVAL);
		buildHome(level, state);
		ensureHomeFriends(level, state);
		state.setHomeBuilt(true);
	}

	public static void onThierryDefeated(ServerLevel level) {
		if (!level.dimension().equals(ModDimensions.PATRICK_WORLD)) {
			return;
		}

		PatrickWorldState state = PatrickWorldState.get(level.getServer());
		state.setArenaStarted(true);
		state.setPouetFreed(true);
		openPouetCage(level);
		ensureFreedPouet(level);
		broadcast(level, "Thierry est KO. Vous avez libere Pouet !");
		playSound(level, POUET_FREED, SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);
		playSound(level, POUET_FREED, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.8F, 1.4F);
	}

	public static void onPascalDefeated(ServerLevel level) {
		if (!level.dimension().equals(ModDimensions.PATRICK_WORLD)) {
			return;
		}

		PatrickWorldState state = PatrickWorldState.get(level.getServer());
		state.setPascalStarted(true);
		state.setPascalDefeated(true);
		state.setFoxFreed(true);
		openFoxCage(level);
		ensureFreedFox(level);
		broadcast(level, "Pascal est KO. Vous avez libere le petit renard !");
		playSound(level, FOX_FREED, SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.2F);
		playSound(level, FOX_FREED, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.8F, 1.5F);
	}

	public static void syncPatrickWithPlayer(ServerPlayer player) {
		ServerLevel targetLevel = player.level();

		if (!targetLevel.dimension().equals(ModDimensions.PATRICK_WORLD)) {
			return;
		}

		BlockPos companionPos = player.blockPosition().offset(2, 0, 2);
		targetLevel.getChunkAt(companionPos);

		PatrickEntity patrick = getClosestPatrick(targetLevel, player);

		if (patrick == null) {
			patrick = movePatrickFromAnotherLevel(targetLevel, companionPos, player.getYRot(), player.getXRot());
		}

		if (patrick == null) {
			patrick = ModEntityTypes.PATRICK.spawn(targetLevel, companionPos, EntitySpawnReason.EVENT);
		}

		if (patrick != null) {
			preparePatrick(patrick);
			patrick.setTarget(null);
			patrick.teleportTo(companionPos.getX() + 0.5D, companionPos.getY(), companionPos.getZ() + 0.5D);
			discardOtherPatricks(targetLevel, patrick);
		}
	}

	private static InteractionResult onUseBlock(Player player, net.minecraft.world.level.Level level, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hitResult) {
		if (level.isClientSide() || !(level instanceof ServerLevel serverLevel) || !(player instanceof ServerPlayer serverPlayer)) {
			return InteractionResult.PASS;
		}

		if (!serverLevel.dimension().equals(ModDimensions.PATRICK_HOME)) {
			return InteractionResult.PASS;
		}

		BlockPos clicked = hitResult.getBlockPos();

		if (clicked.equals(HOME_BBQ)) {
			return handleBbqUse(serverLevel, serverPlayer, hand);
		}

		if (HOME_BOARD_AREA.contains(clicked.getCenter())) {
			sendLevelBoard(serverPlayer, PatrickWorldState.get(serverLevel.getServer()));
			return InteractionResult.SUCCESS_SERVER;
		}

		return InteractionResult.PASS;
	}

	private static InteractionResult handleBbqUse(ServerLevel level, ServerPlayer player, InteractionHand hand) {
		ItemStack held = player.getItemInHand(hand);
		Item cooked = BBQ_RECIPES.get(held.getItem());

		if (cooked == null) {
			player.sendSystemMessage(Component.literal("Le BBQ de Patrick veut de la nourriture crue."));
			return InteractionResult.FAIL;
		}

		if (!player.isCreative()) {
			held.shrink(1);
		}

		ItemStack cookedStack = new ItemStack(cooked);

		if (!player.addItem(cookedStack)) {
			player.drop(cookedStack, false);
		}

		player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 0));
		level.playSound(null, HOME_BBQ, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F);
		level.playSound(null, HOME_BBQ, SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.45F, 1.6F);
		level.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, HOME_BBQ.getX() + 0.5D, HOME_BBQ.getY() + 1.2D, HOME_BBQ.getZ() + 0.5D, 12, 0.3D, 0.25D, 0.3D, 0.01D);
		level.sendParticles(ParticleTypes.FLAME, HOME_BBQ.getX() + 0.5D, HOME_BBQ.getY() + 0.8D, HOME_BBQ.getZ() + 0.5D, 8, 0.2D, 0.1D, 0.2D, 0.02D);
		sendBbqFriendReaction(player, PatrickWorldState.get(level.getServer()));
		return InteractionResult.SUCCESS_SERVER;
	}

	private static void sendBbqFriendReaction(ServerPlayer player, PatrickWorldState state) {
		if (state.isFoxFreed()) {
			player.sendSystemMessage(Component.literal("Le petit renard tourne autour du BBQ. Il a l'air content."));
		} else if (state.isPouetFreed()) {
			player.sendSystemMessage(Component.literal("Pouet profite enfin du BBQ avec Patrick."));
		} else {
			player.sendSystemMessage(Component.literal("Patrick garde une place pour les amis a sauver."));
		}
	}

	private static void sendLevelBoard(ServerPlayer player, PatrickWorldState state) {
		player.sendSystemMessage(Component.literal("Maison de Patrick - niveaux"));
		player.sendSystemMessage(Component.literal("Niveau 1 - Sauver Pouet : " + (state.isPouetFreed() ? "termine" : "disponible")));
		player.sendSystemMessage(Component.literal("Niveau 2 - Meteorite et Pascal : " + (state.isPascalDefeated() ? "termine" : state.isPouetFreed() ? "disponible" : "bloque")));
		player.sendSystemMessage(Component.literal("Niveau 3 - Bientot : bloque"));
	}

	private static void tickLevel(ServerLevel level) {
		if (level.getGameTime() % 20 != 0) {
			return;
		}

		if (level.players().isEmpty()) {
			return;
		}

		PatrickWorldState state = PatrickWorldState.get(level.getServer());

		if (level.dimension().equals(ModDimensions.PATRICK_HOME)) {
			if (!state.isHomeBuilt()) {
				ensureHome(level);
			}

			ensureHomeFriends(level, state);
			return;
		}

		if (!level.dimension().equals(ModDimensions.PATRICK_WORLD)) {
			return;
		}

		if (!state.isHubBuilt()) {
			ensureHub(level);
		}

		ensureFriendlyActors(level, state);
		handleArena(level, state);

		if (state.isEpisode2Built() || hasPlayerIn(level, EPISODE2_AREA)) {
			handleEpisode2(level, state);
		}
	}

	private static void handleArena(ServerLevel level, PatrickWorldState state) {
		if (state.isPouetFreed()) {
			openPouetCage(level);
			ensureFreedPouet(level);
			return;
		}

		boolean playerInArena = !level.getPlayers(player -> ARENA_TRIGGER.contains(player.position())).isEmpty();

		if (!playerInArena) {
			return;
		}

		boolean firstStart = !state.isArenaStarted();
		state.setArenaStarted(true);

		if (!hasThierry(level)) {
			ThierryEntity thierry = ModEntityTypes.THIERRY.spawn(level, THIERRY_SPAWN, EntitySpawnReason.EVENT);

			if (thierry != null) {
				thierry.setPersistenceRequired();
			}
		}

		if (firstStart) {
			broadcast(level, "Un Thierry sauvage apparait !");
			playSound(level, THIERRY_SPAWN, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 0.8F, 1.2F);
		}
	}

	private static void handleEpisode2(ServerLevel level, PatrickWorldState state) {
		if (!state.isEpisode2Built()) {
			ensureEpisode2(level);
		}

		if (hasPlayerIn(level, EPISODE2_AREA)) {
			movePatrickTowardEpisode2(level);
		}

		if (!state.isMeteoriteReturned() && hasPlayerIn(level, METEORITE_TRIGGER)) {
			state.setMeteoriteReturned(true);
			removeMeteorite(level);
			broadcast(level, "Patrick renvoie la meteorite !");
			playSound(level, METEORITE_CENTER, SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1.0F, 0.8F);
			playSound(level, METEORITE_CENTER, SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.BLOCKS, 0.8F, 1.2F);
		}

		if (!state.isFoxFreed()) {
			ensureCaptiveFox(level);
		}

		if (state.isMeteoriteReturned() && !state.isPascalDefeated() && hasPlayerIn(level, CASTLE_TRIGGER)) {
			startPascalFight(level, state);
		}

		PascalEntity pascal = getFirstEntity(level, PascalEntity.class);

		if (pascal != null && pascal.isAlive() && pascal.getHealth() <= pascal.getMaxHealth() * 0.5F && !state.hasThierryHelpedPascalFight()) {
			spawnFriendlyThierry(level);
			state.setThierryHelpedPascalFight(true);
			broadcast(level, "Thierry revient... mais cette fois il aide Patrick !");
			playSound(level, FRIENDLY_THIERRY_SPAWN, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.0F);
		}
	}

	private static void startPascalFight(ServerLevel level, PatrickWorldState state) {
		boolean firstStart = !state.isPascalStarted();
		state.setPascalStarted(true);

		if (!hasEntity(level, PascalEntity.class)) {
			PascalEntity pascal = ModEntityTypes.PASCAL.spawn(level, PASCAL_SPAWN, EntitySpawnReason.EVENT);

			if (pascal != null) {
				pascal.setPersistenceRequired();
			}
		}

		if (firstStart) {
			broadcast(level, "Pascal garde le petit renard dans son chateau !");
			playSound(level, PASCAL_SPAWN, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 0.7F, 1.35F);
		}
	}

	private static void ensureFriendlyActors(ServerLevel level, PatrickWorldState state) {
		List<PatrickEntity> patricks = getEntities(level, PatrickEntity.class);
		PatrickEntity patrick = keepOne(patricks, PATRICK_SPAWN);

		if (patrick == null) {
			PatrickEntity spawnedPatrick = ModEntityTypes.PATRICK.spawn(level, PATRICK_SPAWN, EntitySpawnReason.EVENT);

			if (spawnedPatrick != null) {
				preparePatrick(spawnedPatrick);
			}
		} else {
			preparePatrick(patrick);
		}

		List<SaucisseEntity> saucisses = getEntities(level, SaucisseEntity.class);
		SaucisseEntity saucisse = keepOne(saucisses, SAUCISSE_SPAWN);

		if (saucisse == null) {
			SaucisseEntity spawnedSaucisse = ModEntityTypes.SAUCISSE.spawn(level, SAUCISSE_SPAWN, EntitySpawnReason.EVENT);

			if (spawnedSaucisse != null) {
				spawnedSaucisse.setPersistenceRequired();
			}
		} else {
			saucisse.setPersistenceRequired();
		}

		if (state.isPouetFreed()) {
			ensureFreedPouet(level);
		} else {
			ensureCaptivePouet(level);
		}
	}

	private static void ensureHomeFriends(ServerLevel level, PatrickWorldState state) {
		PatrickEntity patrick = keepOne(getEntities(level, PatrickEntity.class), HOME_PATRICK);

		if (patrick == null) {
			patrick = ModEntityTypes.PATRICK.spawn(level, HOME_PATRICK, EntitySpawnReason.EVENT);
		}

		if (patrick != null) {
			preparePatrick(patrick);
		}

		SaucisseEntity saucisse = keepOne(getEntities(level, SaucisseEntity.class), HOME_SAUCISSE);

		if (saucisse == null) {
			saucisse = ModEntityTypes.SAUCISSE.spawn(level, HOME_SAUCISSE, EntitySpawnReason.EVENT);
		}

		if (saucisse != null) {
			saucisse.setPersistenceRequired();
		}

		if (state.isPouetFreed()) {
			PouetEntity pouet = keepOne(getEntities(level, PouetEntity.class), HOME_POUET);

			if (pouet == null) {
				pouet = ModEntityTypes.POUET.spawn(level, HOME_POUET, EntitySpawnReason.EVENT);
			}

			if (pouet != null) {
				pouet.setNoAi(false);
				pouet.setPersistenceRequired();
			}
		}

		if (state.hasThierryHelpedPascalFight()) {
			ThierryEntity thierry = keepOne(getEntities(level, ThierryEntity.class), HOME_THIERRY);

			if (thierry == null) {
				thierry = ModEntityTypes.THIERRY.spawn(level, HOME_THIERRY, EntitySpawnReason.EVENT);
			}

			if (thierry != null) {
				thierry.setFriendly(true);
				thierry.setPersistenceRequired();
			}
		}

		if (state.isFoxFreed()) {
			Fox fox = keepOne(getEntities(level, Fox.class), HOME_FOX);

			if (fox == null) {
				fox = EntityType.FOX.spawn(level, HOME_FOX, EntitySpawnReason.EVENT);
			}

			if (fox != null) {
				fox.setCustomName(Component.literal("Petit renard"));
				fox.setCustomNameVisible(true);
				fox.setPersistenceRequired();
			}
		}
	}

	private static void ensureCaptivePouet(ServerLevel level) {
		buildPouetCage(level);
		List<PouetEntity> pouets = getEntities(level, PouetEntity.class);
		PouetEntity keptPouet = keepOne(pouets, POUET_CAGE);

		if (keptPouet == null) {
			PouetEntity pouet = ModEntityTypes.POUET.spawn(level, POUET_CAGE, EntitySpawnReason.EVENT);

			if (pouet != null) {
				pouet.setNoAi(true);
				pouet.setPersistenceRequired();
			}

			return;
		}

		keptPouet.setNoAi(true);
		keptPouet.setPersistenceRequired();
		keptPouet.setPos(POUET_CAGE.getX() + 0.5D, POUET_CAGE.getY(), POUET_CAGE.getZ() + 0.5D);
	}

	private static void ensureFreedPouet(ServerLevel level) {
		List<PouetEntity> pouets = getEntities(level, PouetEntity.class);
		PouetEntity keptPouet = keepOne(pouets, POUET_FREED);

		if (keptPouet == null) {
			PouetEntity pouet = ModEntityTypes.POUET.spawn(level, POUET_FREED, EntitySpawnReason.EVENT);

			if (pouet != null) {
				pouet.setPersistenceRequired();
				playSound(level, POUET_FREED, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.NEUTRAL, 1.0F, 1.3F);
			}

			return;
		}

		keptPouet.setNoAi(false);
		keptPouet.setPersistenceRequired();

		if (keptPouet.distanceToSqr(POUET_CAGE.getCenter()) < 16.0D) {
			keptPouet.setPos(POUET_FREED.getX() + 0.5D, POUET_FREED.getY(), POUET_FREED.getZ() + 0.5D);
		}
	}

	private static void ensureCaptiveFox(ServerLevel level) {
		buildFoxCage(level);
		List<Fox> foxes = getEntities(level, Fox.class);
		Fox keptFox = keepOne(foxes, FOX_CAGE);

		if (keptFox == null) {
			Fox fox = EntityType.FOX.spawn(level, FOX_CAGE, EntitySpawnReason.EVENT);

			if (fox != null) {
				fox.setNoAi(true);
				fox.setCustomName(Component.literal("Petit renard"));
				fox.setCustomNameVisible(true);
				fox.setPersistenceRequired();
			}

			return;
		}

		keptFox.setNoAi(true);
		keptFox.setPersistenceRequired();
		keptFox.setPos(FOX_CAGE.getX() + 0.5D, FOX_CAGE.getY(), FOX_CAGE.getZ() + 0.5D);
	}

	private static void ensureFreedFox(ServerLevel level) {
		List<Fox> foxes = getEntities(level, Fox.class);
		Fox keptFox = keepOne(foxes, FOX_FREED);

		if (keptFox == null) {
			Fox fox = EntityType.FOX.spawn(level, FOX_FREED, EntitySpawnReason.EVENT);

			if (fox != null) {
				fox.setCustomName(Component.literal("Petit renard"));
				fox.setCustomNameVisible(true);
				fox.setPersistenceRequired();
				playSound(level, FOX_FREED, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.NEUTRAL, 1.0F, 1.3F);
			}

			return;
		}

		keptFox.setNoAi(false);
		keptFox.setPersistenceRequired();

		if (keptFox.distanceToSqr(FOX_CAGE.getCenter()) < 25.0D) {
			keptFox.setPos(FOX_FREED.getX() + 0.5D, FOX_FREED.getY(), FOX_FREED.getZ() + 0.5D);
		}
	}

	private static void buildHub(ServerLevel level) {
		for (int x = -24; x <= 24; x++) {
			for (int z = -16; z <= 72; z++) {
				BlockPos ground = new BlockPos(x, HUB_Y - 1, z);
				boolean path = Math.abs(x) <= 1 && z >= -6 && z <= 54;
				boolean arena = x * x + (z - ARENA_CENTER.getZ()) * (z - ARENA_CENTER.getZ()) <= 100;
				BlockState top = path || arena ? Blocks.DIRT.defaultBlockState() : Blocks.GRASS_BLOCK.defaultBlockState();

				level.setBlock(ground.below(), Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);
				level.setBlock(ground, top, Block.UPDATE_ALL);

				for (int y = 1; y <= 6; y++) {
					level.setBlock(ground.above(y), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
				}
			}
		}

		buildPortal(level, RETURN_PORTAL_BASE);
		buildSimpleTrees(level);
		buildDenseLevelOneForest(level);
		buildForestArenaDecorations(level);
		buildPouetCage(level);
	}

	private static void buildEpisode2(ServerLevel level, PatrickWorldState state) {
		for (int x = 136; x <= 184; x++) {
			for (int z = -12; z <= 106; z++) {
				BlockPos ground = new BlockPos(x, HUB_Y - 1, z);
				boolean path = Math.abs(x - EPISODE2_ARRIVAL.getX()) <= 2 && z >= -4 && z <= 78;
				BlockState top = path ? Blocks.DIRT.defaultBlockState() : Blocks.GRASS_BLOCK.defaultBlockState();

				level.setBlock(ground.below(), Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);
				level.setBlock(ground, top, Block.UPDATE_ALL);

				for (int y = 1; y <= 7; y++) {
					level.setBlock(ground.above(y), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
				}
			}
		}

		buildPortal(level, new BlockPos(158, HUB_Y, -8));
		buildEpisode2Trees(level);

		if (!state.isMeteoriteReturned()) {
			buildMeteorite(level);
		} else {
			removeMeteorite(level);
		}

		buildCastle(level);

		if (state.isFoxFreed()) {
			openFoxCage(level);
			ensureFreedFox(level);
		} else {
			buildFoxCage(level);
		}
	}

	private static void buildHome(ServerLevel level, PatrickWorldState state) {
		for (int x = -32; x <= 32; x++) {
			for (int z = -18; z <= 42; z++) {
				BlockPos ground = new BlockPos(x, HUB_Y - 1, z);
				boolean path = Math.abs(x) <= 2 && z >= -10 && z <= 20;
				boolean patio = x >= -20 && x <= -8 && z >= 2 && z <= 14;
				BlockState top = path || patio ? Blocks.COARSE_DIRT.defaultBlockState() : Blocks.GRASS_BLOCK.defaultBlockState();

				level.setBlock(ground.below(), Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL);
				level.setBlock(ground, top, Block.UPDATE_ALL);

				for (int y = 1; y <= 8; y++) {
					level.setBlock(ground.above(y), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
				}
			}
		}

		buildPortal(level, HOME_PORTAL_BASE, PortalMode.HOME);
		buildPatrickHouse(level);
		buildBbqArea(level);
		buildLevelBoard(level, state);
		buildHomePrairie(level);
		buildBackgroundMountains(level);
	}

	private static void buildPatrickHouse(ServerLevel level) {
		BlockPos center = HOME_HOUSE_CENTER;

		for (int x = -7; x <= 7; x++) {
			for (int z = -6; z <= 6; z++) {
				BlockPos floor = center.offset(x, -1, z);
				level.setBlock(floor, Blocks.OAK_PLANKS.defaultBlockState(), Block.UPDATE_ALL);

				boolean wall = Math.abs(x) == 7 || Math.abs(z) == 6;
				boolean door = z == -6 && Math.abs(x) <= 1;

				if (wall && !door) {
					for (int y = 0; y <= 4; y++) {
						level.setBlock(center.offset(x, y, z), Blocks.OAK_PLANKS.defaultBlockState(), Block.UPDATE_ALL);
					}
				}
			}
		}

		for (int x = -8; x <= 8; x++) {
			for (int z = -7; z <= 7; z++) {
				if (Math.abs(x) + Math.abs(z) <= 15) {
					level.setBlock(center.offset(x, 5, z), Blocks.RED_TERRACOTTA.defaultBlockState(), Block.UPDATE_ALL);
				}
			}
		}

		for (BlockPos window : new BlockPos[] { center.offset(-7, 2, -2), center.offset(7, 2, -2), center.offset(-3, 2, 6), center.offset(3, 2, 6) }) {
			level.setBlock(window, Blocks.GLASS.defaultBlockState(), Block.UPDATE_ALL);
		}

		level.setBlock(center.offset(0, 0, -6), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
		level.setBlock(center.offset(0, 1, -6), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
		level.setBlock(center.offset(0, 0, -8), Blocks.OAK_PLANKS.defaultBlockState(), Block.UPDATE_ALL);
		level.setBlock(center.offset(-1, 0, -8), Blocks.OAK_FENCE.defaultBlockState(), Block.UPDATE_ALL);
		level.setBlock(center.offset(1, 0, -8), Blocks.OAK_FENCE.defaultBlockState(), Block.UPDATE_ALL);
	}

	private static void buildBbqArea(ServerLevel level) {
		level.setBlock(HOME_BBQ.below(), Blocks.BLACKSTONE.defaultBlockState(), Block.UPDATE_ALL);
		level.setBlock(HOME_BBQ, Blocks.SMOKER.defaultBlockState(), Block.UPDATE_ALL);
		level.setBlock(HOME_BBQ.above(), Blocks.CAMPFIRE.defaultBlockState(), Block.UPDATE_ALL);

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			level.setBlock(HOME_BBQ.relative(direction), Blocks.IRON_BARS.defaultBlockState(), Block.UPDATE_ALL);
		}

		buildPicnicTable(level, new BlockPos(-9, HUB_Y, 8));
		buildPicnicTable(level, new BlockPos(-18, HUB_Y, 13));

		Chicken chicken = getEntities(level, Chicken.class).stream().findFirst().orElse(null);

		if (chicken == null) {
			chicken = EntityType.CHICKEN.spawn(level, new BlockPos(-10, HUB_Y, 11), EntitySpawnReason.EVENT);
		}

		if (chicken != null) {
			chicken.setPersistenceRequired();
		}
	}

	private static void buildPicnicTable(ServerLevel level, BlockPos center) {
		for (int x = -2; x <= 2; x++) {
			level.setBlock(center.offset(x, 0, 0), Blocks.OAK_PLANKS.defaultBlockState(), Block.UPDATE_ALL);
			level.setBlock(center.offset(x, -1, -1), Blocks.OAK_PLANKS.defaultBlockState(), Block.UPDATE_ALL);
			level.setBlock(center.offset(x, -1, 1), Blocks.OAK_PLANKS.defaultBlockState(), Block.UPDATE_ALL);
		}

		level.setBlock(center.offset(-2, -1, 0), Blocks.OAK_FENCE.defaultBlockState(), Block.UPDATE_ALL);
		level.setBlock(center.offset(2, -1, 0), Blocks.OAK_FENCE.defaultBlockState(), Block.UPDATE_ALL);
	}

	private static void buildLevelBoard(ServerLevel level, PatrickWorldState state) {
		for (int x = 0; x <= 4; x++) {
			for (int y = 0; y <= 3; y++) {
				level.setBlock(HOME_LEVEL_BOARD.offset(x, y, 0), Blocks.DARK_OAK_PLANKS.defaultBlockState(), Block.UPDATE_ALL);
			}
		}

		level.setBlock(HOME_LEVEL_BOARD.offset(0, 1, -1), (state.isPouetFreed() ? Blocks.EMERALD_BLOCK : Blocks.GOLD_BLOCK).defaultBlockState(), Block.UPDATE_ALL);
		level.setBlock(HOME_LEVEL_BOARD.offset(2, 1, -1), (state.isPascalDefeated() ? Blocks.EMERALD_BLOCK : state.isPouetFreed() ? Blocks.GOLD_BLOCK : Blocks.REDSTONE_BLOCK).defaultBlockState(), Block.UPDATE_ALL);
		level.setBlock(HOME_LEVEL_BOARD.offset(4, 1, -1), Blocks.REDSTONE_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
	}

	private static void buildHomePrairie(ServerLevel level) {
		for (BlockPos tree : new BlockPos[] {
				new BlockPos(-26, HUB_Y, -8),
				new BlockPos(26, HUB_Y, -6),
				new BlockPos(-27, HUB_Y, 32),
				new BlockPos(25, HUB_Y, 35),
				new BlockPos(18, HUB_Y, 5)
		}) {
			placeTree(level, tree);
		}

		for (int x = -28; x <= 28; x += 7) {
			for (int z = -12; z <= 36; z += 8) {
				BlockPos flower = new BlockPos(x, HUB_Y, z);
				level.setBlock(flower, ((x + z) % 2 == 0 ? Blocks.DANDELION : Blocks.POPPY).defaultBlockState(), Block.UPDATE_ALL);
			}
		}
	}

	private static void buildBackgroundMountains(ServerLevel level) {
		for (int peak = 0; peak < 3; peak++) {
			int centerX = -22 + peak * 22;
			int baseZ = 40;
			int height = 7 + peak * 2;

			for (int y = 0; y <= height; y++) {
				int radius = Math.max(1, height - y);

				for (int x = -radius; x <= radius; x++) {
					for (int z = -radius; z <= radius; z++) {
						if (Math.abs(x) + Math.abs(z) <= radius) {
							BlockState block = y >= height - 1 ? Blocks.SNOW_BLOCK.defaultBlockState() : Blocks.TERRACOTTA.defaultBlockState();
							level.setBlock(new BlockPos(centerX + x, HUB_Y + y - 1, baseZ + z), block, Block.UPDATE_ALL);
						}
					}
				}
			}
		}
	}

	private static void buildPortal(ServerLevel level, BlockPos base) {
		buildPortal(level, base, PortalMode.LEVEL1);
	}

	private static void buildPortal(ServerLevel level, BlockPos base, PortalMode mode) {
		BlockState portalState = ModBlocks.PATRICK_PORTAL.defaultBlockState().setValue(PatrickPortalBlock.MODE, mode);

		for (int width = 0; width < 4; width++) {
			for (int height = 0; height < 5; height++) {
				BlockPos pos = base.east(width).above(height);
				boolean border = width == 0 || width == 3 || height == 0 || height == 4;
				level.setBlock(pos, border ? ModBlocks.PATRICK_PORTAL_FRAME.defaultBlockState() : portalState, Block.UPDATE_ALL);
			}
		}
	}

	private static void buildPouetCage(ServerLevel level) {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 2; y++) {
					BlockPos pos = POUET_CAGE.offset(x, y, z);
					boolean wall = Math.abs(x) == 1 || Math.abs(z) == 1 || y == 2;

					if (wall) {
						level.setBlock(pos, Blocks.IRON_BARS.defaultBlockState(), Block.UPDATE_ALL);
					}
				}
			}
		}
	}

	private static void buildFoxCage(ServerLevel level) {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 2; y++) {
					BlockPos pos = FOX_CAGE.offset(x, y, z);
					boolean wall = Math.abs(x) == 1 || Math.abs(z) == 1 || y == 2;

					if (wall) {
						level.setBlock(pos, Blocks.IRON_BARS.defaultBlockState(), Block.UPDATE_ALL);
					}
				}
			}
		}
	}

	private static void openPouetCage(ServerLevel level) {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 2; y++) {
					BlockPos pos = POUET_CAGE.offset(x, y, z);

					if (level.getBlockState(pos).is(Blocks.IRON_BARS)) {
						level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
					}
				}
			}
		}
	}

	private static void openFoxCage(ServerLevel level) {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 2; y++) {
					BlockPos pos = FOX_CAGE.offset(x, y, z);

					if (level.getBlockState(pos).is(Blocks.IRON_BARS)) {
						level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
					}
				}
			}
		}
	}

	private static void buildSimpleTrees(ServerLevel level) {
		placeTree(level, new BlockPos(-7, HUB_Y, 12));
		placeTree(level, new BlockPos(7, HUB_Y, 22));
		placeTree(level, new BlockPos(-8, HUB_Y, 38));
		placeTree(level, new BlockPos(8, HUB_Y, 60));
	}

	private static void buildDenseLevelOneForest(ServerLevel level) {
		for (int x = -22; x <= 22; x += 6) {
			for (int z = -10; z <= 70; z += 8) {
				boolean keepPathOpen = Math.abs(x) <= 4 && z >= -6 && z <= 58;
				boolean keepArenaOpen = x * x + (z - ARENA_CENTER.getZ()) * (z - ARENA_CENTER.getZ()) <= 144;
				boolean keepCageOpen = Math.abs(x - POUET_CAGE.getX()) <= 4 && Math.abs(z - POUET_CAGE.getZ()) <= 4;

				if (!keepPathOpen && !keepArenaOpen && !keepCageOpen) {
					placeTree(level, new BlockPos(x, HUB_Y, z));
				}
			}
		}

		for (int x = -20; x <= 20; x += 5) {
			for (int z = -8; z <= 68; z += 7) {
				if (Math.abs(x) > 3) {
					BlockPos bush = new BlockPos(x + ((z / 7) % 2), HUB_Y, z);
					level.setBlock(bush, Blocks.OAK_LEAVES.defaultBlockState(), Block.UPDATE_ALL);
					level.setBlock(bush.above(), Blocks.OAK_LEAVES.defaultBlockState(), Block.UPDATE_ALL);
				}
			}
		}
	}

	private static void buildForestArenaDecorations(ServerLevel level) {
		for (BlockPos base : new BlockPos[] {
				new BlockPos(-10, HUB_Y, 42),
				new BlockPos(10, HUB_Y, 42),
				new BlockPos(-9, HUB_Y, 58),
				new BlockPos(9, HUB_Y, 58)
		}) {
			for (int y = 0; y <= 1; y++) {
				level.setBlock(base.above(y), Blocks.OAK_LOG.defaultBlockState(), Block.UPDATE_ALL);
			}

			level.setBlock(base.above(2), Blocks.OAK_LEAVES.defaultBlockState(), Block.UPDATE_ALL);
			level.setBlock(base.north(), Blocks.OAK_LEAVES.defaultBlockState(), Block.UPDATE_ALL);
			level.setBlock(base.south(), Blocks.OAK_LEAVES.defaultBlockState(), Block.UPDATE_ALL);
			level.setBlock(base.east(), Blocks.OAK_LEAVES.defaultBlockState(), Block.UPDATE_ALL);
			level.setBlock(base.west(), Blocks.OAK_LEAVES.defaultBlockState(), Block.UPDATE_ALL);
		}

		for (int x = -8; x <= 8; x += 4) {
			level.setBlock(new BlockPos(x, HUB_Y - 1, ARENA_CENTER.getZ() - 9), Blocks.COARSE_DIRT.defaultBlockState(), Block.UPDATE_ALL);
			level.setBlock(new BlockPos(x, HUB_Y - 1, ARENA_CENTER.getZ() + 9), Blocks.COARSE_DIRT.defaultBlockState(), Block.UPDATE_ALL);
		}
	}

	private static void buildEpisode2Trees(ServerLevel level) {
		placeTree(level, new BlockPos(146, HUB_Y, 14));
		placeTree(level, new BlockPos(176, HUB_Y, 20));
		placeTree(level, new BlockPos(142, HUB_Y, 56));
		placeTree(level, new BlockPos(180, HUB_Y, 62));
	}

	private static void buildMeteorite(ServerLevel level) {
		for (int x = -2; x <= 2; x++) {
			for (int y = -1; y <= 2; y++) {
				for (int z = -2; z <= 2; z++) {
					if (x * x + y * y + z * z <= 6) {
						BlockState block = y > 0 ? Blocks.MAGMA_BLOCK.defaultBlockState() : Blocks.BLACKSTONE.defaultBlockState();
						level.setBlock(METEORITE_CENTER.offset(x, y, z), block, Block.UPDATE_ALL);
					}
				}
			}
		}
	}

	private static void removeMeteorite(ServerLevel level) {
		for (int x = -3; x <= 3; x++) {
			for (int y = -1; y <= 3; y++) {
				for (int z = -3; z <= 3; z++) {
					BlockPos pos = METEORITE_CENTER.offset(x, y, z);

					if (level.getBlockState(pos).is(Blocks.MAGMA_BLOCK) || level.getBlockState(pos).is(Blocks.BLACKSTONE)) {
						level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
					}
				}
			}
		}

		for (int x = -3; x <= 3; x++) {
			for (int z = -3; z <= 3; z++) {
				if (x * x + z * z <= 9) {
					level.setBlock(METEORITE_CENTER.offset(x, -1, z), Blocks.COARSE_DIRT.defaultBlockState(), Block.UPDATE_ALL);
				}
			}
		}
	}

	private static void buildCastle(ServerLevel level) {
		for (int x = -14; x <= 14; x++) {
			for (int z = -14; z <= 14; z++) {
				BlockPos floor = CASTLE_CENTER.offset(x, -1, z);
				level.setBlock(floor, Blocks.SMOOTH_STONE.defaultBlockState(), Block.UPDATE_ALL);

				boolean wall = Math.abs(x) == 14 || Math.abs(z) == 14;
				boolean frontDoor = z == -14 && Math.abs(x) <= 1;

				if (wall && !frontDoor) {
					for (int y = 0; y <= 4; y++) {
						level.setBlock(CASTLE_CENTER.offset(x, y, z), Blocks.STONE_BRICKS.defaultBlockState(), Block.UPDATE_ALL);
					}
				} else if (!wall) {
					for (int y = 0; y <= 5; y++) {
						level.setBlock(CASTLE_CENTER.offset(x, y, z), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
					}
				}
			}
		}

		for (int x : new int[] { -14, 14 }) {
			for (int z : new int[] { -14, 14 }) {
				for (int y = 0; y <= 6; y++) {
					level.setBlock(CASTLE_CENTER.offset(x, y, z), Blocks.STONE_BRICKS.defaultBlockState(), Block.UPDATE_ALL);
				}

				level.setBlock(CASTLE_CENTER.offset(x, 7, z), Blocks.GOLD_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
			}
		}

		buildCastleDetails(level);
	}

	private static void buildCastleDetails(ServerLevel level) {
		for (int x = -3; x <= 3; x++) {
			for (int z = 8; z <= 11; z++) {
				level.setBlock(CASTLE_CENTER.offset(x, -1, z), Blocks.GOLD_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
			}
		}

		for (BlockPos pillar : new BlockPos[] {
				CASTLE_CENTER.offset(-8, 0, -8),
				CASTLE_CENTER.offset(8, 0, -8),
				CASTLE_CENTER.offset(-8, 0, 8),
				CASTLE_CENTER.offset(8, 0, 8)
		}) {
			for (int y = 0; y <= 4; y++) {
				level.setBlock(pillar.above(y), Blocks.STONE_BRICKS.defaultBlockState(), Block.UPDATE_ALL);
			}

			level.setBlock(pillar.above(5), Blocks.GOLD_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
		}

		for (int offset = -9; offset <= 9; offset += 6) {
			level.setBlock(CASTLE_CENTER.offset(offset, -1, 0), Blocks.REDSTONE_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
			level.setBlock(CASTLE_CENTER.offset(0, -1, offset), Blocks.REDSTONE_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
		}
	}

	private static void placeTree(ServerLevel level, BlockPos base) {
		for (int y = 0; y < 4; y++) {
			level.setBlock(base.above(y), Blocks.OAK_LOG.defaultBlockState(), Block.UPDATE_ALL);
		}

		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				for (int y = 3; y <= 5; y++) {
					if (Math.abs(x) + Math.abs(z) + Math.max(0, y - 4) <= 4) {
						level.setBlock(base.offset(x, y, z), Blocks.OAK_LEAVES.defaultBlockState(), Block.UPDATE_ALL);
					}
				}
			}
		}
	}

	private static boolean hasThierry(ServerLevel level) {
		return hasEntity(level, ThierryEntity.class);
	}

	private static void movePatrickTowardEpisode2(ServerLevel level) {
		PatrickEntity patrick = getFirstEntity(level, PatrickEntity.class);

		if (patrick == null) {
			PatrickEntity spawnedPatrick = ModEntityTypes.PATRICK.spawn(level, EPISODE2_PATRICK, EntitySpawnReason.EVENT);

			if (spawnedPatrick != null) {
				preparePatrick(spawnedPatrick);
			}

			return;
		}

		if (patrick.distanceToSqr(EPISODE2_ARRIVAL.getCenter()) > 3600.0D) {
			preparePatrick(patrick);
			patrick.setPos(EPISODE2_PATRICK.getX() + 0.5D, EPISODE2_PATRICK.getY(), EPISODE2_PATRICK.getZ() + 0.5D);
		}
	}

	private static void spawnFriendlyThierry(ServerLevel level) {
		ThierryEntity friendlyThierry = getEntities(level, ThierryEntity.class).stream()
				.filter(ThierryEntity::isFriendly)
				.findFirst()
				.orElse(null);

		if (friendlyThierry == null) {
			friendlyThierry = ModEntityTypes.THIERRY.spawn(level, FRIENDLY_THIERRY_SPAWN, EntitySpawnReason.EVENT);
		}

		if (friendlyThierry != null) {
			friendlyThierry.setFriendly(true);
			friendlyThierry.setPersistenceRequired();
		}
	}

	private static boolean hasPlayerIn(ServerLevel level, AABB area) {
		return !level.getPlayers(player -> area.contains(player.position())).isEmpty();
	}

	private static PatrickEntity getClosestPatrick(ServerLevel level, ServerPlayer player) {
		return getEntities(level, PatrickEntity.class).stream()
				.min(Comparator.comparingDouble(patrick -> patrick.distanceToSqr(player)))
				.orElse(null);
	}

	private static PatrickEntity movePatrickFromAnotherLevel(ServerLevel targetLevel, BlockPos companionPos, float yRot, float xRot) {
		for (ServerLevel level : targetLevel.getServer().getAllLevels()) {
			if (level == targetLevel) {
				continue;
			}

			List<PatrickEntity> patricks = getEntities(level, PatrickEntity.class);

			if (patricks.isEmpty()) {
				continue;
			}

			PatrickEntity patrick = patricks.get(0);
			Entity teleported = patrick.teleport(new TeleportTransition(
					targetLevel,
					Vec3.atBottomCenterOf(companionPos),
					Vec3.ZERO,
					yRot,
					xRot,
					TeleportTransition.DO_NOTHING
			));

			return teleported instanceof PatrickEntity movedPatrick ? movedPatrick : null;
		}

		return null;
	}

	private static void preparePatrick(PatrickEntity patrick) {
		patrick.refreshCompanionStats();
		patrick.setPersistenceRequired();
	}

	private static void discardOtherPatricks(ServerLevel targetLevel, PatrickEntity keeper) {
		for (ServerLevel level : targetLevel.getServer().getAllLevels()) {
			for (PatrickEntity patrick : getEntities(level, PatrickEntity.class)) {
				if (!patrick.getUUID().equals(keeper.getUUID())) {
					patrick.discard();
				}
			}
		}
	}

	private static <T extends Entity> boolean hasEntity(ServerLevel level, Class<T> entityClass) {
		return !level.getEntities(EntityTypeTest.forClass(entityClass), entity -> !entity.isRemoved()).isEmpty();
	}

	private static <T extends Entity> T getFirstEntity(ServerLevel level, Class<T> entityClass) {
		List<T> entities = getEntities(level, entityClass);
		return entities.isEmpty() ? null : entities.get(0);
	}

	private static <T extends Entity> List<T> getEntities(ServerLevel level, Class<T> entityClass) {
		return new ArrayList<>(level.getEntities(EntityTypeTest.forClass(entityClass), entity -> !entity.isRemoved()));
	}

	private static <T extends Entity> T keepOne(List<T> entities, BlockPos preferredPos) {
		if (entities.isEmpty()) {
			return null;
		}

		T keeper = entities.stream()
				.min(Comparator.comparingDouble(entity -> entity.distanceToSqr(preferredPos.getCenter())))
				.orElse(null);

		for (T entity : entities) {
			if (entity != keeper) {
				entity.discard();
			}
		}

		return keeper;
	}

	private static void playSound(ServerLevel level, BlockPos pos, net.minecraft.sounds.SoundEvent sound, SoundSource source, float volume, float pitch) {
		level.playSound(null, pos, sound, source, volume, pitch);
	}

	private static void broadcast(ServerLevel level, String message) {
		for (ServerPlayer player : level.players()) {
			player.sendSystemMessage(Component.literal(message));
		}
	}
}
