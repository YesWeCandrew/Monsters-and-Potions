package objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HeroTests {

    private static Hero testHero;

    @BeforeEach
    void setUpHero() {
        testHero = new Hero('%', "TestHero", 10, 10,
                new String[5], "Test hero description", new ArrayList<>());
    }

    @Test
    void PickUpItemTest_SizeGreaterThan_ReturnTrue(){
        Item healthItem = new Item('@', "Soup", "Health soup", "health", 5);
        Item attackItem = new Item('@', "Dart", "Poison dart", "attack", 5);

        assertTrue(testHero.pickUpItem(healthItem));
        assertTrue(testHero.pickUpItem(attackItem));

        assertEquals(healthItem, testHero.getItem(0));
        assertEquals(attackItem, testHero.getItem(1));

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> testHero.getItem(2));
        assertEquals("Index 2 out of bounds for length 2", exception.getMessage());
    }

    @Test
    void PickUpItemTest_InventoryFull_ReturnFalse() {
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item('@', "Sword", "Sharp sword", "attack", 5));
        itemList.add(new Item('@', "Spear", "Long spear", "attack", 2));
        itemList.add(new Item('@', "Potion", "Health potion", "health", 8));
        itemList.add(new Item('@', "Potion", "Health potion", "health", 8));

        Hero testHero = new Hero('%', "TestHero", 10, 10,
                new String[5], "Test hero description", itemList);

        // case where items in inventory are equal to the maximum size
        assertFalse(testHero.pickUpItem(new Item('@', "Shield", "Small shield", "health", 3)));

        itemList.add(new Item('@', null, null, "attack", 0));
        itemList.add(new Item('@', null, null, "health", 0));

        testHero = new Hero('%', "TestHero", 10, 10,
                new String[5], "Test hero description", itemList);

        // case where items in inventory are greater than the maximum size
        assertFalse(testHero.pickUpItem(new Item('@', null, null, "health", 3)));
    }

    @Test
    void DiscardItem_InventoryEmpty_ReturnNull() {
        assertNull(testHero.discardItem(0));
    }

    @Test
    void DiscardItem_InventoryContainsItems_ReturnDiscardedItem() {
        Item healthItem = new Item('@', "Soup", "Health soup", "health", 5);
        Item attackItem = new Item('@', "Dart", "Poison dart", "attack", 5);

        testHero.pickUpItem(healthItem);
        testHero.pickUpItem(attackItem);

        // Check discarding second item
        assertEquals(attackItem, testHero.discardItem(1));
        assertNull(testHero.discardItem(1));
        assertEquals(10, testHero.getAttackPoints());

        // Check discarding first item
        assertEquals(healthItem, testHero.discardItem(0));
        assertNull(testHero.discardItem(0));
        assertEquals(10, testHero.getHealthPoints());
    }
}
