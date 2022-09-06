package src;

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
}