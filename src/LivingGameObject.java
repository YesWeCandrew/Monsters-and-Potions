package src;

public abstract class LivingGameObject extends GameObject {

    public LivingGameObject(char charRepresentation, char charRepresentation1, String name, int healthPoints, int attackPoints, Item[] items, String[] phrases, String description) {
        super(charRepresentation);
        this.charRepresentation = charRepresentation1;
        this.name = name;
        this.healthPoints = healthPoints;
        this.attackPoints = attackPoints;
        this.items = items;
        this.phrases = phrases;
        this.description = description;
    }

// ATTRIBUTES


        private char charRepresentation; // the char representation of the object

        private String name; // the name of the object

        private int healthPoints; // the number of health points
        private int attackPoints; // the number of attack points

        /**
        The items that the object holds. If it is the user/hero, this is the
        inventory. If it is a monster then it is the items dropped when it is
        killed.
         */
        private Item[] items;

        /**
        Things that the living object can say when speak() is called.
         */
        private String[] phrases;

        private String description; // The help-text / description of the object


    // GETTERS AND SETTERS


        public void setName(String name) {
            this.name = name;
        }

        public void setHealthPoints(int healthPoints) {
            this.healthPoints = healthPoints;
        }

        public void setAttackPoints(int attackPoints) {
            this.attackPoints = attackPoints;
        }

        public void setItems(Item[] items) {
            this.items = items;
        }

        public void setPhrases(String[] phrases) {
            this.phrases = phrases;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public int getHealthPoints() {
            return healthPoints;
        }

        public int getAttackPoints() {
            return attackPoints;
        }

        public Item[] getItems() {
            return items;
        }

        public String[] getPhrases() {
            return phrases;
        }

        public String getDescription() {
            return description;
        }


    // GENERAL METHODS

        // TODO: Implement these methods

    /**
     * Reduces the health points of the object by the integer x.
     * @param x the amount to reduce the objects health by
     * @return true if health was reduced and object still alive, false if
     * the hit caused the object to die (healthPoints <=0)
     */
    public boolean reduceHealth(int x) {
        return true;
    }

    /**
    Increases the health points of the object by the integer x.
     @param x the amount to increase the objects health by
     */
    public void increaseHealth(int x) {
    }

    /**
    Reduces the attack points of the object by the integer x.
     @param x the amount to reduce the objects attackPoints by
     */
    public void reduceAttack(int x) {
    }

    /**
    Increases the health points of the object by the integer x.
     @param x the amount to increase the objects attackPoints by
     */
    public void increaseAttack(int x){

    }

    /**
    Returns the nth item in the item list.
     @param n the (0 start) index in the array of the item to return
     */
    public Item getItem(int n) {
        return new Item('*',"FIXME","FIXME","FIXME");
    }



}
