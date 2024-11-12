package ch.bbw.csr.murder_matrix;

import ch.bbw.csr.murder_matrix.core.GameScreen;
import ch.bbw.csr.murder_matrix.core.GlobalConstants;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * The main entry point for the Murder Matrix game.
 * <p>
 * This class extends {@link com.badlogic.gdx.Game} and serves as the
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms. It initializes the game environment and sets up the main game screen.
 * </p>
 *
 * @author Cédric Skwar <cdrc@5y5.one>
 * @version 1.0
 * @see com.badlogic.gdx.Game
 * @see ch.bbw.csr.murder_matrix.core.GameScreen
 * @since 0.0.1
 */
public class Main extends Game {
    /**
     * Initializes the game.
     * <p>
     * This method is called when the game is first created. It performs the following tasks:
     * <ul>
     *   <li>Sets the log level to debug if {@link GlobalConstants#isDebug} is true</li>
     *   <li>Creates a new {@link GameScreen} instance</li>
     *   <li>Sets the active screen to the newly created {@code GameScreen}</li>
     * </ul>
     * </p>
     *
     * @see GlobalConstants#isDebug
     * @see com.badlogic.gdx.Game#setScreen(com.badlogic.gdx.Screen)
     */
    @Override
    public void create() {
        if (GlobalConstants.isDebug) {
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
        }
        GameScreen gameScreen = new GameScreen();
        setScreen(gameScreen);
    }
}
