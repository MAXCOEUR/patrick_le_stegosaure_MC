package com.patricklestegosaure.client.render;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.entity.KalashEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class KalashRenderer extends MobRenderer<KalashEntity, LivingEntityRenderState, KalashModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(id("kalash"), "main");
	private static final Identifier TEXTURE = id("textures/entity/kalash.png");

	public KalashRenderer(EntityRendererProvider.Context context) {
		super(context, new KalashModel(context.bakeLayer(LAYER)), 0.18F);
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
