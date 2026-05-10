package com.patricklestegosaure;

import net.fabricmc.api.ModInitializer;

import com.patricklestegosaure.registry.ModEntityTypes;
import com.patricklestegosaure.registry.ModItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatrickLeStegosaure implements ModInitializer {
	public static final String MOD_ID = "patrick-le-stegosaure";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEntityTypes.register();
		ModItems.register();

		LOGGER.info("Patrick and Thierry are ready!");
	}
}
