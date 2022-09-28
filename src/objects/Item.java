package objects;

import objects.GameObject;

public class Item extends GameObject {

    private final String name;
    private final String description;

    /**
     * This must either be the word health or the word attack. Determines
     * whether the item will affect the health of the character or the attack
     */
    private final actionEffect actionEffects;

    /**
     * The amount to increase or decrease the attack or health points of the hero
     */
    private final int actionChange;


    /**
     *
     * @param charRepresentation the character to represent the item
     * @param name the name of the item
     * @param description the items description
     * @param actionEffects either "health" or "attack" anything else will throw error
     * @param actionChange the amount to change health or attack by (can be negative)
     */
    public Item(char charRepresentation, String name, String description, String actionEffects, int actionChange) {
        super(charRepresentation);
        this.name = name;
        this.description = description;

        // must be either attack or health.
        switch (actionEffects) {
            case "attack" -> this.actionEffects = actionEffect.attack;
            case "health" -> this.actionEffects = actionEffect.health;
            default -> throw new IllegalArgumentException();
        }

        this.actionChange = actionChange;
    }

    public enum actionEffect {
        health,
        attack
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getActionChange() {return actionChange;}

    public actionEffect getActionEffect() {return actionEffects;}

    /**
     * Helper function for Map.allPossibleActions()
     * Given an item and whether the player is facing it, it returns information
     * about the item
     * @param isFacing is the player facing the item
     * @return a relevant action for the item
     */
    @Override
    public String actionOptions(boolean isFacing) {
        if (isFacing) {
            return "(P) Pick up " + getName() + " - " + getDescription();
        } else return " to face " + getName();
    }
}
