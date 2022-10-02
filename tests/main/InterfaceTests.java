package main;

import map.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InterfaceTests {

    private String[] expected;

    @BeforeEach
    void setUpInterface() {
        String[] expectedResult = {
                " HP (100/100) **********  | @@@@@@@@@@@@@ |  Objective:               ",
                " AP  (50/100) *****-----  | @###########@ |  Escape the maze by       ",
                "                          | @###########@ |  finding the magic Amulet.",
                " Inventory:               | @### I##### @ |                           ",
                " |                    |   | @### #     #@ |                           ",
                " |                    |   | @### # # # #@ |  Options:                 ",
                " |                    |   | @### #H# # #@ |  | (W) Move up        |   ",
                " |                    |   | @### ### MI#@ |  |                    |   ",
                "                          | @###     #  @ |  |                    |   ",
                "                          | @### #   # #@ |  |                    |   ",
                "                          | @### #######@ |  |                    |   ",
                "                          | @### #     #@ |                           ",
                "                          | @@@@@@@@@@@@@ |                           ",
        };

        expected = expectedResult;
    }

    @Test
    void testMethod1() {
        Map map = new Map("dummySave", "56-anotherSave");
        Interface main = new Interface(map);

        assertEquals(String.join(System.lineSeparator(), expected), main.displayUI());
    }
}
