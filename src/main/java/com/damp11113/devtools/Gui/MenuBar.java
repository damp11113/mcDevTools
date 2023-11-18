package com.damp11113.devtools.Gui;

import com.damp11113.devtools.DevTools;
import com.damp11113.devtools.interfaces.Renderable;
import com.damp11113.devtools.interfaces.Theme;
import imgui.ImGui;
import net.minecraft.client.MinecraftClient;

public class MenuBar implements Renderable {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Theme getTheme() {
        return null;
    }

    @Override
    public void render() {
        if (ImGui.beginMainMenuBar()) {
            if (ImGui.beginMenu("[DevTools]")) {
                ImGui.menuItem("New Project");
                ImGui.separator();
                ImGui.menuItem("Save");
                ImGui.menuItem("Open");
                ImGui.separator();
                if (ImGui.menuItem("Exit")) {
                    //MinecraftClient.getInstance().stop();
                    DevTools.LOGGER.info("Exit func (not used)");
                }
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("Tools")) {
                ImGui.menuItem("NBT edit");
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("Options")) {
                if (ImGui.menuItem("test")) {
                    DevTools.LOGGER.info("tested");
                }
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("Windows")) {
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("Help")) {
                if (ImGui.menuItem("about")) {
                    DevTools.LOGGER.info("DevTools Beta");
                }
                ImGui.endMenu();
            }
            ImGui.endMainMenuBar();
        }
    }
}
