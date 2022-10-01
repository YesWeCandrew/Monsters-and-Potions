package ui;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeparatorTests {
    @Test
    void GetStringRender_Horizontal_ReturnStringArray() {
        Separator testSeparator = new Separator(3, true, '#');

        String[] result = testSeparator.getStringRender();

        assertEquals("[###]", Arrays.toString(result));
    }

    @Test
    void GetStringRender_Vertical_ReturnStringArray() {
        Separator testSeparator = new Separator(3, false, '#');

        String[] result = testSeparator.getStringRender();

        assertEquals("[#, #, #]", Arrays.toString(result));
    }

    @Test
    void MaximiseSize_Horizontal() {
        Separator testSeparator = new Separator(3, true, '#');
        testSeparator.maximizeSize();
        assertEquals(3, testSeparator.getWidth());
        assertEquals(1, testSeparator.getHeight());
    }

    @Test
    void MaximiseSize_Vertical() {
        Separator testSeparator = new Separator(3, false, '#');
        testSeparator.maximizeSize();
        assertEquals(1, testSeparator.getWidth());
        assertEquals(3, testSeparator.getHeight());
    }
}
