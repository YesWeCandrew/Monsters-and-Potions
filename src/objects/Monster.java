package objects;

import main.Main;

import java.util.Random;

public class Monster extends LivingGameObject{
    public Monster(char charRepresentation, String name, int healthPoints, int attackPoints, String[] phrases, String description, Item itemToDrop) {
        super(charRepresentation, name, healthPoints, attackPoints, phrases, description);
        this.itemToDrop = itemToDrop;
    }

    private Item itemToDrop;

    public String speak() {
        Random rand = new Random();
        return getPhrases()[rand.nextInt(getPhrases().length)];
    }

    public Item getItemToDrop() {
        return itemToDrop;
    }

    /**
     * Helper function for Map.allPossibleActions()
     * Given an item and whether the player is facing it, it returns information
     * about the monster
     *
     * @author Andrew Howes
     *
     * @param isFacing is the player facing the item
     *
     * @return a relevant action for the item
     */
    @Override
    public String actionOptions(Hero hero, boolean isFacing) {
        if (isFacing) {
            Main.listeners.add(c -> {
                boolean caught = false;
                if(Character.toLowerCase(c) == 'a' && hero != null) {
                    changeHealth(-25);
                    hero.changeAttack(-25);
                    caught = true;
                }

                return caught;
            });
            return "(O) Attack " + getName();
        }
        else {
            return " to face " + getName();
        }
    }
}
