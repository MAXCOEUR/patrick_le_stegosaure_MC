package com.patricklestegosaure.client.render;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.entity.PatrickEntity;
import com.patricklestegosaure.entity.ThierryEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;

public class SimpleDinoRenderer<T extends Mob> extends MobRenderer<T, LivingEntityRenderState, SimpleDinoModel> {
	public static final ModelLayerLocation PATRICK_LAYER = new ModelLayerLocation(id("patrick"), "main");
	public static final ModelLayerLocation THIERRY_LAYER = new ModelLayerLocation(id("thierry"), "main");

	private static final Identifier PATRICK_TEXTURE = id("textures/entity/patrick.png");
	private static final Identifier THIERRY_TEXTURE = id("textures/entity/thierry.png");

	private final Identifier texture;

	private SimpleDinoRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer, Identifier texture) {
		super(context, new SimpleDinoModel(context.bakeLayer(layer)), 0.45F);
		this.texture = texture;
	}

	public static SimpleDinoRenderer<PatrickEntity> patrick(EntityRendererProvider.Context context) {
		return new SimpleDinoRenderer<>(context, PATRICK_LAYER, PATRICK_TEXTURE);
	}

	public static SimpleDinoRenderer<ThierryEntity> thierry(EntityRendererProvider.Context context) {
		return new SimpleDinoRenderer<>(context, THIERRY_LAYER, THIERRY_TEXTURE);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState renderState) {
		return this.texture;
	}

	private static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(PatrickLeStegosaure.MOD_ID, path);
	}
}
