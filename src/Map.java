package src;

public class Map {

    /**
    The map which is an array of arrays of GameObjects.
    X = column
    Y = row

     */
    private GameObject[][] map;

    /**
     * Generates the Map (and all the other objects using their relevant
     * constructors), using the csv and JSON (or other file type tbd) provided
     * @param pathToCSV the path to the map csv file
     * @param pathToJSON the path to the JSON or XML or whatever file
     */
    public Map(String pathToCSV,String pathToJSON) {
    }

    /**
    To easily find the player in the map. Is called Reference to just make it
    clear that it is just a reference to the cell in the map that holds the
    Hero object
     */
    private Position heroPositionReference;

    private Cardinality heroFacing;

    /**
     * Returns the GameState as a string ready to display in terminal
     * @param x the x coordinate of the middle cell to be displayed
     * @param y the y coordinate of the middle cell to be displayed
     * @param size the number of rows and columns to display around x,y
     *             (will always be square, but we could change this)
     * @return a string representation of the current GameState
     */
    public String displayArea(int x, int y, int size) {
        assert size >= 1;
        assert x >= 0;
        assert y >= 0;
        return "fixme";
    }



    /**
     * Moves the hero. Will need some sort of input
     */
    public void moveHero(Cardinality cardinality) {
    }

    public void pickUpItem() {}

    public void attack(){}

    public void getHelp(){}


}
