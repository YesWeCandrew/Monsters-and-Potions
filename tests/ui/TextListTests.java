package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextListTests {

    private static TextList textList;

    @BeforeEach
    void setUpTextList() {
        textList = new TextList(5, 10);
        textList.addAllText(new ArrayList<>(Arrays.asList("hello", "goodbye", "testing", "  testing")));
    }

    @Test
    void checkSize() {
        textList.maximizeSize();
        assertEquals(10, textList.getWidth());
        assertEquals(5, textList.getHeight());

        String[] texts = textList.getStringRender();

        for(String text : texts) {
            assertEquals(10, text.length());
        }
    }
}
