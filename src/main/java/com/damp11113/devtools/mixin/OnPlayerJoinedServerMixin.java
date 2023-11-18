package com.damp11113.devtools.mixin;

import com.damp11113.devtools.DevTools;
import com.damp11113.devtools.Gui.MenuBar;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(ServerPlayerEntity.class)
public class OnPlayerJoinedServerMixin {
    @Inject(at = @At("HEAD"), method = "onSpawn")
    private void init(CallbackInfo ci) {
        DevTools.renderstack.add(new MenuBar());
        DevTools.LOGGER.info("DevTools is running now!");
    }

    @Inject(at = @At("HEAD"), method = "getPermissionLevel")
    private void init(CallbackInfoReturnable<Integer> cir) {
        DevTools.LOGGER.debug(String.valueOf(cir));
    }


    @Inject(at = @At("HEAD"), method = "onDisconnect")
    private void unshow(CallbackInfo info) {
        DevTools.renderstack.clear();
        DevTools.LOGGER.info("DevTools is standby now!");
    }

}