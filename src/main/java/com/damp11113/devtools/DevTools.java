package com.damp11113.devtools;

import com.damp11113.devtools.interfaces.Renderable;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

public class DevTools implements ModInitializer {
	public static final String MODID = "DevTools";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final MinecraftClient MINECRAFT = MinecraftClient.getInstance();
	public static ArrayList<Renderable> renderstack = new ArrayList<>();

	@Override
	public void onInitialize() {
		LOGGER.info("DevTools is started now!");
	}

	public static Renderable pushRenderable(Renderable renderable) {
		renderstack.add(renderable);
		return renderable;
	}

	public static Renderable pullRenderable(Renderable renderable) {
		renderstack.remove(renderable);
		return renderable;
	}
}