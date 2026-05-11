package com.patricklestegosaure.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.patricklestegosaure.PatrickLeStegosaure;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class PatrickWorldState extends SavedData {
	public static final Codec<PatrickWorldState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.BOOL.optionalFieldOf("hub_built", false).forGetter(state -> state.hubBuilt),
			Codec.BOOL.optionalFieldOf("arena_started", false).forGetter(state -> state.arenaStarted),
			Codec.BOOL.optionalFieldOf("pouet_freed", false).forGetter(state -> state.pouetFreed),
			Codec.BOOL.optionalFieldOf("episode2_built", false).forGetter(state -> state.episode2Built),
			Codec.BOOL.optionalFieldOf("meteorite_returned", false).forGetter(state -> state.meteoriteReturned),
			Codec.BOOL.optionalFieldOf("pascal_started", false).forGetter(state -> state.pascalStarted),
			Codec.BOOL.optionalFieldOf("pascal_defeated", false).forGetter(state -> state.pascalDefeated),
			Codec.BOOL.optionalFieldOf("fox_freed", false).forGetter(state -> state.foxFreed),
			Codec.BOOL.optionalFieldOf("thierry_helped_pascal_fight", false).forGetter(state -> state.thierryHelpedPascalFight),
			Codec.INT.optionalFieldOf("return_x", 0).forGetter(state -> state.returnX),
			Codec.INT.optionalFieldOf("return_y", 80).forGetter(state -> state.returnY),
			Codec.INT.optionalFieldOf("return_z", 0).forGetter(state -> state.returnZ)
	).apply(instance, PatrickWorldState::new));

	public static final SavedDataType<PatrickWorldState> TYPE = new SavedDataType<>(
			Identifier.fromNamespaceAndPath(PatrickLeStegosaure.MOD_ID, "patrick_world_state"),
			PatrickWorldState::new,
			CODEC,
			DataFixTypes.SAVED_DATA_COMMAND_STORAGE
	);

	private boolean hubBuilt;
	private boolean arenaStarted;
	private boolean pouetFreed;
	private boolean episode2Built;
	private boolean meteoriteReturned;
	private boolean pascalStarted;
	private boolean pascalDefeated;
	private boolean foxFreed;
	private boolean thierryHelpedPascalFight;
	private int returnX;
	private int returnY;
	private int returnZ;

	public PatrickWorldState() {
		this(false, false, false, false, false, false, false, false, false, 0, 80, 0);
	}

	public PatrickWorldState(boolean hubBuilt, boolean arenaStarted, boolean pouetFreed, boolean episode2Built, boolean meteoriteReturned, boolean pascalStarted, boolean pascalDefeated, boolean foxFreed, boolean thierryHelpedPascalFight, int returnX, int returnY, int returnZ) {
		this.hubBuilt = hubBuilt;
		this.arenaStarted = arenaStarted;
		this.pouetFreed = pouetFreed;
		this.episode2Built = episode2Built;
		this.meteoriteReturned = meteoriteReturned;
		this.pascalStarted = pascalStarted;
		this.pascalDefeated = pascalDefeated;
		this.foxFreed = foxFreed;
		this.thierryHelpedPascalFight = thierryHelpedPascalFight;
		this.returnX = returnX;
		this.returnY = returnY;
		this.returnZ = returnZ;
	}

	public static PatrickWorldState get(MinecraftServer server) {
		return server.getDataStorage().computeIfAbsent(TYPE);
	}

	public boolean isHubBuilt() {
		return hubBuilt;
	}

	public void setHubBuilt(boolean hubBuilt) {
		if (this.hubBuilt != hubBuilt) {
			this.hubBuilt = hubBuilt;
			setDirty();
		}
	}

	public boolean isArenaStarted() {
		return arenaStarted;
	}

	public void setArenaStarted(boolean arenaStarted) {
		if (this.arenaStarted != arenaStarted) {
			this.arenaStarted = arenaStarted;
			setDirty();
		}
	}

	public boolean isPouetFreed() {
		return pouetFreed;
	}

	public void setPouetFreed(boolean pouetFreed) {
		if (this.pouetFreed != pouetFreed) {
			this.pouetFreed = pouetFreed;
			setDirty();
		}
	}

	public boolean isEpisode2Built() {
		return episode2Built;
	}

	public void setEpisode2Built(boolean episode2Built) {
		if (this.episode2Built != episode2Built) {
			this.episode2Built = episode2Built;
			setDirty();
		}
	}

	public boolean isMeteoriteReturned() {
		return meteoriteReturned;
	}

	public void setMeteoriteReturned(boolean meteoriteReturned) {
		if (this.meteoriteReturned != meteoriteReturned) {
			this.meteoriteReturned = meteoriteReturned;
			setDirty();
		}
	}

	public boolean isPascalStarted() {
		return pascalStarted;
	}

	public void setPascalStarted(boolean pascalStarted) {
		if (this.pascalStarted != pascalStarted) {
			this.pascalStarted = pascalStarted;
			setDirty();
		}
	}

	public boolean isPascalDefeated() {
		return pascalDefeated;
	}

	public void setPascalDefeated(boolean pascalDefeated) {
		if (this.pascalDefeated != pascalDefeated) {
			this.pascalDefeated = pascalDefeated;
			setDirty();
		}
	}

	public boolean isFoxFreed() {
		return foxFreed;
	}

	public void setFoxFreed(boolean foxFreed) {
		if (this.foxFreed != foxFreed) {
			this.foxFreed = foxFreed;
			setDirty();
		}
	}

	public boolean hasThierryHelpedPascalFight() {
		return thierryHelpedPascalFight;
	}

	public void setThierryHelpedPascalFight(boolean thierryHelpedPascalFight) {
		if (this.thierryHelpedPascalFight != thierryHelpedPascalFight) {
			this.thierryHelpedPascalFight = thierryHelpedPascalFight;
			setDirty();
		}
	}

	public BlockPos getOverworldReturnPos() {
		return new BlockPos(returnX, returnY, returnZ);
	}

	public void rememberOverworldReturn(BlockPos pos) {
		this.returnX = pos.getX();
		this.returnY = pos.getY();
		this.returnZ = pos.getZ();
		setDirty();
	}
}
