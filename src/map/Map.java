package map;

import com.opencsv.CSVWriter;
import objects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Map {

    /**
     To easily find the player in the map. Is called Reference to just make it
     clear that it is just a reference to the cell in the map that holds the
     Hero object
     */
    private Position heroPositionReference;
    private Cardinality heroFacing;
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

        loadMapFromCSV(pathToCSV);
        loadEntitiesFromJSON(pathToJSON);

    }

    /**
     * for testing purposes only
     *
     */
    public Map(GameObject[][] map, Position heroPos) {
        this.heroPositionReference = heroPos;
        map[heroPos.getX()][heroPos.getY()] = new Hero('H', "test-hero", 100, 100, null, "for testing purposes", null);
        heroFacing = Cardinality.NORTH;
        this.map = map;
    }

    private static GameObject[][] initialiseBoard(int sizeX, int sizeY){
        return new GameObject[sizeX][sizeY];
    }
    private static void loadEntitiesFromJSON(String pathToJSON) {

        //Initialise parser for JSON files.
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(SAVE_FILE_PATH+"//"+ pathToJSON+ ".json"))
        {
            //Read JSON file
            Object json = jsonParser.parse(reader);

            JSONArray gameObjects = (JSONArray) json;
            int laugh = 1;
            //Iterate over employee array
            for (Object object:
                 gameObjects) {
                JSONObject jsonObject = (JSONObject) object;
                parseGameObject(jsonObject);

                System.out.println(jsonObject);
            }

        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseGameObject(JSONObject jsonObject) {
        String type = jsonObject.toJSONString().substring(2,9);
        String resultStr = "";
        //Collect Object type.
        for (int i=0;i<type.length();i++)
        {
            //Determine if character is alphabetical.
            if (Character.isAlphabetic(type.charAt(i))) //returns true if both conditions returns true
            {
                //adding characters into empty string
                resultStr=resultStr+type.charAt(i);
            }
        }
        String charRep;
        String name;
        int healthPoints;
        int attackPoints;
        String[] phrases;
        String description;
        ArrayList<Item> items;
        String position;
        int x;
        int y;

        switch (resultStr) {
            case "hero" -> {
                JSONObject heroObject = (JSONObject) jsonObject.get(resultStr);
                charRep = heroObject.get("charRepresentation").toString();
                name = heroObject.get("name").toString();
                healthPoints = Integer.parseInt(heroObject.get("healthPoints").toString());
                attackPoints = Integer.parseInt(heroObject.get("attackPoints").toString());
                phrases = (String[]) heroObject.get("phrases");
                description = null;
                items = null;
                //ArrayList<Item> items = (ArrayList<Item>) heroObject.get("items");
                Hero hero = new Hero(charRep.charAt(0), name, healthPoints, attackPoints, phrases, description, items);
                position = heroObject.get("position").toString();
                x = Integer.parseInt(position.charAt(0) + "");
                y = Integer.parseInt(position.charAt(position.length() - 1) + "");
                map[x][y] = hero;
            }
            case "monster" -> {
                JSONObject monsterObject = (JSONObject) jsonObject.get(resultStr);
                charRep = monsterObject.get("charRepresentation").toString();
                name = monsterObject.get("name").toString();
                healthPoints = Integer.parseInt(monsterObject.get("healthPoints").toString());
                attackPoints = Integer.parseInt(monsterObject.get("attackPoints").toString());
                phrases = (String[]) monsterObject.get("phrases");
                description = null;
                Item dropItem = (Item) monsterObject.get("item");
                //ArrayList<Item> items = (ArrayList<Item>) heroObject.get("items");
                Monster monster = new Monster(charRep.charAt(0), name, healthPoints, attackPoints, phrases, description, dropItem);
                position = monsterObject.get("position").toString();
                x = Integer.parseInt(position.charAt(0) + "");
                y = Integer.parseInt(position.charAt(position.length() - 1) + "");

                //Place monster into map
                //setObjectAt();
                map[x][y] = monster;
            }
            case "item" -> {
                JSONObject itemObject = (JSONObject) jsonObject.get(resultStr);
                charRep = itemObject.get("charRepresentation").toString();
                name = itemObject.get("name").toString();
                if (itemObject.get("description") != null) {
                    description = itemObject.get("description").toString();
                } else {
                    description = "null";
                }
                String actionEffects = itemObject.get("actionEffects").toString();
                int actionChange = Integer.parseInt(itemObject.get("actionChange").toString());
                Item item = new Item(charRep.charAt(0), name, description, actionEffects, actionChange);
                position = itemObject.get("position").toString();
                x = Integer.parseInt(position.charAt(0) + "");
                y = Integer.parseInt(position.charAt(position.length() - 1) + "");
                map[x][y] = item;
            }
            default -> throw new RuntimeException("Invalid object type");
        }
    }

    /**
     Collects the attributes and positions for each GameObject
     on the map, transforms and saves a new JSON file to local storage,
     named with the given fileName.
     @author Ethan Teber-Rossi
     @author ..........
     @param fileName the name of the new JSON file.
     */
     private static void saveEntitiesToJSON(String fileName){
        //Collect items, monsters, hero on board put them into array list.
        Hero hero = null;
        Position heroPosition = null;

        ArrayList<Monster> monsters = new ArrayList<>();
        ArrayList<Position> monsterPositions = new ArrayList<>();

        ArrayList<Item> items = new ArrayList<>();
        ArrayList<Position> itemPositions = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            //Construct the CSV row.
            for (int j = 0; j < size; j++) {

                // TODO: 24/09/2022   switch statement this
                //CASE: Hero
                if (map[j][i] instanceof Hero) {
                 hero = (Hero) map[j][i];
                 heroPosition = new Position(j,i);
                }
                //CASE: Monster
                else if (map[j][i] instanceof Monster monster) {
                    monsters.add(monster);
                  monsterPositions.add(new Position(j,i));
                }
                //CASE: Item:
                else if (map[j][i] instanceof Item) {
                    Item item = (Item) map[j][i];
                    items.add(item);
                    itemPositions.add(new Position(j,i));
                }
            }
        }


        //Construct array of all the objects for the JSON file.
        JSONArray jsonElements = new JSONArray();

        //Create hero json object.
        if(hero == null){
            throw new RuntimeException("Writing JSON file failed: no hero found on board.");
        } else{
            jsonElements.add(createJSONHero(hero,heroPosition));
        }
        //Create monster json objects;
        for (int i = 0; i < monsters.size(); i++) {
            jsonElements.add(createJSONMonster(monsters.get(i), monsterPositions.get(i)));
        }
        //Create items json objects.
        for (int i = 0; i < items.size(); i++) {
            JSONObject itemObject = createJSONItem(items.get(i),itemPositions.get(i));
            jsonElements.add(itemObject);
        }

        //Write to file.
         writeJSONtoStorage(jsonElements,fileName);
    }

    /**
     *Writes the JSONArray to the save folder.
     * @author Ethan Teber-Rossi
     * @param json The JSONArray object
     * @param fileName the intended name for the new JSON file.
     */
    private static void writeJSONtoStorage(JSONArray json,String fileName){
        try (FileWriter file = new FileWriter(SAVE_FILE_PATH+"//"+ fileName+ ".json")) {
            file.write(json.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     Constructs a JSON Object from a Hero Object,
     including its attributes and its position on the board.
     @author Ethan Teber-Rossi
     @author ..........
     *
     * @param hero the hero object on the board.
     * @param position the position of the hero object on the board.
     * @return The JSON Encoding of the hero object.
     */
    private static JSONObject createJSONHero(Hero hero, Position position){
        //Place the hero attributes & its position into a JSON Object.
        JSONObject heroAttributes = new JSONObject();
        heroAttributes.put("position",position.getX() + ", "+ position.getY());
        heroAttributes.put("charRepresentation",String.valueOf(hero.getChar()));
        heroAttributes.put("name",hero.getName());
        heroAttributes.put("healthPoints",hero.getHealthPoints());
        heroAttributes.put("attackPoints",hero.getAttackPoints());
        heroAttributes.put("phrases",hero.getPhrases());
        heroAttributes.put("description",hero.getDescription());
        //Items
        // jsonObject.put("items",hero.getItems());

        //Construct the nested hero JSON Object.
        JSONObject jsonHero = new JSONObject();
        String heroPlaceHolder = "hero";
        jsonHero.put(heroPlaceHolder,heroAttributes);
        return jsonHero;
    }


    /**
     *
     * @param monster
     * @param position
     * @return
     */
    private static JSONObject createJSONMonster(Monster monster, Position position){
        //Place the monster attributes & its position into a JSON Object.
        JSONObject monsterDetails = new JSONObject();
        monsterDetails.put("position",position.getX() + ", "+ position.getY());
        monsterDetails.put("charRepresentation",String.valueOf(monster.getChar()));
        monsterDetails.put("name",monster.getName());
        monsterDetails.put("healthPoints",monster.getHealthPoints());
        monsterDetails.put("attackPoints",monster.getAttackPoints());
        monsterDetails.put("phrases",monster.getPhrases());
        monsterDetails.put("description",monster.getDescription());
        // jsonObject.put("items",monster.getItems());

        //Construct the nested monster JSON Object.
        JSONObject jsonMonster = new JSONObject();
        String monsterPlaceHolder = "monster";
        jsonMonster.put(monsterPlaceHolder,monsterDetails);

        return jsonMonster;
    }

    private static JSONObject createJSONItem(Item item, Position position){
        JSONObject itemAttributes = new JSONObject();
        itemAttributes.put("position",position.getX() + ", "+ position.getY());
        itemAttributes.put("charRepresentation",String.valueOf(item.getChar()));
        itemAttributes.put("name",item.getName());
        itemAttributes.put("actionEffects",item.getActionEffect().toString());
        itemAttributes.put("actionChange",item.getActionChange());

        //Construct the nested item JSON Object.
        JSONObject jsonItem = new JSONObject();
        String itemPlaceHolder = "item";
        jsonItem.put(itemPlaceHolder,itemAttributes);

        return jsonItem;
    }

    /**
     * Saves the current game state to file.
     * This process saves a csv file to store the map
     * with a json file to store the entities.
     * @param id the primary key of the file.
     * @param saveFileName the name of the save file.
     * @ return true if successful.
     */
    public static boolean save(int id,String saveFileName){
        //Validate ID and saveFileName inputs
        if (id < 0){
            throw new RuntimeException("ID must be a positive integer.");
        }
        if (saveFileName == null || saveFileName.length() == 0 || saveFileName.isBlank()) {
            throw new RuntimeException("saveFileName cannot be blank.");
        }

        //Obtain the date
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        //Construct the saveFileName
        String splitBy = "-";
        String fileNameDescriptor = id + splitBy + saveFileName + splitBy +strDate;
        if(map == null){
            throw new RuntimeException("Map does not exist.");
        }
        //Save the map.
        saveMapCSV(fileNameDescriptor);
        //Save the entities.
        saveEntitiesToJSON(fileNameDescriptor);

        return true;
    }



    public static void main(String[] args) {
        //readJSON("newJsonFile");
//        map = loadMapFromCSV("dummySave");
//         Hero hero = new Hero('H',"Jeff",100,100,null,null,null);
//        map[2][3] = hero;
//        Monster monster = new Monster('M',"The big monster",30,40,null,"A big monster",null);
//        map [4][5] = monster;
//        Item item = new Item('I',"Fancy Sword","Can kill and stab. its a good sword.","attack",0);
//        map [4][6] = item;
//        Item item2 = new Item('I',"Fancy Health Potion","Can kill and stab. its a good potion.","health",0);
//        map [0][1] = item;
//        saveGameState(04,"myGame");

        loadMapFromCSV("dummySave");
//        Hero hero = new Hero('H',"Jeff",100,100,null,null,null);
//        map[2][3] = hero;
//        Monster monster = new Monster('M',"The big monster",30,40,null,"A big monster",null);
//        map [4][5] = monster;
//        Item item = new Item('I',"Fancy Sword","Can kill and stab. its a good sword.","attack",0);
//        map [4][6] = item;
//        Item item2 = new Item('I',"Fancy Health Potion","Can kill and stab. its a good potion.","health",0);
//        map [0][1] = item;
        loadEntitiesFromJSON("99-test-2022-09-26");
        save(23,"dummyTest");

    }


    /**
     * Constructs a blank game objectMap with walls based on the wall
     * layout of the given map csv file.
     * @param pathToCSV the path to the map csv file
     */
    private static void loadMapFromCSV(String pathToCSV) {

        //Declare restricted size of the map
        GameObject[][] returnMap = initialiseBoard(size,size);

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

        map = returnMap;
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
     * Returns the GameState as a string ready to display in terminal
     * @param x the x coordinate of the middle cell to be displayed
     * @param y the y coordinate of the middle cell to be displayed
     * @param size the number of rows and columns to display around x,y
     *             (will always be square, but we could change this)
     * @return a string representation of the current GameState
     */
    public String displayArea(int x, int y, int size) {
        assert size >= 1;
        assert x >= 0 && x <= map.length;
        assert y >= 0 && y <= map[0].length;

        boolean bordered = true;
        int d = size * 2 + 1;
        String area = "", boarderChar = "@", wallChar = String.valueOf(new Wall().getChar());
        String horizontalBorder = bordered ? boarderChar.repeat(d + 2) : "";

        area += horizontalBorder; // Top Border
        for(int i = x - size; i >= d; i++) {
            area += bordered ? boarderChar : ""; // Left Border


            for(int j = y - size; j >= d; j++)
                area += i < 0 ? wallChar :
                        j < 0 ? wallChar :
                        i > map.length ? wallChar :
                        j > map[0].length ? wallChar :
                        map[j][i].getChar();

            area += (bordered ? boarderChar : "") + System.lineSeparator(); // Right Border + New Line
        }
        area += horizontalBorder; // Bottom Border

        return area;
    }

    public int getWidth() {
        return map.length;
    }

    public int getHeight() {
        return map[0].length;
    }

    public Position getHeroPositionReference() {
        return heroPositionReference;
    }

    /**
     * Returns the src.objects.GameObject in the map at the provided position
     * @param position the position to return the object of
     * @throws ArrayIndexOutOfBoundsException if the position is out of bounds.
     * @return the src.objects.GameObject at the given position
     */
    public GameObject getObjectAt(Position position) throws ArrayIndexOutOfBoundsException {
        return map[position.getX()][position.getY()];
    }

    private Position positionInFrontOfHero() {
        return heroPositionReference.positionInFront(heroFacing);
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
        boolean set = false;
        if(isEmpty(position)) {
            map[position.getX()][position.getY()] = object;
            set = true;
        }

        return set;

//        // Wrapped in try block in case the position is off the board
//        // The assert function also ensures that the catch is called if there
//        // is already an object at that position.
//        try {
//            // Makes sure that the position exists and is empty
//            assert map[position.getX()][position.getY()] == null;
//
//            map[position.getX()][position.getY()] = object;
//            return true;
//
//        } catch (Exception e) {
//            return false;
//        }
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
        boolean hasObject = false;
        int x = position.getX(), y = position.getY();
        if (x < 0 || y < 0 || x >= map.length || y >= map.length) {
            hasObject = map[position.getX()][position.getY()] != null;
        }

        return hasObject;

        // none-functional code
//        try {
//            // this line ensures that the program will move to the catch lines
//            // if the position is out of bounds, or if there is not an object.
//            assert map[position.getX()][position.getY()] != null;
//            return true;
//        }
//
//        catch (Exception e) {
//            return false;
//        }
    }

    /**
     * Checks first that the position is on the board, if it is the function
     * returns the negation of hasObject(position). If it is off the board,
     * the function returns false;
     * @param position the position to check for emptiness
     * @return whether the position is on the board and empty
     */
    public boolean isEmpty(Position position) {
        return !hasObject(position);
    }

    /**
     * Moves the hero in the cardinality/direction they are facing.
     * If hero is facing any other objects or the edge of the board,
     * the hero will not move and the function will return False.
     * @return true if the hero was successfully moved forwards, else returns
     * false
     */
    public boolean moveHero() {

        // If the position is empty (it exists, and it does not already have
        // an object
        if (isEmpty(positionInFrontOfHero())) {
            // Copy the hero to that point in the array
            setObjectAt(positionInFrontOfHero(),getObjectAt(heroPositionReference));

            // Remove the hero from its original position
            clearPosition(heroPositionReference);

            // change the hero pointer to the hero's new position
            heroPositionReference = positionInFrontOfHero();

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
            Item item = (Item) getObjectAt(positionInFrontOfHero());
            return getHero().pickUpItem(item);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Discards the item at the nth index of the Hero's inventory and places it
     * on the map in front of the hero.
     *
     * Returns true if successfully discarded. Will return false if:
     * - There is no object at that index in the array, or
     * - There is no position in front of the hero (is the edge of the map), or
     * - There is an object already in that place,
     * @param n the number of the item to discard (starting at 0)
     * @return whether the item could be discarded
     */
    public boolean discardItem(int n) {
        if (isEmpty(positionInFrontOfHero())) {
           Item item = getHero().discardItem(n);
           if (item != null) {
               setObjectAt(positionInFrontOfHero(),item);
               return true;
           }
        }

        return false;
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
            Position monstersPosition = positionInFrontOfHero();
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
