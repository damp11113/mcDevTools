package com.damp11113.devtools.imgui;


import com.damp11113.devtools.DevTools;
import com.damp11113.devtools.interfaces.Renderable;
import imgui.*;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import java.util.Locale;

import static org.lwjgl.glfw.GLFW.*;

public class ImguiLoader {
    private static final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();

    private static final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private static long windowHandle;

    public static void onGlfwInit(long handle) {
        initializeImGui(handle);
        imGuiGlfw.init(handle,true);

        if (System.getProperty("os.name").toLowerCase(Locale.getDefault()).contains("mac")) {
            // force it to use opengl 3.1 instead of 3.0 because Apple
            imGuiGl3.init("#version 140");
        } else {
            imGuiGl3.init("#version 130");
        }

        windowHandle = handle;
    }

    public static void onFrameRender() {
        imGuiGlfw.newFrame();
        ImGui.newFrame();

        //user render code

        for (Renderable renderable: DevTools.renderstack) {
            DevTools.MINECRAFT.getProfiler().push("ImGui Render" + (renderable.getName() != null ? ("/" + renderable.getName()) : ""));

            if (renderable.getTheme() != null) {
                renderable.getTheme().preRender();
            }

            renderable.render();

            if (renderable.getTheme() != null) {
                renderable.getTheme().postRender();
            }

            DevTools.MINECRAFT.getProfiler().pop();
        }

        //end of user code

        ImGui.render();
        endFrame(windowHandle);
    }

    public static boolean charTyped(char chr, int keyCode) {
        final ImGuiIO io = ImGui.getIO();

        if (io.getWantTextInput()) {
            io.addInputCharacter(chr);
        }
        return true;
    }
    public static boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        final ImGuiIO io = ImGui.getIO();

        if (io.getWantCaptureKeyboard()) {
            if (io.getKeysDown(keyCode)) {
                io.setKeysDown(new boolean[]{ true });
            }
        }
        return true;
    }
    public static boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        final ImGuiIO io = ImGui.getIO();

        if (io.getWantCaptureKeyboard()) {
            if (io.getKeysDown(keyCode)) {
                io.setKeysDown(new boolean[] { false });
            }
        }
        return true;
    }

    private static void initializeImGui(long glHandle) {
        DevTools.LOGGER.info("ImGui Starting");
        ImGui.createContext();

        final ImGuiIO io = ImGui.getIO();

        io.setIniFilename(null);                               // We don't want to save .ini file
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard); // Enable Keyboard Controls
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);     // Enable Docking
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);   // Enable Multi-Viewport / Platform Windows
        io.setConfigViewportsNoTaskBarIcon(true);

        final ImFontAtlas fontAtlas = io.getFonts();
        final ImFontConfig fontConfig = new ImFontConfig(); // Natively allocated object, should be explicitly destroyed

        fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesCyrillic());

        fontAtlas.addFontDefault();

        fontConfig.setMergeMode(true); // When enabled, all fonts added with this config would be merged with the previously added font
        fontConfig.setPixelSnapH(true);

        fontConfig.destroy();

        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final ImGuiStyle style = ImGui.getStyle();
            style.setWindowRounding(0.0f);
            style.setColor(ImGuiCol.WindowBg, ImGui.getColorU32(ImGuiCol.WindowBg, 1));
        }
    }

    private static void endFrame(long windowPtr) {
        // After Dear ImGui prepared a draw data, we use it in the LWJGL3 renderer.
        // At that moment ImGui will be rendered to the current OpenGL context.
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(backupWindowPtr);
        }
    }
}
