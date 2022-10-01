package objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTests {
    @Test
    void actionOptionsTest(){
        Item testItem = new Item('%', "testItem", "This item is a test.",
                "health", 5);

        assertEquals("(P) Pick up testItem - This item is a test.", testItem.actionOptions(null, true));
        assertEquals(" to face testItem", testItem.actionOptions(null,false));
    }
}
