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
     * Moves the hero. Will need some sort of input
     */
    public void moveHero(Cardinality cardinality) {
    }

    public void pickUpItem() {}

    public void attack(){}

    public void getHelp(){}


}
