package com.patricklestegosaure.client.render;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.entity.ThierryEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class ThierryRenderer extends MobRenderer<ThierryEntity, LivingEntityRenderState, ThierryModel> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(id("thierry"), "main");
	private static final Identifier TEXTURE = id("textures/entity/thierry.png");

	public ThierryRenderer(EntityRendererProvider.Context context) {
		super(context, new ThierryModel(context.bakeLayer(LAYER)), 0.45F);
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
