package map;

import objects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MapTests {

    private static Map testMap;

    /*
    TestMap:
    _,#,#,#,#,#,#,#
    _,#,_,_,_,_,_,#
    _,#,_,#,_,#,_,#
    _,#,_,#,_,#,_,#
    $,#,#,#,_,#,_,#
    !,^,_,_,_,#,_,_
    _,#,_,_,_,#,_,#
    #,#,#,#,#,#,#,#

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
        String expected5 = "[(↑) Move up, (→) Turn right to face Demonspawn, (↓) Move down]";
        String expected6 = "[(↑) Move up, (P) Pick up Amulet - Win the game!, (↓) Move down]";

        assertEquals(expected1,allPos1);
        assertEquals(expected2,allPos2);
        assertEquals(expected3,allPos3);
        assertEquals(expected1,allPos4);
        assertEquals(expected5,allPos5);
        assertEquals(expected6,allPos6);
    }

    /**
     * Tests that the Boolean heroEscaped is set to null initially, and then
     * after being killed it tests that heroEscaped is set to false.
     *
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

        String expectedWithoutItem = """
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

        assertEquals(expectedWithItem, mapBefore);
        assertEquals(expectedWithoutItem, mapDuring);
        assertEquals(expectedWithItem, mapAfter);
    }

    @Test
    void Save_SaveStateToFiles() {
        String firstMap = testMap.toString();
        testMap.save(99,"testMapSave");
        Map readMap = new Map("99-testMapSave","99-testMapSave");
        String firstReadMapString = readMap.toString();

        // Ensures that heroPositionReference, heroFacing and heroEscaped are equal
        assertEquals(testMap.toString(), readMap.toString());

        setUpMap();

        testMap.pickUpItem();
        String secondMap = testMap.toString();
        testMap.save(99,"testMapSave");
        readMap = new Map("99-testMapSave","99-testMapSave");
        String secondReadMapString = readMap.toString();

        // Ensures that heroPositionReference, heroFacing and heroEscaped
        // are equal
        assertEquals(testMap.toString(), readMap.toString());

        // Ensures that the objects exist and are in right place
        assertEquals(firstMap, firstReadMapString);
        assertEquals(secondMap, secondReadMapString);
    }

    @Test
    void Save_NegativeId_ThrowException() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> testMap.save(-1, "testSave"));
        assertEquals("ID must be a positive integer.", exception.getMessage());
    }

    @Test
    void Save_NullFileName_ThrowException() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> testMap.save(123, null));
        assertEquals("saveFileName cannot be blank.", exception.getMessage());
    }

    @Test
    void Save_EmptyStringFileName_ThrowException() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> testMap.save(123, ""));
        assertEquals("saveFileName cannot be blank.", exception.getMessage());
    }

    @Test
    void Save_WhiteSpaceFileName_ThrowException() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> testMap.save(123, "   "));
        assertEquals("saveFileName cannot be blank.", exception.getMessage());
    }

    @Test
    void SaveMapCSV_SaveCSVToDisk() {
        File checkFile = new File("src/saves/Test_SaveMapCSV.csv");
        try {
            Files.deleteIfExists(checkFile.toPath());
        } catch (IOException ignored) {
        }

        Map.saveMapCSV("Test_SaveMapCSV");

        assertTrue(checkFile.exists());

        Map reloadMap = new Map("Test_SaveMapCSV");
        assertEquals("""
                 #######
                 #     #
                 # # # #
                 # # # #
                 ### # #
                     # \s
                 #   # #
                ########""", reloadMap.toString());
    }

    @Test
    void CreateJSONItemForHero_ReturnJSONObject() {
        Item testItem = new Item('@', "Item", "The test item", "health", 100);

        String result = Map.createJSONItemForHero(testItem).toJSONString();
        String expected = "{\"item\":{\"actionEffects\":\"health\",\"actionChange\":100,\"name\":\"Item\",\"description\":\"The test item\",\"charRepresentation\":\"@\"}}";

        assertEquals(expected, result);
    }

    // TODO: displayArea --> is this used?

    @Test
    void GetObjectAt_SquareNotOnBoard_ThrowException() {
        Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> testMap.getObjectAt(new Position(100, 100)));
        assertEquals("Index 100 out of bounds for length 8", exception.getMessage());
    }

    @Test
    void GetObjectAt_SquareOnBoard_ReturnObject() {
        LivingGameObject result = (LivingGameObject) testMap.getObjectAt(new Position(1, 5));
        assertAll(
                () -> assertEquals("Demonspawn", result.getName()),
                () -> assertEquals("Will hurt you! Better be prepared.", result.getDescription()),
                () -> assertEquals('^', result.getChar()));
    }

    @Test
    void SetObjectAt_SquareNotEmpty_ReturnFalse() {
        Position testPosition = new Position(1, 0);

        boolean result = testMap.setObjectAt(testPosition, null);

        assertFalse(result);
        assertEquals("""
                 #######
                 #     #
                 # # # #
                 # # # #
                $### # #
                !^   # \s
                 #   # #
                ########""", testMap.toString());
    }

    @Test
    void SetObjectAt_SquareEmpty_ReturnTrue() {
        Position testPosition = new Position(0, 0);

        boolean result = testMap.setObjectAt(testPosition,
                new Item('@', "Item", "Item", "health", 5));

        assertTrue(result);
        assertEquals("""
                @#######
                 #     #
                 # # # #
                 # # # #
                $### # #
                !^   # \s
                 #   # #
                ########""", testMap.toString());
    }

    @Test
    void ClearPosition_SquareEmpty_NoChange() {
        assertNull(testMap.getMap()[0][0]);
        testMap.clearPosition(new Position(0, 0));

        assertNull(testMap.getMap()[0][0]);
        assertEquals("""
                 #######
                 #     #
                 # # # #
                 # # # #
                $### # #
                !^   # \s
                 #   # #
                ########""", testMap.toString());
    }

    @Test
    void ClearPosition_SquareNotEmpty_PositionCleared() {
        assertNotNull(testMap.getMap()[0][4]);
        testMap.clearPosition(new Position(0, 4));

        assertNotNull(testMap.getMap()[0][4]);
        assertEquals("""
                 #######
                 #     #
                 # # # #
                 # # # #
                 ### # #
                !^   # \s
                 #   # #
                ########""", testMap.toString());
    }

    @Test
    void ClearPosition_SquareOffBoard_NoChange() {
        testMap.clearPosition(new Position(100, 100));

        assertEquals("""
                 #######
                 #     #
                 # # # #
                 # # # #
                $### # #
                !^   # \s
                 #   # #
                ########""", testMap.toString());
    }

    @Test
    void IsEmpty_SquareEmpty_ReturnTrue() {
        Position testPosition = new Position(0, 0);

        boolean result = testMap.isEmpty(testPosition);

        assertTrue(result);
    }

    @Test
    void IsEmpty_NotOnBoard_ReturnFalse() {
        Position testPosition = new Position(100, 100);

        boolean result = testMap.isEmpty(testPosition);

        assertFalse(result);
    }

    @Test
    void IsEmpty_SquareNotEmpty_ReturnFalse() {
        Position testPosition = new Position(1, 0);

        boolean result = testMap.isEmpty(testPosition);

        assertFalse(result);
    }

    @Test
    void MoveHero_SquareEmpty_ReturnTrue() {
        testMap.setHeroFacing(Cardinality.SOUTH);

        boolean result = testMap.moveHero();

        assertTrue(result);
        assertEquals("""
                 #######
                 #     #
                 # # # #
                 # # # #
                $### # #
                 ^   # \s
                !#   # #
                ########""", testMap.toString());
        assertAll(
                () -> assertEquals(0, testMap.getHeroPositionReference().getX()),
                () -> assertEquals(6, testMap.getHeroPositionReference().getY())
        );
    }

    @Test
    void MoveHero_SquareNotEmpty_ReturnFalse() {
        boolean result = testMap.moveHero();
        assertFalse(result);
    }

    @Test
    void GoLeft_SquareEmpty_ReturnTrue() {
        killTestMonster();
        testMap.pickUpItem();
        testMap.goRight();

        boolean result = testMap.goLeft();

        assertTrue(result);
        assertEquals(Cardinality.WEST, testMap.getHeroFacing());
    }

    @Test
    void GoLeft_SquareNotEmpty_ReturnFalse() {
        boolean result = testMap.goLeft();

        assertFalse(result);
        assertEquals(Cardinality.WEST, testMap.getHeroFacing());
    }

    @Test
    void GoUp_SquareEmpty_ReturnTrue() {
        testMap.pickUpItem();

        testMap.setHeroFacing(Cardinality.WEST);
        boolean result = testMap.goUp();

        assertTrue(result);
        assertEquals(Cardinality.NORTH, testMap.getHeroFacing());
    }

    @Test
    void GoUp_SquareNotEmpty_ReturnFalse() {
        testMap.setHeroFacing(Cardinality.WEST);
        boolean result = testMap.goUp();

        assertFalse(result);
        assertEquals(Cardinality.NORTH, testMap.getHeroFacing());
    }

    @Test
    void GoRight_SquareEmpty_ReturnTrue() {
        killTestMonster();
        testMap.pickUpItem();

        boolean result = testMap.goRight();

        assertTrue(result);
        assertEquals(Cardinality.EAST, testMap.getHeroFacing());
    }

    @Test
    void GoRight_SquareNotEmpty_ReturnFalse() {
        boolean result = testMap.goRight();

        assertFalse(result);
        assertEquals(Cardinality.EAST, testMap.getHeroFacing());
    }

    @Test
    void GoDown_SquareEmpty_ReturnTrue() {
        boolean result = testMap.goDown();

        assertTrue(result);
        assertEquals(Cardinality.SOUTH, testMap.getHeroFacing());
    }

    @Test
    void GoDown_SquareNotEmpty_ReturnFalse() {
        testMap.goDown();
        testMap.setHeroFacing(Cardinality.NORTH);

        boolean result = testMap.goDown();

        assertFalse(result);
        assertEquals(Cardinality.SOUTH, testMap.getHeroFacing());
    }

    @Test
    void PickUpItem_PickUpAmulet_ReturnTrueSetHeroEscaped() {
        killTestMonster();

        boolean result = testMap.pickUpItem();

        assertTrue(result);
        assertTrue(testMap.heroEscaped);
    }

    @Test
    void PickUpItem_PickUpOtherItem_ReturnTrue() {
        boolean result = testMap.pickUpItem();

        assertTrue(result);
        assertEquals("""
                 #######
                 #     #
                 # # # #
                 # # # #
                 ### # #
                !^   # \s
                 #   # #
                ########""", testMap.toString());
    }

    @Test
    void PickUpItem_NoItemInFront_ReturnFalse() {
        testMap.goDown();

        boolean result = testMap.pickUpItem();
        assertFalse(result);
    }

    @Test
    void CheckHeroDead_HealthGreaterThanZero_NoChange() {
        testMap.checkHeroDead();
        assertNull(testMap.heroEscaped);

        killTestMonster();
        testMap.pickUpItem();

        testMap.checkHeroDead();
        assertTrue(testMap.heroEscaped);
    }

    @Test
    void CheckHeroDead_HealthLessThanZero_SetHeroEscapedFalse() {
        testMap.getHero().changeHealth(-39);
        testMap.checkHeroDead();
        assertNull(testMap.heroEscaped);

        testMap.getHero().changeHealth(-1);
        testMap.checkHeroDead();
        assertFalse(testMap.heroEscaped);

        testMap.getHero().changeHealth(-1);
        testMap.checkHeroDead();
        assertFalse(testMap.heroEscaped);
    }

    @Test
    void Attack_MonsterHit_ReturnTrue() {
        testMap.goRight();
        boolean result = testMap.attack();
        assertTrue(result);

        testMap.attack();
        testMap.attack();
        testMap.attack();
        testMap.attack();
        assertEquals("""
                 #######
                 #     #
                 # # # #
                 # # # #
                $### # #
                !%   # \s
                 #   # #
                ########""", testMap.toString());
    }

    @Test
    void Attack_MonsterNotHit_ReturnFalse() {
        // Attack up in the default position
        assertFalse(testMap.attack());

        // Move position and try attacking different objects
        // Attack up
        testMap.goDown();
        assertFalse(testMap.attack());

        // Attack right
        testMap.goRight();
        assertFalse(testMap.attack());

        // Attack down
        testMap.goDown();
        assertFalse(testMap.attack());

        // Attack left
        testMap.goLeft();
        assertFalse(testMap.attack());
    }

    void killTestMonster() {
        testMap.goRight();
        testMap.attack();
        testMap.attack();
        testMap.attack();
        testMap.attack();
        testMap.attack();
    }
}
