package map;

/**
 * This class allows us to store the position of important GameObjects, like the
 * player so that they can be accessed easily. If we don't end up using it we can
 * just remove it.
 */
public class Position {

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the position of the block in front of the current position,
     * given the cardinality provided. For instance if facing East at
     * [3,4] moving forwards means going to position [4,4], but if facing North
     * at [3,4] moving forwards means going to position [3,3].
     *
     * @author Andrew Howes
     *
     * @param cardinality the direction to move in
     *
     * @return the position one ahead of current in the directon specified
     */
    public Position positionInFront(Cardinality cardinality) {
        switch (cardinality) {
            case NORTH -> { return new Position(x,y-1); }
            case SOUTH -> { return new Position(x,y+1); }
            case EAST -> { return new Position(x+1,y); }
            case WEST -> { return new Position(x-1,y); }
            default -> { return null; }
        }
    }

    /**
     * Returns the string instruction to move in the given direction
     *
     * @author Andrew Howes
     *
     * @param cardinality the direction to move in
     * @param action should be Move or Face. To describe type of action
     *
     * @return the string instruction to move that way
     */
    public static String actions(Cardinality cardinality, String action) {
        switch (cardinality) {
            case NORTH -> { return "(↑) "+action+" up"; }
            case EAST -> { return "(→) "+action+" right"; }
            case SOUTH -> { return "(↓) "+action+" down"; }
            case WEST -> { return "(←) "+action+" left"; }
            default -> { return null; }
        }
    }
}
