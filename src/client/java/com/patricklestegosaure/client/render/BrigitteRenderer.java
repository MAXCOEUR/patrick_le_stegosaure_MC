package com.patricklestegosaure.client.render;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.entity.BrigitteEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class BrigitteRenderer extends MobRenderer<BrigitteEntity, LivingEntityRenderState, BrigitteModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(id("brigitte"), "main");
	private static final Identifier TEXTURE = id("textures/entity/brigitte.png");

	public BrigitteRenderer(EntityRendererProvider.Context context) {
		super(context, new BrigitteModel(context.bakeLayer(LAYER)), 0.28F);
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
