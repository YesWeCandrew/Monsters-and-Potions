
public class Item extends GameObject {

    private String name;
    private String description;

    /*
    We need to decide what kind of actions items should make when they are
    picked up and how to represent them. For now just bodging it as a string
    But we should think more about this.
     */
    private String action;

    public Item(char charRepresentation, String name, String description, String action) {
        super(charRepresentation);
        this.name = name;
        this.description = description;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAction() {
        return action;
    }
}
