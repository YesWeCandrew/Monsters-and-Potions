package objects;

public class Monster extends LivingGameObject{
    public Monster(char charRepresentation, String name, int healthPoints, int attackPoints, String[] phrases, String description, Item itemToDrop) {
        super(charRepresentation, name, healthPoints, attackPoints, phrases, description);
        this.itemToDrop = itemToDrop;
    }

    private Item itemToDrop;

    public String speak() {
        // should probably return a random string from the phrases
        return getPhrases()[0];
    }

    public Item getItemToDrop() {
        return itemToDrop;
    }
}
