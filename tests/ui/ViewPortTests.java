package ui;

import map.Map;
import map.Position;
import objects.GameObject;
import objects.Wall;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewPortTests {

    private static Map map;
    private static ViewPort viewPort;

    @BeforeEach
    void setUpViewPort() {
        GameObject[][] area = new GameObject[5][5];
        area[0][1] = new Wall(); // in front to the left
        area[0][3] = new Wall(); // in front to the right
        area[3][2] = new Wall(); // directly behind the hero

        map = new Map(area, new Position(2, 2));

        viewPort = new ViewPort(map, 2, true);
    }

    @Test
    void testViewPortCentre() {
        String[] expected = {
                "@@@@@@@",
                "@ # # @",
                "@     @",
                "@  H  @",
                "@  #  @",
                "@     @",
                "@@@@@@@"
        };

        assertEquals(Arrays.toString(expected), Arrays.toString(viewPort.getStringRender()));
    }

    @Test
    void testViewPortOutOfBounds() {
        String[] expected = {
                "@@@@@@@",
                "@#####@",
                "@# # #@",
                "@# H  @",
                "@#    @",
                "@#  # @",
                "@@@@@@@"
        };

        map.goUp();
        map.goLeft();

        assertEquals(Arrays.toString(expected), Arrays.toString(viewPort.getStringRender()));
    }
}
