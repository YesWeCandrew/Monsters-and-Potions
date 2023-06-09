package objects;

import objects.GameObject;

public abstract class LivingGameObject extends GameObject {

    public LivingGameObject(char charRepresentation, String name, int healthPoints, int attackPoints, String[] phrases, String description) {
        super(charRepresentation);
        this.name = name;
        this.healthPoints = healthPoints;
        this.attackPoints = attackPoints;
        this.phrases = phrases;
        this.description = description;
    }

        private String name; // the name of the object
        private int healthPoints; // the number of health points
        private int attackPoints; // the number of attack points


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

        public String[] getPhrases() {
            return phrases;
        }

        public String getDescription() {
            return description;
        }

    /**
     * Reduces the health points of the object by the integer x.
     * @param x the amount to reduce the objects health by
     * @return true if health was reduced and object still alive, false if
     * the hit caused the object to die (healthPoints <=0)
     * @author Andrew Howes
     */
    public boolean reduceHealth(int x) {
        // throw an exception if x is negative
        if (x < 0) {
            throw new IllegalArgumentException("x must be positive");
        }
        else if(healthPoints - x <= 0) {
            healthPoints = 0;
            return false;
        }
        else {
            healthPoints -= x;
            return true;
        }
    }

    /**
    Increases the health points of the object by the integer x.
     @param x the amount to increase the objects health by
     @author Andrew Howes
     */
    public void increaseHealth(int x) {
        // throw an exception if x is negative
        if (x < 0) {
            throw new IllegalArgumentException("x must be positive");
        }
        else {
            healthPoints += x;
        }
    }

    /**
     Changes the health points of the object by the positive or negative
     integer x.
     @param x the amount to change the objects health by
     @author Andrew Howes
     */
    public void changeHealth(int x) {
            healthPoints += x;
    }

    /**
    Changes the attack points of the object by the positive or negative
     integer x.
     @param x the amount to change the objects attackPoints by
     @author Andrew Howes
     */
    public void changeAttack(int x) {
            attackPoints += x;
    }


}
