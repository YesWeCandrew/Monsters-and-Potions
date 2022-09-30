package map;

import objects.Hero;
import objects.Item;
import objects.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MapTests {

    private static Map testMap;

    /*
    TestMap:
    _,X,X,X,X,X,X,X
    _,X,_,_,_,_,_,X
    _,X,_,X,_,X,_,X
    _,X,_,X,_,X,_,X
    $,X,X,X,_,X,_,X
    !,^,_,_,_,X,_,_
    _,X,_,_,_,X,_,X
    X,X,X,X,X,X,X,X

    Where:
    ! = Hero (sam)
    ^ = Monster (Demonspawn)
    $ = Item (Potion)

    And the Monster drops the Amulet when killed
     */

    @BeforeEach
    public void setUpMap() {
        testMap = new Map("testMap");

        // Adds the hero at 0,5
        Position heroPos = new Position(0,5);
        testMap.setObjectAt(
                heroPos,
                new Hero(
                        '!',
                        "sam",
                        40,
                        10,
                        null,
                        "asdf",
                        new ArrayList<>()));
        testMap.setHeroPositionReference(heroPos);
        testMap.setHeroFacing(Cardinality.NORTH);

        // Add
        Position monsterPos = new Position(1,5);
        testMap.setObjectAt(
                monsterPos,
                new Monster(
                        '^',
                        "Demonspawn",
                        45,
                        10,
                        null,
                        "Will hurt you! Better be prepared.",
                        new Item(
                                '%',
                                "Amulet",
                                "Win the game!",
                                "health",
                                5)
                ));

        Position itemPos = new Position(0,4);
        testMap.setObjectAt(
                itemPos,
                new Item(
                        '$',
                        "Potion",
                        "Increases health by 10",
                        "health",
                        10)
                );
    }

    /**
     * @author Andrew Howes
     */
    @Test
    void TestAllPossibleActions(){
        String allPos1 = testMap.allPossibleActions().toString();

        testMap.goRight();
        String allPos2 = testMap.allPossibleActions().toString();

        testMap.goDown();
        String allPos3 = testMap.allPossibleActions().toString();

        testMap.goUp();
        String allPos4 = testMap.allPossibleActions().toString();

        testMap.pickUpItem();
        String allPos5 = testMap.allPossibleActions().toString();

        testMap.goRight();
        testMap.attack();
        testMap.attack();
        testMap.attack();
        testMap.attack();
        testMap.attack();
        String allPos6 = testMap.allPossibleActions().toString();

        String expected1 = "[(P) Pick up Potion - Increases health by 10, (→) Turn right to face Demonspawn, (↓) Move down]";
        String expected2 = "[(↑) Turn up to face Potion, (A) Attack Demonspawn, (↓) Move down]";
        String expected3 = "[(↑) Move up]";
        String expected4 = expected1;
        String expected5 = "[(↑) Move up, (→) Turn right to face Demonspawn, (↓) Move down]";
        String expected6 = "[(↑) Move up, (P) Pick up Amulet - Win the game!, (↓) Move down]";

        assertEquals(expected1,allPos1);
        assertEquals(expected2,allPos2);
        assertEquals(expected3,allPos3);
        assertEquals(expected4,allPos4);
        assertEquals(expected5,allPos5);
        assertEquals(expected6,allPos6);
    }

    /**
     * Tests that the Boolean heroEscaped is set to null initially, and then
     * after being killed it tests that heroEscaped is set to false;
     * @author Andrew Howes
     */
    @Test
    void HeroCanBeKilled() {
        Boolean initial = testMap.heroEscaped;

        testMap.goRight();
        testMap.attack();
        testMap.attack();
        testMap.attack();
        testMap.attack();

        Boolean after = testMap.heroEscaped;

        assertNull(initial);
        assertFalse(after);
    }

    /**
     * Tests that the hero can win by picking up the amulet.
     */
    @Test
    void HeroCanWin() {
        Boolean initial = testMap.heroEscaped;

        testMap.pickUpItem();
        testMap.goRight();
        testMap.attack();
        testMap.attack();
        testMap.attack();
        testMap.attack();
        testMap.attack();
        testMap.pickUpItem();

        Boolean after = testMap.heroEscaped;

        assertNull(initial);
        assertTrue(after);
    }

    /**
     * Tests that hero can discard item after picking up and also tests the to string function
     */
    @Test
    void canDiscardItem() {
        String expectedWithItem = """
                 #######
                 #     #
                 # # # #
                 # # # #
                $### # #
                !^   # \s
                 #   # #
                ########""";

        String expectedWithoutItem= """
                 #######
                 #     #
                 # # # #
                 # # # #
                 ### # #
                !^   # \s
                 #   # #
                ########""";

        String mapBefore = testMap.toString();

        testMap.pickUpItem();
        String mapDuring = testMap.toString();

        testMap.discardItem(0);
        String mapAfter = testMap.toString();

        assertEquals(expectedWithItem,mapBefore);
        assertEquals(expectedWithoutItem,mapDuring);
        assertEquals(expectedWithItem,mapAfter);
    }

    @Test
    void writeReadTest() {
        String firstMap = testMap.toString();
        Map.save(99,"testMapSave");
        new Map("99-testMapSave","99-testMapSave");
        String firstReadMapString = testMap.toString();

        setUpMap();

        testMap.pickUpItem();
        String secondMap = testMap.toString();
        Map.save(99,"testMapSave");
        new Map("99-testMapSave","99-testMapSave");
        String secondReadMapString = testMap.toString();

        setUpMap();

        assertEquals(firstMap,firstReadMapString);
        assertEquals(secondMap,secondReadMapString);
    }
}
