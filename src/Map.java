package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Map {

    /**
    The map which is an array of arrays of GameObjects.
    X = column
    Y = row

     */
    private static GameObject[][] map;
    static final String SAVE_FILE_PATH = "src//saves";
    private static final short size = 8;
    private static final String WALL_CHARACTER = "X";
    private static final String VACANT_CHARACTER = "_";
    /**
     * Generates the Map (and all the other objects using their relevant
     * constructors), using the csv and JSON (or other file type tbd) provided
     * @param pathToCSV the path to the map csv file
     * @param pathToJSON the path to the JSON or XML or whatever file
     */
    public Map(String pathToCSV,String pathToJSON) {

        GameObject[][] initialMap =  loadMapFromCSV(pathToCSV);
        map = loadEntitiesFromJSON(initialMap,pathToJSON);

    }

    private GameObject[][] loadEntitiesFromJSON(GameObject[][] initialMap, String pathToJSON) {
        GameObject[][] retMap = initialMap;

        //Load json file

        //Go through file.


        Hero hero = new Hero('H',"Jeff",100,100,null,null,"A hero.");
        String jsonObject = writeJSONObject(hero);
        Hero hero2 = new Hero('H',"Greg",200,100,null,null,"A greg.");
        String jsonObject2 = writeJSONObject(hero);

        String finalJson = jsonObject+jsonObject2;
        writeJSONTOFile(finalJson);

        return retMap;
    }

    private void writeJSONTOFile(String json){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        try (FileWriter writer = new FileWriter(SAVE_FILE_PATH+"//newJsonFile.json")) {
            gson.toJson(json, writer);
            System.out.println("Written!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }




    private String writeJSONObject(GameObject object){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        String json = gson.toJson(object);
        return json;
    }

    public static String readJSON(String filePath){
        Gson gson = new Gson();
        try {
            BufferedReader br = new BufferedReader(new FileReader(SAVE_FILE_PATH + "//" + filePath+".json"));
            String result = gson.toJson(br);
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        new Map("dummySave","");
        //readJSON("newJsonFile");
        map = loadMapFromCSV("dummySave");
        saveMapCSV("greg");

    }


    /**
     * Constructs a blank game objectMap with walls based on the wall
     * layout of the given map csv file.
     * @param pathToCSV the path to the map csv file
     * @return the corresponding GameObject map.
     */
    private static GameObject[][] loadMapFromCSV(String pathToCSV) {


        //Declare restricted size of the map
        GameObject[][] returnMap = new GameObject[size][size];

        //Initialise parameters for csv file
        String line;
        String splitBy = ",";
        try
        {
        //Parse the CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(SAVE_FILE_PATH + "//" + pathToCSV +".csv"));
            short row = 0;
            //Read each line, splitting into walls and spaces.
            while ((line = br.readLine()) != null)
            {
                // Split the line into segments based on comma as separator
                String[] gameRow = line.split(splitBy);

                //Convert to game objects and place into the map.
                for (int i = 0; i < gameRow.length && i < size; i++) {

                    if (gameRow[i].equals(WALL_CHARACTER)){
                        returnMap[i][row] = new Wall();
                    } else{
                        returnMap[i][row] = null;
                    }

                }
                row++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

//        //Printing out test map
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map.length; j++) {
//                if(map[j][i] instanceof Wall){
//                    System.out.print("X");
//                } else{
//                    System.out.print("_");
//                }
//                if(j ==7){
//                    System.out.println("");
//                }
//            }
//        }

        return returnMap;
    }
    /**
     * Saves the current map as a CSV to local storage.
     * @param fileName the name of the new CSV filesave.
     */
    public static void saveMapCSV(String fileName) {
        //initialise string array to collect map data.
        List<String[]> data = new ArrayList<>();

        //Convert map to String 2D Array
        for (int i = 0; i < size; i++) {
            //Construct the CSV row.
            String[] row = new String[size];
            for (int j = 0; j < size; j++) {
                if ((map[j][i] != null) && (map[j][i] instanceof Wall)) {
                    row[j] = WALL_CHARACTER;
                } else {
                    row[j] = VACANT_CHARACTER;
                }
            }
            //Place in data array.
            data.add(row);
        }
        //Setup file
        File file = new File(SAVE_FILE_PATH + "//" + fileName + ".csv");
        try {
            // Create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // Create CSVWriter with ',' as separator
            CSVWriter writer = new CSVWriter(outputfile, ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            //Write data to CSV file.
            writer.writeAll(data);
            // Closing writer connection
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        /**
     *
     * @return
     */
    public static GameObject createGameObject(){
        GameObject retGameObject = null;

        return retGameObject;
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
     * Returns the GameObject in the map at the provided position
     * @param position the position to return the object of
     * @throws ArrayIndexOutOfBoundsException if the position is out of bounds.
     * @return the GameObject at the given position
     */
    public GameObject getObjectAt(Position position) throws ArrayIndexOutOfBoundsException {
        return map[position.getX()][position.getY()];
    }

    /**
     * Sets the provided object at the given position.
     * Will return true if the object was successfully placed
     * Will return false if:
     * - The position is off the board, or
     * - There is already an object at the given position
     * To replace an object, first use the removeObjectAt function, then
     * call this function.
     * @param position the position to place the object
     * @param object the object to place
     * @return true if successfully placed, otherwise false
     */
    public boolean setObjectAt(Position position, GameObject object) {
        // Wrapped in try block in case the position is off the board
        // The assert function also ensures that the catch is called if there
        // is already an object at that position.
        try {
            // Makes sure that the position exists and is empty
            assert map[position.getX()][position.getY()] == null;

            map[position.getX()][position.getY()] = object;
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets the given position to null.
     * Note that this does so *safely*.
     * If the position is off the board nothing will happen
     * @param position the position to set to null.
     */
    public void clearPosition(Position position) {

        try {
            map[position.getX()][position.getY()] = null;
        }

        catch (Exception ignored) {
        }
    }

    /**
     * Returns a boolean describing whether an object exists at the position.
     * NOTE: this function will return false if the position is off the board.
     * Use isEmpty to check if there is NO object and the cell exists.
     * @param position the position to check
     * @return true if an object exists, otherwise false
     */
    public boolean hasObject(Position position) {
        try {
            // this line ensures that the program will move to the catch lines
            // if the position is out of bounds, or if there is not an object.
            assert map[position.getX()][position.getY()] != null;
            return true;
        }

        catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks first that the position is on the board, if it is the function
     * returns the negation of hasObject(position). If it is off the board,
     * the function returns false;
     * @param position the position to check for emptiness
     * @return whether the position is on the board and empty
     */
    public boolean isEmpty(Position position) {
        if (position.getX() > map.length - 1 | position.getY() > map.length - 1) {
            return false;
        } else {
            return !hasObject(position);
        }
    }

    /**
     * Moves the hero in the cardinality/direction they are facing.
     * If hero is facing any other objects or the edge of the board,
     * the hero will not move and the function will return False.
     * @return true if the hero was successfully moved forwards, else returns
     * false
     */
    public boolean moveHero() {
        // The position to try to move the hero to
        Position moveTo = heroPositionReference.positionInFront(heroFacing);

        // If the position is empty (it exists, and it does not already have
        // an object
        if (isEmpty(moveTo)) {
            // Copy the hero to that point in the array
            setObjectAt(moveTo,getObjectAt(heroPositionReference));

            // Remove the hero from its original position
            clearPosition(heroPositionReference);

            // change the hero pointer to the hero's new position
            heroPositionReference = moveTo;

            return true;
        } else {

            return false;
        }

    }

    /**
     * Turns the direction that the hero is facing by 90 degrees left
     */
    public void turnHeroLeft() {
        switch (heroFacing) {
            case NORTH -> heroFacing = Cardinality.WEST;
            case WEST -> heroFacing = Cardinality.SOUTH;
            case SOUTH -> heroFacing = Cardinality.EAST;
            case EAST -> heroFacing = Cardinality.NORTH;
        }
    }

    /**
     * Turns the direction that the hero is facing by 90 degrees right
     */
    public void turnHeroRight(){
        switch (heroFacing) {
            case NORTH -> heroFacing = Cardinality.EAST;
            case EAST -> heroFacing = Cardinality.SOUTH;
            case SOUTH -> heroFacing = Cardinality.WEST;
            case WEST -> heroFacing = Cardinality.NORTH;
        }
    }

    /**
     * Returns the hero.
     * Will fail if the heroPositionReference is not pointing at the
     * hero position on the map.
     * @return the Hero.
     */
    public Hero getHero(){
        return (Hero) getObjectAt(heroPositionReference);
    }

    /**
     * Picks up the item directly in front of the player in the direction they
     * are facing
     * @return true if the object is successfully picked up, otherwise false
     */
    public boolean pickUpItem() {
        try {
            Item item = (Item) getObjectAt(heroPositionReference.positionInFront(heroFacing));
            ((Hero) getObjectAt(heroPositionReference)).pickUpItem(item);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Attacks the Monster in front of the hero. if the object in front of
     * the hero is not a monster, it returns false.
     * Otherwise, this will reduce the health points of the Monster by the
     * attackPoints of the Hero everytime it is called.
     * Note that this function will remove the Monster from the map when killed.
     * @return true if the monster is hit, otherwise if there is no monster, returns false
     */
    public boolean attack(){
        try {
            Position monstersPosition = heroPositionReference.positionInFront(heroFacing);
            Monster monster = (Monster) getObjectAt(monstersPosition);

            // Reduce the monsters health points by the hero's attack points.
            // If the monster is still alive after this, return true
            if (monster.reduceHealth(getHero().getAttackPoints())){
                return true;
            }
            // Else if the monster is dead remove it from the board and
            // replace it with either the first item or null if the monster has
            // no items
            else {
                Item drop = null;

                if (monster.getItemToDrop() != null) {
                    drop = monster.getItemToDrop();
                }

                clearPosition(monstersPosition);
                setObjectAt(monstersPosition, drop);
            }
            return true;
        } catch (Exception e) {

            // If there is no monster to attack
            return false;
        }
    }

    public void getHelp(){}


}
