package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PaneTests {

    private static Pane pane;

    @BeforeEach
    void setUpPane() {
        pane = new Pane();
    }

    @Test
    void assertPaneSizeSimple() {
        pane.clearElements();
        pane.addElement(new TextField("hello world"));

        pane.maximizeSize();
        assertEquals(pane.getWidth(), 11);
        assertEquals(pane.getHeight(), 1);
    }

    @Test
    void assertPaneSizeComplex() {
        pane.clearElements();

        Pane first = new Pane();
        TextField hello = new TextField("hello");
        TextField goodbye = new TextField("goodbye");
        goodbye.relocate(10, 10);

        first.addElements(hello, goodbye);
        first.relocate(20, 0);

        Pane second = new Pane();
        TextField multi = new TextField("this " + System.lineSeparator() + " is " + System.lineSeparator() + " new");
        second.relocate(2, 2);

        second.addElement(multi);

        pane.addElements(first, second);
        pane.maximizeSize();

        assertEquals(20 + 10 + ("goodbye").length(), pane.getWidth());
        assertEquals(10 + 1, pane.getHeight());
    }
}
