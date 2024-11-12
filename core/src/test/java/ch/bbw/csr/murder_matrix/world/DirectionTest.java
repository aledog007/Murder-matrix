package ch.bbw.csr.murder_matrix.world;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DirectionTest {

    @Test
    void fromString_ValidUppercaseInput_ReturnsCorrectDirection() {
        assertEquals(Direction.NORTH, Direction.fromString("NORTH"));
        assertEquals(Direction.EAST, Direction.fromString("EAST"));
        assertEquals(Direction.SOUTH, Direction.fromString("SOUTH"));
        assertEquals(Direction.WEST, Direction.fromString("WEST"));
    }

    @Test
    void fromString_ValidLowercaseInput_ReturnsCorrectDirection() {
        assertEquals(Direction.NORTH, Direction.fromString("north"));
        assertEquals(Direction.EAST, Direction.fromString("east"));
        assertEquals(Direction.SOUTH, Direction.fromString("south"));
        assertEquals(Direction.WEST, Direction.fromString("west"));
    }

    @Test
    void fromString_ValidMixedCaseInput_ReturnsCorrectDirection() {
        assertEquals(Direction.NORTH, Direction.fromString("NoRtH"));
        assertEquals(Direction.EAST, Direction.fromString("EaSt"));
        assertEquals(Direction.SOUTH, Direction.fromString("SoUtH"));
        assertEquals(Direction.WEST, Direction.fromString("WeSt"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"NORTHEAST", "UP", "DOWN", "LEFT", "RIGHT", ""})
    void fromString_InvalidInput_ThrowsIllegalArgumentException(String invalidDirection) {
        assertThrows(IllegalArgumentException.class, () -> Direction.fromString(invalidDirection));
    }

    @Test
    void fromString_NullInput_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Direction.fromString(null));
    }
}
