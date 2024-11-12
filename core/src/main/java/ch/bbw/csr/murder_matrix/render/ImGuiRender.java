package ch.bbw.csr.murder_matrix.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

/**
 * The type Im gui render.
 */
public class ImGuiRender {
    /**
     * The constant imGuiGlfw.
     */
    public static ImGuiImplGlfw imGuiGlfw;
    /**
     * The constant imGuiGl3.
     */
    public static ImGuiImplGl3 imGuiGl3;
    /**
     * The constant tmpProcessor.
     */
    public static InputProcessor tmpProcessor;

    /**
     * Init im gui.
     */
    public static void initImGui() {
        imGuiGlfw = new ImGuiImplGlfw();
        imGuiGl3 = new ImGuiImplGl3();
        long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow()
            .getWindowHandle();
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);
        io.getFonts().addFontDefault();
        io.getFonts().setFreeTypeRenderer(true);
        io.getFonts().build();
        imGuiGlfw.init(windowHandle, true);
        imGuiGl3.init("#version 150");
    }

    /**
     * Start im gui.
     */
    public static void startImGui() {
        if (tmpProcessor != null) {
            Gdx.input.setInputProcessor(tmpProcessor);
            tmpProcessor = null;
        }

        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    /**
     * Dispose.
     */
    public static void dispose() {
        imGuiGl3 = null;
        imGuiGlfw = null;
        ImGui.destroyContext();
    }

    /**
     * End im gui.
     */
    public static void endImGui() {
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        // If ImGui wants to capture the input, disable libGDX's input processor
        if (ImGui.getIO().getWantCaptureKeyboard() || ImGui.getIO().getWantCaptureMouse()) {
            tmpProcessor = Gdx.input.getInputProcessor();
            Gdx.input.setInputProcessor(null);
        }
    }
}
