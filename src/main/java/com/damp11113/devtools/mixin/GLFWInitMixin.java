package com.damp11113.devtools.mixin;

import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.Window;
import com.damp11113.devtools.imgui.ImguiLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class GLFWInitMixin {
	@Shadow
	@Final private long handle;

	@Inject(at = @At("TAIL"), method = "<init>", remap = false)
	private void onGLFWInit(WindowEventHandler windowEventHandler, MonitorTracker monitorTracker, WindowSettings windowSettings, String string, String string2, CallbackInfo ci){
		ImguiLoader.onGlfwInit(handle);
	}
}
