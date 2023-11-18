package com.damp11113.devtools.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.damp11113.devtools.DevTools;
import com.damp11113.devtools.imgui.ImguiLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderSystem.class, remap = false)
public class TailRenderMixin {
	@Inject(at = @At("HEAD"), method="flipFrame")
	private static void runTickTail(CallbackInfo ci) {
		DevTools.MINECRAFT.getProfiler().push("ImGui Render");
		ImguiLoader.onFrameRender();
		DevTools.MINECRAFT.getProfiler().pop();
	}
}
