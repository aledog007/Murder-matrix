package ch.bbw.csr.murder_matrix.core;

import ch.bbw.csr.murder_matrix.world.Direction;

/**
 * A utility class that holds global constant values.
 * This class cannot be instantiated.
 *
 * @author Cédric Skwar <cdrc@5y5.one>
 */
public final class GlobalConstants {
    /**
     * Indicates whether the application is in debug mode.
     * In debug mode additional logging is available and `Dear ImGUI` is activated in the game.
     */
    public static final boolean isDebug = true;

    /**
     * The constant currentScreens.
     */
    public static Screens currentScreens = Screens.MAIN_GAME;

    /**
     * The constant lastDirection.
     */
    public static Direction lastDirection;

    /**
     * Private constructor prevent instantiation of this utility class.
     */
    private GlobalConstants() {
    }
}
