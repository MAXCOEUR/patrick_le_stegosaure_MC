package com.patricklestegosaure.client.render;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.entity.SaucisseEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class SaucisseRenderer extends MobRenderer<SaucisseEntity, LivingEntityRenderState, SaucisseModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(id("saucisse"), "main");
	private static final Identifier TEXTURE = id("textures/entity/saucisse.png");

	public SaucisseRenderer(EntityRendererProvider.Context context) {
		super(context, new SaucisseModel(context.bakeLayer(LAYER)), 0.08F);
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
