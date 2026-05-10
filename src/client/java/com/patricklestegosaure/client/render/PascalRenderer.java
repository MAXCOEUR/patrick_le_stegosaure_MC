package com.patricklestegosaure.client.render;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.entity.PascalEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class PascalRenderer extends MobRenderer<PascalEntity, LivingEntityRenderState, PascalModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(id("pascal"), "main");
	private static final Identifier TEXTURE = id("textures/entity/pascal.png");

	public PascalRenderer(EntityRendererProvider.Context context) {
		super(context, new PascalModel(context.bakeLayer(LAYER)), 0.8F);
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
