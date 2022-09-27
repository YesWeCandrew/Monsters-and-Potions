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



    /**
     * Helper function for Map.allPossibleActions()
     * Given an item and whether the player is facing it, it returns information
     * about the monster
     * @param isFacing is the player facing the item
     * @return a relevant action for the item
     */
    @Override
    public String actionOptions(boolean isFacing) {
        if (isFacing) {
            return "(A) Attack " + getName();
        } else return " to face " + getName();
    }
}
