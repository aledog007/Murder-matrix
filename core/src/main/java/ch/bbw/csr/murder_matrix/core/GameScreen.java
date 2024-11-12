package ch.bbw.csr.murder_matrix.core;

import ch.bbw.csr.murder_matrix.players.Player;
import ch.bbw.csr.murder_matrix.render.ImGuiRender;
import ch.bbw.csr.murder_matrix.render.screens.GameOverScreen;
import ch.bbw.csr.murder_matrix.render.screens.WinScreen;
import ch.bbw.csr.murder_matrix.world.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import java.util.*;

/**
 * The type Game screen.
 */
public class GameScreen implements Screen {
    private static Map<String, Boolean> additionalImGuiWindows = new HashMap<>();
    private final World world;
    private final Player player;
    private final SpriteBatch batch;
    private final StringBuilder gameText;
    private final CommandParser commandParser;
    private final float baseFontSize = 16f;
    private final FreeTypeFontGenerator fontGenerator;
    private final FreeTypeFontParameter fontParameter;
    private final List<String> commandHistory = new ArrayList<>();
    private String currentInput;
    private BitmapFont font;
    private float currentFontSize;
    private int screenWidth;
    private int screenHeight;
    private float scrollPosition;
    private float maxScrollPosition;
    private int historyIndex = -1;

    /**
     * Instantiates a new Game screen.
     */
    public GameScreen() {
        world = new World();
        player = new Player(Objects.requireNonNull(world.getStartingRoom()));
        batch = new SpriteBatch();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        currentFontSize = baseFontSize;
        scrollPosition = 0f;
        maxScrollPosition = 0f;
        Gdx.input.setInputProcessor(new InputHandler(this));

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoMono-Regular.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        createFont();
        if (GlobalConstants.isDebug) {
            ImGuiRender.initImGui();
        }
        gameText = new StringBuilder();
        currentInput = "";
        commandParser = new CommandParser(this, world, player);
        appendToGameText("Welcome to the Murder Mystery!");
        appendToGameText("For a full overview of commands enter: \"help\"");
        appendToGameText("You have 19 days to find the murderer. Good luck!");
        appendToGameText("What would you like to do? ");
        additionalImGuiWindows.put("metrics", false);
        additionalImGuiWindows.put("debug", GlobalConstants.isDebug);
        additionalImGuiWindows.put("styleEditor", false);
    }

    private void createFont() {
        if (font != null) {
            font.dispose();
        }
        fontParameter.size = Math.round(currentFontSize);
        fontParameter.color = Color.GREEN;
        fontParameter.mono = false;
        fontParameter.kerning = true;
        font = fontGenerator.generateFont(fontParameter);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (GlobalConstants.currentScreens == Screens.MAIN_GAME) {
            if (world.currentDay >= world.getNPCs().size() - 1) {
                GlobalConstants.currentScreens = Screens.GAME_OVER;
                this.render(delta + 1);
            }
            world.update(delta);
            batch.begin();
            float drawStartY = screenHeight - 10 + scrollPosition;
            font.draw(batch, gameText.toString() + "\n> " + currentInput, 10, drawStartY);
            batch.end();
        } else if (GlobalConstants.currentScreens == Screens.GAME_OVER) {
            GameOverScreen.INSTANCE.render(batch);
        } else if (GlobalConstants.currentScreens == Screens.WIN) {
            WinScreen.INSTANCE.render(batch);
        }

        if (GlobalConstants.isDebug) {
            additionalImGuiWindows = ImGuiRenderHandlerKt.imGuiRenderHandler(additionalImGuiWindows, player, world);
        }
    }


    @Override
    public void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;

        float scaleFactor = Math.min((float) width / 800, (float) height / 600);
        currentFontSize = baseFontSize * scaleFactor;

        createFont();

        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        updateMaxScrollPosition();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        fontGenerator.dispose();
    }

    /**
     * Append to game text.
     *
     * @param text the text
     */
    public void appendToGameText(String text) {
        gameText.append(text).append("\n");

        // Ensure the text doesn't grow too long
        while (gameText.length() > 2000) {
            gameText.delete(0, gameText.indexOf("\n") + 1);
        }

        updateMaxScrollPosition();
        scrollToBottom();
    }


    private void updateMaxScrollPosition() {
        GlyphLayout layout = new GlyphLayout(font, gameText.toString() + "\n> " + currentInput);
        maxScrollPosition = Math.max(0, layout.height - (screenHeight - 20));
    }

    private void scrollToBottom() {
        scrollPosition = maxScrollPosition;
    }

    /**
     * Gets current input.
     *
     * @return the current input
     */
    public String getCurrentInput() {
        return currentInput;
    }

    /**
     * Sets current input.
     *
     * @param input the input
     */
    public void setCurrentInput(String input) {
        this.currentInput = input;
    }

    /**
     * Process command.
     *
     * @param command the command
     */
    public void processCommand(String command) {
        addToCommandHistory(command);
        appendToGameText("\n> " + command + "\n");
        String response = commandParser.commander(command);
        Gdx.app.debug("GameScreen.processCommand", String
            .format("Got the response from commandParser: %s, with the length: %d", response, response.length()));
        if (response.contains("SPECIAL-PLAYER-LOSS")) {
            GlobalConstants.currentScreens = Screens.GAME_OVER;
            return;
        } else if (response.contains("SPECIAL-PLAYER-WIN")) {
            GlobalConstants.currentScreens = Screens.WIN;
            return;
        }
        appendToGameText(response);
        appendToGameText("What would you like to do? ");
    }

    private void addToCommandHistory(String command) {
        commandHistory.add(command);
        historyIndex = commandHistory.size();
    }


    public String getPreviousCommand() {
        if (historyIndex > 0) {
            historyIndex--;
            return commandHistory.get(historyIndex);
        }
        return "";
    }

    public String getNextCommand() {
        if (historyIndex < commandHistory.size() - 1) {
            historyIndex++;
            return commandHistory.get(historyIndex);
        }
        return "";
    }

    public void resetHistoryIndex() {
        historyIndex = commandHistory.size();
    }

    public float getFontLineHeight() {
        return font.getLineHeight();
    }

    public void scroll(float amount) {
        scrollPosition -= amount;
        scrollPosition = Math.max(0, Math.min(scrollPosition, maxScrollPosition));
    }
}
