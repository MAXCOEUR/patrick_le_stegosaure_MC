package com.patricklestegosaure.client.render;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.entity.PouetEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class PouetRenderer extends MobRenderer<PouetEntity, LivingEntityRenderState, PouetModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(id("pouet"), "main");
	private static final Identifier TEXTURE = id("textures/entity/pouet.png");

	public PouetRenderer(EntityRendererProvider.Context context) {
		super(context, new PouetModel(context.bakeLayer(LAYER)), 0.32F);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState renderState) {
		return TEXTURE;
	}

	private static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(PatrickLeStegosaure.MOD_ID, path);
	}
}
