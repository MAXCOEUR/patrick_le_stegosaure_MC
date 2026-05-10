package com.patricklestegosaure.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

import com.patricklestegosaure.client.render.BrigitteModel;
import com.patricklestegosaure.client.render.BrigitteRenderer;
import com.patricklestegosaure.client.render.KalashModel;
import com.patricklestegosaure.client.render.KalashRenderer;
import com.patricklestegosaure.client.render.PamoukModel;
import com.patricklestegosaure.client.render.PamoukRenderer;
import com.patricklestegosaure.client.render.PascalModel;
import com.patricklestegosaure.client.render.PascalRenderer;
import com.patricklestegosaure.client.render.PatrickModel;
import com.patricklestegosaure.client.render.PatrickRenderer;
import com.patricklestegosaure.client.render.PouetModel;
import com.patricklestegosaure.client.render.PouetRenderer;
import com.patricklestegosaure.client.render.SaucisseModel;
import com.patricklestegosaure.client.render.SaucisseRenderer;
import com.patricklestegosaure.client.render.ThierryModel;
import com.patricklestegosaure.client.render.ThierryRenderer;
import com.patricklestegosaure.registry.ModEntityTypes;

public class PatrickLeStegosaureClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModelLayerRegistry.registerModelLayer(PatrickRenderer.LAYER, PatrickModel::createLayer);
		ModelLayerRegistry.registerModelLayer(ThierryRenderer.LAYER, ThierryModel::createLayer);
		ModelLayerRegistry.registerModelLayer(SaucisseRenderer.LAYER, SaucisseModel::createLayer);
		ModelLayerRegistry.registerModelLayer(PouetRenderer.LAYER, PouetModel::createLayer);
		ModelLayerRegistry.registerModelLayer(PascalRenderer.LAYER, PascalModel::createLayer);
		ModelLayerRegistry.registerModelLayer(PamoukRenderer.LAYER, PamoukModel::createLayer);
		ModelLayerRegistry.registerModelLayer(KalashRenderer.LAYER, KalashModel::createLayer);
		ModelLayerRegistry.registerModelLayer(BrigitteRenderer.LAYER, BrigitteModel::createLayer);

		EntityRendererRegistry.register(ModEntityTypes.PATRICK, PatrickRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.THIERRY, ThierryRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.SAUCISSE, SaucisseRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.POUET, PouetRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.PASCAL, PascalRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.PAMOUK, PamoukRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.KALASH, KalashRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.BRIGITTE, BrigitteRenderer::new);
	}
}
