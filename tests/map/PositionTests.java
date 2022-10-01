package map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTests {

    private static Position testPosition;

    @BeforeEach
    void setUpPosition() {
        testPosition = new Position(5, 5);
    }

    @Test
    void PositionInFront_NORTH_ReturnPositionUp() {
        Position result = testPosition.positionInFront(Cardinality.NORTH);

        assertAll(
                () -> assertEquals(5, result.getX()),
                () -> assertEquals(4, result.getY())
        );
    }

    @Test
    void PositionInFront_SOUTH_ReturnPositionDown() {
        Position result = testPosition.positionInFront(Cardinality.SOUTH);

        assertAll(
                () -> assertEquals(5, result.getX()),
                () -> assertEquals(6, result.getY())
        );
    }

    @Test
    void PositionInFront_EAST_ReturnPositionRight() {
        Position result = testPosition.positionInFront(Cardinality.EAST);

        assertAll(
                () -> assertEquals(6, result.getX()),
                () -> assertEquals(5, result.getY())
        );
    }

    @Test
    void PositionInFront_WEST_ReturnPositionLeft() {
        Position result = testPosition.positionInFront(Cardinality.WEST);

        assertAll(
                () -> assertEquals(4, result.getX()),
                () -> assertEquals(5, result.getY())
        );
    }

    @Test
    void Actions_NORTH_ReturnUpString() {
        String result = Position.actions(Cardinality.NORTH, "Move");
        assertEquals("(↑) Move up", result);

        result = Position.actions(Cardinality.NORTH, "Face");
        assertEquals("(↑) Face up", result);
    }

    @Test
    void Actions_SOUTH_ReturnUpString() {
        String result = Position.actions(Cardinality.SOUTH, "Move");
        assertEquals("(↓) Move down", result);

        result = Position.actions(Cardinality.SOUTH, "Face");
        assertEquals("(↓) Face down", result);
    }

    @Test
    void Actions_EAST_ReturnUpString() {
        String result = Position.actions(Cardinality.EAST, "Move");
        assertEquals("(→) Move right", result);

        result = Position.actions(Cardinality.EAST, "Face");
        assertEquals("(→) Face right", result);
    }

    @Test
    void Actions_WEST_ReturnUpString() {
        String result = Position.actions(Cardinality.WEST, "Move");
        assertEquals("(←) Move left", result);

        result = Position.actions(Cardinality.WEST, "Face");
        assertEquals("(←) Face left", result);
    }
}
