package com.patricklestegosaure.client.render;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.entity.PamoukEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class PamoukRenderer extends MobRenderer<PamoukEntity, LivingEntityRenderState, PamoukModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(id("pamouk"), "main");
	private static final Identifier TEXTURE = id("textures/entity/pamouk.png");

	public PamoukRenderer(EntityRendererProvider.Context context) {
		super(context, new PamoukModel(context.bakeLayer(LAYER)), 0.75F);
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
