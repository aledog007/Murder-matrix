package ch.bbw.csr.murder_matrix.core;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * The type Input handler.
 */
public class InputHandler implements InputProcessor {
    private final GameScreen gameScreen;

    /**
     * Instantiates a new Input handler.
     *
     * @param gameScreen the game screen
     */
    public InputHandler(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ENTER) {
            gameScreen.processCommand(gameScreen.getCurrentInput());
            gameScreen.setCurrentInput("");
        } else if (keycode == Input.Keys.BACKSPACE && !gameScreen.getCurrentInput().isEmpty()) {
            String currentInput = gameScreen.getCurrentInput();
            gameScreen.setCurrentInput(currentInput.substring(0, currentInput.length() - 1));
        } else if (keycode == Input.Keys.UP) {
            String previousCommand = gameScreen.getPreviousCommand();
            gameScreen.setCurrentInput(previousCommand);
        } else if (keycode == Input.Keys.DOWN) {
            String nextCommand = gameScreen.getNextCommand();
            gameScreen.setCurrentInput(nextCommand);
        } else if (keycode == Input.Keys.INSERT) {
            ImGuiRenderHandlerKt.handleKeyPress();
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        if (character != '\b') { // Ignore backspace
            gameScreen.setCurrentInput(gameScreen.getCurrentInput() + character);
            gameScreen.resetHistoryIndex();
        }
        return true;
    }


    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when the touch gesture is cancelled. Reason may be from OS interruption to touch becoming a large surface such as
     * the user cheek). Relevant on Android and iOS only. The button parameter will be {@link Input.Buttons#LEFT} on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amountX the horizontal scroll amount, negative or positive depending on the direction the wheel was scrolled.
     * @param amountY the vertical scroll amount, negative or positive depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(float amountX, float amountY) {
        float scrollAmount = -amountY * gameScreen.getFontLineHeight();
        gameScreen.scroll(scrollAmount);
        return true;
    }


    /**
     * Scrolled boolean.
     *
     * @param amount the amount
     * @return the boolean
     */
    public boolean scrolled(int amount) {
        return false;
    }
}
