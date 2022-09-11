

import java.util.ArrayList;

public class Hero extends LivingGameObject{

    private ArrayList<src.Item> items;
    private int itemsSize;

    public Hero(char charRepresentation, String name, int healthPoints, int attackPoints, String[] phrases, String description, ArrayList<src.Item> items) {
        super(charRepresentation, name, healthPoints, attackPoints, phrases, description);
        this.items = items;

        // The itemsize for the hero. Set by the game, rather than the developer
        // To make displaying it easier
        this.itemsSize = 4;
    }

    public boolean pickUpItem(src.Item item){
        // if duplicate items are allowed
        return items.add(item);

        // if duplicate items are not allowed
        /*if (items.contains(item)){
            return false;
        }
        else {
            return items.add(item);
        }*/
    }

    public src.Item discardItem(int x) {
        return items.remove(x);
    }

    /**
     Returns the nth item in the item list.
     @param n the (0 start) index in the array of the item to return
     */
    public src.Item getItem(int n) {
        return new src.Item('*',"FIXME","FIXME","FIXME");
    }



    // IDK what other kind of methods we want for just the hero, maybe

    // ALSO should consider making this a singleton type class as it can't have
    // more than one player / user / hero

}
