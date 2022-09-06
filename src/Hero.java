package src;

public class Hero extends LivingGameObject{

    public Hero(char charRepresentation, String name, int healthPoints, int attackPoints, Item[] items, String[] phrases, String description) {
        super(charRepresentation, name, healthPoints, attackPoints, items, phrases, description);
    }

    public void pickUpItem(Item item){
        // would just add to this.items;
    }

    public void discardItem(int x) {
        // remove item at index x from the list
    }

    // IDK what other kind of methods we want for just the hero, maybe

    // ALSO should consider making this a singleton type class as it can't have
    // more than one player / user / hero

}
