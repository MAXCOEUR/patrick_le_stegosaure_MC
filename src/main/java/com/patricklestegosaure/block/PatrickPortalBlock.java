package com.patricklestegosaure.block;

import com.mojang.serialization.MapCodec;
import com.patricklestegosaure.registry.ModBlocks;
import com.patricklestegosaure.registry.ModDimensions;
import com.patricklestegosaure.registry.ModItems;
import com.patricklestegosaure.world.PatrickWorldEvents;
import com.patricklestegosaure.world.PatrickWorldState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

public class PatrickPortalBlock extends Block implements Portal {
	public static final MapCodec<PatrickPortalBlock> CODEC = simpleCodec(PatrickPortalBlock::new);

	public PatrickPortalBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public MapCodec<PatrickPortalBlock> codec() {
		return CODEC;
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean entering) {
		if (entity.canUsePortal(false)) {
			entity.setAsInsidePortal(this, pos);
		}
	}

	@Override
	public int getPortalTransitionTime(ServerLevel level, Entity entity) {
		return entity instanceof Player ? 20 : 0;
	}

	@Override
	public TeleportTransition getPortalDestination(ServerLevel sourceLevel, Entity entity, BlockPos portalPos) {
		ServerLevel targetLevel;
		BlockPos destination;

		if (sourceLevel.dimension().equals(ModDimensions.PATRICK_WORLD)) {
			PatrickWorldState state = PatrickWorldState.get(sourceLevel.getServer());
			targetLevel = sourceLevel.getServer().getLevel(Level.OVERWORLD);
			destination = state.getOverworldReturnPos();
		} else {
			PatrickWorldState state = PatrickWorldState.get(sourceLevel.getServer());
			state.rememberOverworldReturn(portalPos);
			targetLevel = sourceLevel.getServer().getLevel(ModDimensions.PATRICK_WORLD);
			boolean episode2KeyHeld = entity instanceof ServerPlayer player && hasEpisode2Key(player);
			boolean episode2Unlocked = state.isPouetFreed() && episode2KeyHeld;
			destination = episode2Unlocked ? PatrickWorldEvents.EPISODE2_ARRIVAL : PatrickWorldEvents.HUB_ARRIVAL;

			if (targetLevel != null) {
				if (episode2Unlocked) {
					PatrickWorldEvents.ensureEpisode2(targetLevel);
				} else {
					PatrickWorldEvents.ensureHub(targetLevel);
				}
			}
		}

		if (targetLevel == null) {
			return null;
		}

		return new TeleportTransition(
				targetLevel,
				Vec3.atBottomCenterOf(destination),
				entity.getDeltaMovement(),
				entity.getYRot(),
				entity.getXRot(),
				TeleportTransition.PLAY_PORTAL_SOUND.then(teleportedEntity -> {
					if (teleportedEntity instanceof ServerPlayer player) {
						PatrickWorldEvents.syncPatrickWithPlayer(player);
					}
				})
		);
	}

	@Override
	public Portal.Transition getLocalTransition() {
		return Portal.Transition.CONFUSION;
	}

	public static boolean tryLightPortal(Level level, BlockPos clickedPos) {
		for (Direction.Axis axis : new Direction.Axis[] { Direction.Axis.X, Direction.Axis.Z }) {
			Direction widthDirection = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;

			for (int widthOffset = 0; widthOffset < 4; widthOffset++) {
				for (int heightOffset = 0; heightOffset < 5; heightOffset++) {
					BlockPos base = clickedPos.relative(widthDirection, -widthOffset).below(heightOffset);

					if (isCompleteFrame(level, base, widthDirection)) {
						lightInterior(level, base, widthDirection);
						level.playSound(null, clickedPos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
						return true;
					}
				}
			}
		}

		return false;
	}

	private static boolean isCompleteFrame(Level level, BlockPos base, Direction widthDirection) {
		for (int width = 0; width < 4; width++) {
			for (int height = 0; height < 5; height++) {
				BlockPos pos = base.relative(widthDirection, width).above(height);
				BlockState state = level.getBlockState(pos);
				boolean border = width == 0 || width == 3 || height == 0 || height == 4;

				if (border) {
					if (!state.is(ModBlocks.PATRICK_PORTAL_FRAME)) {
						return false;
					}
				} else if (!state.isAir() && !state.is(ModBlocks.PATRICK_PORTAL)) {
					return false;
				}
			}
		}

		return true;
	}

	private static void lightInterior(Level level, BlockPos base, Direction widthDirection) {
		BlockState portalState = ModBlocks.PATRICK_PORTAL.defaultBlockState();

		for (int width = 1; width <= 2; width++) {
			for (int height = 1; height <= 3; height++) {
				level.setBlockAndUpdate(base.relative(widthDirection, width).above(height), portalState);
			}
		}
	}

	private static boolean hasEpisode2Key(ServerPlayer player) {
		return player.getMainHandItem().getItem() == ModItems.PATRICK_EPISODE_2_KEY
				|| player.getOffhandItem().getItem() == ModItems.PATRICK_EPISODE_2_KEY;
	}
}
