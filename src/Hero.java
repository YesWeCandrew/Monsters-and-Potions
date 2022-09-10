package src;

import java.util.ArrayList;

public class Hero extends LivingGameObject{

    private ArrayList<Item> items;
    private int itemsSize;

    public Hero(char charRepresentation, String name, int healthPoints, int attackPoints, String[] phrases, String description, ArrayList<Item> items) {
        super(charRepresentation, name, healthPoints, attackPoints, phrases, description);
        this.items = items;

        // The itemsize for the hero. Set by the game, rather than the developer
        // To make displaying it easier
        this.itemsSize = 4;
    }

    public boolean pickUpItem(Item item){
        return items.add(item);
    }

    public Item discardItem(int x) {
        return items.remove(x);
    }

    /**
     Returns the nth item in the item list.
     @param n the (0 start) index in the array of the item to return
     */
    public Item getItem(int n) {
        return new Item('*',"FIXME","FIXME","FIXME");
    }



    // IDK what other kind of methods we want for just the hero, maybe

    // ALSO should consider making this a singleton type class as it can't have
    // more than one player / user / hero

}
