package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

        loadMapFromCSV(pathToCSV);

    }

    public static void main(String[] args) {
        loadMapFromCSV("dummySave");
    }


    /**
     * Constructs a blank game objectMap with walls based on the wall
     * layout of the given map csv file.
     * @param pathToCSV the path to the map csv file
     * @return the corresponding GameObject map.
     */
    private static GameObject[][] loadMapFromCSV(String pathToCSV) {
        
        String line = "";
        String splitBy = ",";
        final String SAVE_FILE_PATH = "src//saves";


        short size = 8;
        GameObject[][] map = new GameObject[size][size];
        String [][] printMap = new String[size][size];
        short row = 0;


        try
        {
        //Parse the CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(SAVE_FILE_PATH + "//" + pathToCSV +".csv"));

            //Read each line, splitting into walls and spaces.
            while ((line = br.readLine()) != null)
            {
                // Split the line into segments based on comma as separator
                String[] gameRow = line.split(splitBy);

                //Convert to game objects and place into the map.
                for (int i = 0; i < gameRow.length && i < size; i++) {

                    //Create "create gameObject function
                    GameObject object = createGameObject();

                    //temp map
                    printMap[i][row] = gameRow[i];
                    if (gameRow[i].equals("_")){
                        printMap[i][row] = " ";
                    }

                }
                row++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //Printing out test map
        for (int i = 0; i < printMap.length; i++) {
            for (int j = 0; j < printMap.length; j++) {
                System.out.print(printMap[j][i]);
                if(j ==7){
                    System.out.println("");
                }
            }
        }



        return map;
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
