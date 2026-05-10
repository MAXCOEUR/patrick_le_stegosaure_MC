package com.patricklestegosaure.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

import com.patricklestegosaure.client.render.DetailedCharacterModel;
import com.patricklestegosaure.client.render.DetailedCharacterRenderer;
import com.patricklestegosaure.client.render.SimpleDinoModel;
import com.patricklestegosaure.client.render.SimpleDinoRenderer;
import com.patricklestegosaure.registry.ModEntityTypes;

public class PatrickLeStegosaureClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModelLayerRegistry.registerModelLayer(SimpleDinoRenderer.PATRICK_LAYER, SimpleDinoModel::createStegosaurusLayer);
		ModelLayerRegistry.registerModelLayer(SimpleDinoRenderer.THIERRY_LAYER, SimpleDinoModel::createTrexLayer);
		ModelLayerRegistry.registerModelLayer(DetailedCharacterRenderer.SAUCISSE_LAYER, DetailedCharacterModel::createSaucisseLayer);
		ModelLayerRegistry.registerModelLayer(DetailedCharacterRenderer.POUET_LAYER, DetailedCharacterModel::createPouetLayer);
		ModelLayerRegistry.registerModelLayer(DetailedCharacterRenderer.PASCAL_LAYER, DetailedCharacterModel::createPascalLayer);
		ModelLayerRegistry.registerModelLayer(DetailedCharacterRenderer.PAMOUK_LAYER, DetailedCharacterModel::createPamoukLayer);
		ModelLayerRegistry.registerModelLayer(DetailedCharacterRenderer.KALASH_LAYER, DetailedCharacterModel::createKalashLayer);
		ModelLayerRegistry.registerModelLayer(DetailedCharacterRenderer.BRIGITTE_LAYER, DetailedCharacterModel::createBrigitteLayer);

		EntityRendererRegistry.register(ModEntityTypes.PATRICK, context -> SimpleDinoRenderer.patrick(context));
		EntityRendererRegistry.register(ModEntityTypes.THIERRY, context -> SimpleDinoRenderer.thierry(context));
		EntityRendererRegistry.register(ModEntityTypes.SAUCISSE, DetailedCharacterRenderer::saucisse);
		EntityRendererRegistry.register(ModEntityTypes.POUET, DetailedCharacterRenderer::pouet);
		EntityRendererRegistry.register(ModEntityTypes.PASCAL, DetailedCharacterRenderer::pascal);
		EntityRendererRegistry.register(ModEntityTypes.PAMOUK, DetailedCharacterRenderer::pamouk);
		EntityRendererRegistry.register(ModEntityTypes.KALASH, DetailedCharacterRenderer::kalash);
		EntityRendererRegistry.register(ModEntityTypes.BRIGITTE, DetailedCharacterRenderer::brigitte);
	}
}
