package objects;

public abstract class GameObject {

    public GameObject(char charRepresentation) {
        this.charRepresentation = charRepresentation;
    }

    private char charRepresentation; // The character that will represent the object in the UI

    public char getChar(){
        return charRepresentation;
    }

    public void setChar(char charRepresentation) {
        this.charRepresentation = charRepresentation;
    }

    /**
     * Helper function for Map.allPossibleActions()
     * Given an object and whether the player is facing it, it returns information
     * about the item
     * @param isFacing is the player facing the item
     * @return a relevant action for the item by default returns ""
     * @author Andrew Howes
     */
    public String actionOptions(boolean isFacing) {
        return "";
    }
}