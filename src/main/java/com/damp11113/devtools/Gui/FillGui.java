package com.damp11113.devtools.Gui;

import com.damp11113.devtools.DevTools;
import imgui.ImGui;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class FillGui {
    public static boolean show = false;

    public static void Render() {
        if (show) {
            ImGui.begin("Fill");
            ImGui.text("Hello world");
            ImGui.end();
        }
    }
}
