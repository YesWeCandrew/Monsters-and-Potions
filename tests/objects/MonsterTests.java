package objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonsterTests {

    private static Monster testMonster;

    @BeforeAll
    static void setUpTestMonster() {
        String[] testPhrases = {"GRRR!", "sss...", "I want to suck your blood!"};
        testMonster = new Monster('!', "testMonster", 10, 10,
                testPhrases, "This is a test monster.", null);
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.MILLISECONDS)
    void speakTest() {
        ArrayList<String> returnedPhrases = new ArrayList<>();

        // Check that calling speak can return all possible phrases
        while (returnedPhrases.size() != testMonster.getPhrases().length) {
            String phrase = testMonster.speak();

            if (!returnedPhrases.contains(phrase))
                returnedPhrases.add(phrase);
        }
    }

    @Test
    void actionOptionsTests() {
        assertEquals("(A) Attack testMonster", testMonster.actionOptions(null, true));
        assertEquals(" to face testMonster", testMonster.actionOptions(null, false));
    }
}
