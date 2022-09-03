package src;

public class Monster extends LivingGameObject{
    public Monster(char charRepresentation, char charRepresentation1, String name, int healthPoints, int attackPoints, Item[] items, String[] phrases, String description) {
        super(charRepresentation, charRepresentation1, name, healthPoints, attackPoints, items, phrases, description);
    }

    public String speak() {
        // should probably return a random string from the phrases
        return getPhrases()[0];
    }
}
