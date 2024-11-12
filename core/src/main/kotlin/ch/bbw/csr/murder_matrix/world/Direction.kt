package ch.bbw.csr.murder_matrix.world

import ch.bbw.csr.murder_matrix.world.Direction.valueOf
import java.util.*

/**
 * Represents the four cardinal directions in the game world.
 */
enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    companion object {
        /**
         * Converts a string representation to a `Direction` enum value.
         *
         *
         * This method attempts to match the given string (case-insensitive)
         * to one of the enum constants. If successful, it returns the
         * corresponding `Direction`. If unsuccessful, it throws an
         * `IllegalArgumentException`.
         *
         *
         * @param directionString the string representation of the direction
         * @return the corresponding [Direction] enum value
         * @throws IllegalArgumentException if the input string doesn't match any `Direction`
         * @see .valueOf
         * @see String.toUpperCase
         */
        @JvmStatic
        fun fromString(directionString: String): Direction {
            try {
                return valueOf(directionString.uppercase(Locale.getDefault()))
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Invalid direction: $directionString")
            }
        }

        @JvmStatic
        fun invertDirection(direction: Direction): Direction {
            return when (direction) {
                EAST -> WEST
                SOUTH -> NORTH
                WEST -> EAST
                NORTH -> SOUTH
            }
        }
    }
}
