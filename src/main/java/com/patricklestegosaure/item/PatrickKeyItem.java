package com.patricklestegosaure.item;

import com.patricklestegosaure.block.PatrickPortalBlock;
import com.patricklestegosaure.block.PatrickPortalBlock.ActivationResult;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class PatrickKeyItem extends Item {
	public PatrickKeyItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();

		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		}

		if (context.getPlayer() instanceof ServerPlayer player) {
			ActivationResult result = PatrickPortalBlock.tryUseKey(level, context.getClickedPos(), player, this);

			if (result != ActivationResult.INVALID_FRAME) {
				return result == ActivationResult.LOCKED ? InteractionResult.FAIL : InteractionResult.SUCCESS_SERVER;
			}

			player.sendSystemMessage(Component.literal("Le cadre du portail de Patrick doit faire 4x5."));
		}

		return InteractionResult.FAIL;
	}
}
