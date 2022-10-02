package ui;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextFieldTests {

    @Test
    void considersNewLines() {
        TextField multi = new TextField("this " + System.lineSeparator() + " is " + System.lineSeparator() + " new");

        String[] muiltResult = {"this", "is  ", "new ", "    "};

        String[] result = multi.getStringRender();
        assertEquals(4, multi.getWidth());
        assertEquals(Arrays.toString(muiltResult), Arrays.toString(multi.getStringRender()));
    }
}
