package map;

import objects.Hero;
import objects.Item;
import objects.Monster;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTests {

    static Map testMap = new Map("dummySave","");

    /*
    testMap:
    _,X,X,X,X,X,X,X
    _,X,_,_,_,_,_,X
    _,X,_,X,_,X,_,X
    _,X,_,X,_,X,_,X
    %,X,X,X,_,X,_,X
    !,^,_,_,_,X,_,_
    _,X,_,_,_,X,_,X
    X,X,X,X,X,X,X,X

    Where:
    ! = Hero (sam)
    ^ = Monster (Demonspawn)
    % = Item (Health Potion)
     */

    @BeforeAll
    public static void setUpMap() {
        // Adds the hero at 0,5
        Position heroPos = new Position(0,5);
        testMap.setObjectAt(
                heroPos,
                new Hero(
                        '!',
                        "sam",
                        5,
                        5,
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
                        5,
                        5,
                        null,
                        "Will hurt you! Better be prepared.",
                        null
                ));

        Position itemPos = new Position(0,4);
        testMap.setObjectAt(
                itemPos,
                new Item(
                        '%',
                        "Health Potion",
                        "Increases health by five",
                        "health",
                        5));
    }


    @Test
    /**
     * @author Andrew Howes
     */
    void TestAllPossibleActions(){
        String allPos1 = testMap.allPossibleActions().toString();
        testMap.goRight();
        String allPos2 = testMap.allPossibleActions().toString();
        testMap.goDown();
        String allPos3 = testMap.allPossibleActions().toString();

        String expected1 = "[(P) Pick up Health Potion - Increases health by five, (→) Turn right to face Demonspawn, (↓) Move down]";
        String expected2 = "[(↑) Turn up to face Health Potion, (A) Attack Demonspawn, (↓) Move down]";
        String expected3 = "[(↑) Move up]";

        assertEquals(allPos1,expected1);
        assertEquals(allPos2,expected2);
        assertEquals(allPos3,expected3);
    }
}
