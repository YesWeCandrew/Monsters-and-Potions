package objects;

import java.util.ArrayList;

public class Hero extends LivingGameObject{

    private ArrayList<Item> items;
    private final int maxItemsSize;

    public Hero(char charRepresentation, String name, int healthPoints, int attackPoints,
                String[] phrases, String description, ArrayList<Item> items) {
        super(charRepresentation, name, healthPoints, attackPoints, phrases, description);
        this.items = items;

        // The itemsize for the hero. Set by the game, rather than the developer
        // To make displaying it easier
        this.maxItemsSize = 4;
    }

    /**
     * Attempts to add the item to the Hero's inventory. If the object can be
     * added returns true and executes the action described by the object.
     *
     * If the object cannot be added (eg. inventory is  full) returns false
     * @param item the item to add
     * @return whether the object was successfully added
     * @author Andrew Howes
     */
    public boolean pickUpItem(Item item){
        // if items is null, create a new arraylist
        if(items == null){
            items = new ArrayList<>();
            items.add(item);
            return true;
        }
        else if (items.size() >= maxItemsSize) {
            return false;
        }
        else{
            // Adding the item to the inventory.
            items.add(item);
        }

        // Taking the action as described by the items actionEffect and actionChange
        switch (item.getActionEffect()) {
            case health ->
                this.changeHealth(item.getActionChange());
            case attack ->
                this.changeAttack(item.getActionChange());
        }
        return true;
    }

    /**
     * Removes the item at the xth number of the inventory. Also undoes the
     * effects on health/armour
     *
     * @param x the number in the array to remove (starting at 0)
     * @return the item if it was removed successfully, or null otherwise
     * @author Andrew Howes
     */
    public Item discardItem(int x) {
        Item item;
        try {
            item = items.remove(x);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

        // Undoing the action as described by the items actionEffect and actionChange
        switch (item.getActionEffect()) {
            case health ->
                this.changeHealth(-item.getActionChange());
            case attack ->
                this.changeAttack(-item.getActionChange());
        }

        return item;
    }

    /**
     * Returns the nth item in the item list.
     *
     * @param n the (0 start) index in the array of the item to return
     * @author Andrew Howes
     */
    public Item getItem(int n) {
        return this.items.get(n);
    }

    public int getItemsSize() {
        if (this.items == null){
            return 0;
        }
        return this.items.size();
    }

    public int getMaxItemsSize() {
        return this.maxItemsSize;
    }


    // IDK what other kind of methods we want for just the hero, maybe

    // ALSO should consider making this a singleton type class as it can't have
    // more than one player / user / hero

}
