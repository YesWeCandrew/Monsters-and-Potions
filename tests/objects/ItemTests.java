package objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTests {

    private static Item testItem;

    @BeforeAll
    static void setUpItem() {
        testItem = new Item('%', "testItem", "This item is a test.",
                "health", 5);
    }

    @Test
    void ActionOptions_IsFacingTrue_ReturnsPickUpInstruction(){
        assertEquals("(P) Pick up testItem - This item is a test.", testItem.actionOptions(null, true));
    }

    @Test
    void ActionOptions_IsFacingFalse_ReturnsFaceInstruction() {
        assertEquals(" to face testItem", testItem.actionOptions(null, false));
    }
}
