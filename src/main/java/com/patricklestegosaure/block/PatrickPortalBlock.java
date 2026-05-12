package com.patricklestegosaure.block;

import com.mojang.serialization.MapCodec;
import com.patricklestegosaure.registry.ModBlocks;
import com.patricklestegosaure.registry.ModDimensions;
import com.patricklestegosaure.registry.ModItems;
import com.patricklestegosaure.world.PatrickWorldEvents;
import com.patricklestegosaure.world.PatrickWorldState;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

public class PatrickPortalBlock extends Block implements Portal {
	public static final MapCodec<PatrickPortalBlock> CODEC = simpleCodec(PatrickPortalBlock::new);
	public static final EnumProperty<PortalMode> MODE = EnumProperty.create("mode", PortalMode.class);

	public PatrickPortalBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(MODE, PortalMode.LEVEL1));
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

		if (sourceLevel.dimension().equals(ModDimensions.PATRICK_WORLD) || sourceLevel.dimension().equals(ModDimensions.PATRICK_HOME)) {
			PatrickWorldState state = PatrickWorldState.get(sourceLevel.getServer());
			targetLevel = sourceLevel.getServer().getLevel(Level.OVERWORLD);
			destination = state.getOverworldReturnPos();
		} else {
			PatrickWorldState state = PatrickWorldState.get(sourceLevel.getServer());
			state.rememberOverworldReturn(portalPos);
			PortalMode mode = getPortalMode(sourceLevel.getBlockState(portalPos));
			boolean homePortal = mode == PortalMode.HOME;
			boolean episode2Unlocked = mode == PortalMode.LEVEL2 && state.isPouetFreed();
			targetLevel = sourceLevel.getServer().getLevel(homePortal ? ModDimensions.PATRICK_HOME : ModDimensions.PATRICK_WORLD);
			destination = homePortal ? PatrickWorldEvents.HOME_ARRIVAL : episode2Unlocked ? PatrickWorldEvents.EPISODE2_ARRIVAL : PatrickWorldEvents.HUB_ARRIVAL;

			if (targetLevel != null) {
				if (homePortal) {
					PatrickWorldEvents.ensureHome(targetLevel);
				} else if (episode2Unlocked) {
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
					if (teleportedEntity instanceof ServerPlayer player && (player.level().dimension().equals(ModDimensions.PATRICK_WORLD) || player.level().dimension().equals(ModDimensions.PATRICK_HOME))) {
						PatrickWorldEvents.syncPatrickWithPlayer(player);
					}
				})
		);
	}

	@Override
	public Portal.Transition getLocalTransition() {
		return Portal.Transition.CONFUSION;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(MODE);
	}

	public static ActivationResult tryUseKey(Level level, BlockPos clickedPos, ServerPlayer player, Item key) {
		PortalMode targetMode = getModeForKey(key);

		if (targetMode == PortalMode.LEVEL2 && !PatrickWorldState.get(player.level().getServer()).isPouetFreed()) {
			player.sendSystemMessage(Component.literal("Impossible : il faut d'abord finir le niveau 1 et sauver Pouet."));
			return ActivationResult.LOCKED;
		}

		for (Direction.Axis axis : new Direction.Axis[] { Direction.Axis.X, Direction.Axis.Z }) {
			Direction widthDirection = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;

			for (int widthOffset = 0; widthOffset < 4; widthOffset++) {
				for (int heightOffset = 0; heightOffset < 5; heightOffset++) {
					BlockPos base = clickedPos.relative(widthDirection, -widthOffset).below(heightOffset);

					if (isCompleteFrame(level, base, widthDirection)) {
						PortalMode previousMode = getPortalMode(level, base, widthDirection);
						boolean wasLit = isLitPortal(level, base, widthDirection);
						lightInterior(level, base, widthDirection, targetMode);
						level.playSound(null, clickedPos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);

						if (targetMode == PortalMode.LEVEL2 && previousMode != PortalMode.LEVEL2) {
							player.sendSystemMessage(Component.literal("Le portail passe au niveau 2 !"));
							return ActivationResult.UPGRADED;
						}

						if (targetMode == PortalMode.HOME && previousMode != PortalMode.HOME) {
							player.sendSystemMessage(Component.literal("Le portail mene maintenant a la maison de Patrick !"));
							return ActivationResult.UPGRADED;
						}

						return wasLit ? ActivationResult.ALREADY_LIT : ActivationResult.LIT;
					}
				}
			}
		}

		return ActivationResult.INVALID_FRAME;
	}

	private static PortalMode getModeForKey(Item key) {
		if (key == ModItems.PATRICK_EPISODE_2_KEY) {
			return PortalMode.LEVEL2;
		}

		if (key == ModItems.PATRICK_HOME_KEY) {
			return PortalMode.HOME;
		}

		return PortalMode.LEVEL1;
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

	private static void lightInterior(Level level, BlockPos base, Direction widthDirection, PortalMode mode) {
		BlockState portalState = ModBlocks.PATRICK_PORTAL.defaultBlockState().setValue(MODE, mode);

		for (int width = 1; width <= 2; width++) {
			for (int height = 1; height <= 3; height++) {
				level.setBlockAndUpdate(base.relative(widthDirection, width).above(height), portalState);
			}
		}
	}

	private static boolean isLitPortal(Level level, BlockPos base, Direction widthDirection) {
		for (int width = 1; width <= 2; width++) {
			for (int height = 1; height <= 3; height++) {
				if (level.getBlockState(base.relative(widthDirection, width).above(height)).is(ModBlocks.PATRICK_PORTAL)) {
					return true;
				}
			}
		}

		return false;
	}

	public static PortalMode getPortalMode(Level level, BlockPos base, Direction widthDirection) {
		for (int width = 1; width <= 2; width++) {
			for (int height = 1; height <= 3; height++) {
				BlockState state = level.getBlockState(base.relative(widthDirection, width).above(height));

				if (state.is(ModBlocks.PATRICK_PORTAL)) {
					return getPortalMode(state);
				}
			}
		}

		return PortalMode.LEVEL1;
	}

	private static PortalMode getPortalMode(BlockState state) {
		return state.is(ModBlocks.PATRICK_PORTAL) ? state.getValue(MODE) : PortalMode.LEVEL1;
	}

	public enum ActivationResult {
		LIT,
		ALREADY_LIT,
		UPGRADED,
		LOCKED,
		INVALID_FRAME
	}

	public enum PortalMode implements StringRepresentable {
		LEVEL1("level1"),
		LEVEL2("level2"),
		HOME("home");

		private final String name;

		PortalMode(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}
	}
}
