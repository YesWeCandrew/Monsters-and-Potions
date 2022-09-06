package src;

public class Monster extends LivingGameObject{
    public Monster(char charRepresentation, String name, int healthPoints, int attackPoints, Item[] items, String[] phrases, String description) {
        super(charRepresentation, name, healthPoints, attackPoints, items, phrases, description);
    }

    public String speak() {
        // should probably return a random string from the phrases
        return getPhrases()[0];
    }
}
