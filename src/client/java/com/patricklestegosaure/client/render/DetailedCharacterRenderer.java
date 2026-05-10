package com.patricklestegosaure.client.render;

import com.patricklestegosaure.PatrickLeStegosaure;
import com.patricklestegosaure.entity.BrigitteEntity;
import com.patricklestegosaure.entity.KalashEntity;
import com.patricklestegosaure.entity.PamoukEntity;
import com.patricklestegosaure.entity.PascalEntity;
import com.patricklestegosaure.entity.PouetEntity;
import com.patricklestegosaure.entity.SaucisseEntity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.PathfinderMob;

public class DetailedCharacterRenderer<T extends PathfinderMob> extends MobRenderer<T, LivingEntityRenderState, DetailedCharacterModel> {
	public static final ModelLayerLocation SAUCISSE_LAYER = layer("saucisse");
	public static final ModelLayerLocation POUET_LAYER = layer("pouet");
	public static final ModelLayerLocation PASCAL_LAYER = layer("pascal");
	public static final ModelLayerLocation PAMOUK_LAYER = layer("pamouk");
	public static final ModelLayerLocation KALASH_LAYER = layer("kalash");
	public static final ModelLayerLocation BRIGITTE_LAYER = layer("brigitte");

	private final Identifier texture;

	private DetailedCharacterRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer, Identifier texture, float shadowRadius) {
		super(context, new DetailedCharacterModel(context.bakeLayer(layer)), shadowRadius);
		this.texture = texture;
	}

	public static DetailedCharacterRenderer<SaucisseEntity> saucisse(EntityRendererProvider.Context context) {
		return new DetailedCharacterRenderer<>(context, SAUCISSE_LAYER, texture("saucisse"), 0.08F);
	}

	public static DetailedCharacterRenderer<PouetEntity> pouet(EntityRendererProvider.Context context) {
		return new DetailedCharacterRenderer<>(context, POUET_LAYER, texture("pouet"), 0.32F);
	}

	public static DetailedCharacterRenderer<PascalEntity> pascal(EntityRendererProvider.Context context) {
		return new DetailedCharacterRenderer<>(context, PASCAL_LAYER, texture("pascal"), 0.8F);
	}

	public static DetailedCharacterRenderer<PamoukEntity> pamouk(EntityRendererProvider.Context context) {
		return new DetailedCharacterRenderer<>(context, PAMOUK_LAYER, texture("pamouk"), 0.75F);
	}

	public static DetailedCharacterRenderer<KalashEntity> kalash(EntityRendererProvider.Context context) {
		return new DetailedCharacterRenderer<>(context, KALASH_LAYER, texture("kalash"), 0.18F);
	}

	public static DetailedCharacterRenderer<BrigitteEntity> brigitte(EntityRendererProvider.Context context) {
		return new DetailedCharacterRenderer<>(context, BRIGITTE_LAYER, texture("brigitte"), 0.28F);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState renderState) {
		return this.texture;
	}

	private static ModelLayerLocation layer(String path) {
		return new ModelLayerLocation(id(path), "main");
	}

	private static Identifier texture(String path) {
		return id("textures/entity/" + path + ".png");
	}

	private static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(PatrickLeStegosaure.MOD_ID, path);
	}
}
