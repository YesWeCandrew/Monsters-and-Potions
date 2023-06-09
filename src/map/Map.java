package map;

import com.opencsv.CSVWriter;
import main.Main;
import objects.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static main.Main.scanner;
import static main.Menu.*;

public class Map {

    /**
     To easily find the player in the map. Is called Reference to just make it
     clear that it is just a reference to the cell in the map that holds the
     Hero object
     */
    private static Position heroPositionReference;
    private static Cardinality heroFacing;

    public void setHeroPositionReference(Position heroPositionReference) {
        this.heroPositionReference = heroPositionReference;
    }

    public void setHeroFacing(Cardinality heroFacing) {
        this.heroFacing = heroFacing;
    }

    public Cardinality getHeroFacing() {
        return this.heroFacing;
    }

    /**
    The map which is an array of arrays of GameObjects.
    X = column
    Y = row

     */
    private static GameObject[][] map;
    static final String SAVE_FILE_PATH = System.getProperty("user.dir")+"//src//saves";
    private static final String WALL_CHARACTER = String.valueOf(new Wall().getChar());
    private static final String VACANT_CHARACTER = "_";
    private static int X_SIZE;  // AKA number of columns/length
    private static int Y_SIZE; // AKA number of rows/height


    public static void main(String[] args) {
        loadXANDYSize("dummySave");
        loadMapFromCSV("dummySave");
        Item startingItems = new Item('I', "test-item", null, "health",  10);
        Item startingItems2 = new Item('I', "test-item", "heal", "health",  10);
        ArrayList<Item> items = new ArrayList<>();
        items.add(startingItems);
        items.add(startingItems2);
        Hero hero = new Hero('H',"Greg",100,100,null,null,null);
        map[2][3] = hero;
        Monster monster = new Monster('M',"The big monster",30,40,null,"A big monster",null);
        map [4][5] = monster;
        Item item = new Item('I',"Fancy Sword","Can kill and stab. its a good sword.","attack",0);
        map [4][6] = item;
        Item item2 = new Item('I',"Fancy Health Potion","Can kill and stab. its a good potion.","health",0);
        map [0][1] = item;
        hero.pickUpItem(item2);
        //loadEntitiesFromJSON("99-test-2022-09-26");
        //heroEscaped = false;
        heroFacing = Cardinality.WEST;
        heroPositionReference = new Position(0,0);
        //save(56,"anotherSave");
        loadMapFromCSV("54-anotherSave");
        //loadEntitiesFromJSON("54-anotherSave");

    }

    // boolean is null during the game.
    // Game play is set to loop while (heroEscaped == null)
    // If player dies this heroEscaped set to false.
    // If players wins by picking up the Amulet then it is set to true.
    // We can then display a winning or loosing screen.
    public Boolean heroEscaped;


    /**
     * Generates the Map (and all the other objects using their relevant
     * constructors), using the csv and JSON (or other file type tbd) provided
     *
     * @param pathToCSV  the path to the map csv file
     * @param pathToJSON the path to the JSON or XML or whatever file
     */
    public Map(String pathToCSV,String pathToJSON) {
        this(pathToCSV);
        loadEntitiesFromJSON(pathToJSON);
    }

    /**
     * Generates the Map without any objects from CSV. Just for testing.
     * @param pathToCSV the path to the csv file
     * @author Andrew Howes
     */
    public Map(String pathToCSV) {
        loadXANDYSize(pathToCSV);
        loadMapFromCSV(pathToCSV);
    }

    private static void loadXANDYSize(String pathToCSV) {
        obtainXSize(pathToCSV);
        obtainYSize(pathToCSV);
    }

    /**
     * for testing purposes only
     *
     * @param map the map to use
     * @param heroPos the position of the hero
     */
    public Map(GameObject[][] map, Position heroPos) {
        this.heroPositionReference = heroPos;
        map[heroPos.getX()][heroPos.getY()] = new Hero('H', "test-hero", 100, 100, null, "for testing purposes", new ArrayList<>());
        heroFacing = Cardinality.NORTH;
        this.map = map;
        this.X_SIZE = getWidth();
        this.Y_SIZE = getHeight();
    }

    private static GameObject[][] initialiseBoard(int sizeX, int sizeY){
        return new GameObject[sizeY][sizeX];
    }

    /**
     * Saves the current game state to file.
     * This process saves a csv file to store the map
     * with a json file to store the entities.
     *
     * @param id the primary key of the file.
     * @param saveFileName the name of the save file.
     *
     * @return true if successful.
     */
    public boolean save(int id, String saveFileName){
        //Validate ID and saveFileName inputs
        if (id < 0) {
            throw new RuntimeException("ID must be a positive integer.");
        }

        if (saveFileName == null || saveFileName.length() == 0 || saveFileName.isBlank()) {
            throw new RuntimeException("saveFileName cannot be blank.");
        }

        //Construct the save file name.
        String splitBy = "-";
        String fileNameDescriptor = id + splitBy + saveFileName;
        if (map == null) {
            throw new RuntimeException("Board is empty.");
        }

        //Save the map.
        saveMapCSV(fileNameDescriptor);
        //Save the entities.
        saveEntitiesToJSON(fileNameDescriptor);

        return true;
    }

    /**
     * Constructs a blank game objectMap with walls based on the wall
     * layout of the given map csv file.
     *
     * @param pathToCSV the path to the map csv file.
     */
    private static void loadMapFromCSV(String pathToCSV) {

        //Declare restricted size of the map
        // TODO: 2021-09-28  make this dynamic
        obtainYSize(pathToCSV);
        GameObject[][] returnMap = initialiseBoard(X_SIZE,Y_SIZE);

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
                for (int col = 0; col < gameRow.length && col < Y_SIZE; col++) {

                    if (gameRow[col].equals(WALL_CHARACTER)){
                        returnMap[row][col] = new Wall();
                    } else{
                        returnMap[row][col] = null;
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

    private static int obtainYSize(String pathToCSV) {
        BufferedReader bufferedReader;
        int count = 0;
        try {
            bufferedReader = new BufferedReader(new FileReader(SAVE_FILE_PATH + "//" + pathToCSV +".csv"));
            while(bufferedReader.readLine() != null)
            {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Y_SIZE = count;
        return count;
    }

    private static int obtainXSize(String pathToCSV) {
        BufferedReader br = null;
        int size = 0;
        try {
            br = new BufferedReader(new FileReader(SAVE_FILE_PATH + "//" + pathToCSV + ".csv"));
            String line = br.readLine();
            String splitBy = ",";
            String[] columns = line.split(splitBy);
            size = columns.length;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            X_SIZE = size;
        }
        return size;
    }

    /**
     * Saves the current map as a CSV to local storage.
     *
     * @param fileName the name of the new CSV file to save.
     */
    public static void saveMapCSV(String fileName) {
        //initialise string array to collect map data.
        List<String[]> data = new ArrayList<>();

        //Convert map to String 2D Array
        for (int i = 0; i < Y_SIZE; i++) {
            //Construct the CSV row.
            String[] row = new String[X_SIZE];
            for (int j = 0; j < X_SIZE; j++) {
                if ((map[i][j] != null) && (map[i][j] instanceof Wall)) {
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
            FileWriter outputFile = new FileWriter(file);

            // Create CSVWriter with ',' as separator
            CSVWriter writer = new CSVWriter(outputFile, ',',
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

    private void loadEntitiesFromJSON(String pathToJSON) {
        //Initialise parser for JSON files.
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(SAVE_FILE_PATH+"//"+ pathToJSON+ ".json"))
        {
            //Read JSON file
            Object json = jsonParser.parse(reader);

            JSONArray gameObjects = (JSONArray) json;
            //Iterate over objects array
            for (Object object: gameObjects) {
                JSONObject jsonObject = (JSONObject) object;
                //Obtain the game object and place within the board.
                parseGameObject(jsonObject);
            }

        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseGameObject(JSONObject jsonObject) {
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
        ArrayList<Item> items = null;
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
                if(heroObject.get("phrases") != null) {
                    phrases = heroObject.get("phrases").toString().split(",");
                } else {
                    phrases = null;
                }
                // TODO change the below two lines instead of add null to description and items
                if(heroObject.get("description") != null){
                    description = heroObject.get("description").toString();
                } else {
                    description = null;
                }
                // if hero has no items, then items will be null
                if(heroObject.get("items") != null){
                    // store the item instead of the reference to the item
                    items = (ArrayList<Item>) heroObject.get("items");
                } else {
                    items = null;
                }
                //items = heroObject.get("items") == null ? null : (ArrayList<Item>) heroObject.get("items");
                //ArrayList<Item> items = (ArrayList<Item>) heroObject.get("items");
                Hero hero = new Hero(charRep.charAt(0), name, healthPoints, attackPoints, phrases, description, items);
                //position = heroObject.get("position").toString();
                x = Integer.parseInt(heroObject.get("positionX").toString());
                y = Integer.parseInt(heroObject.get("positionY").toString());
                map[y][x] = hero;

                String cardinality = heroObject.get("cardinality").toString();
                System.out.println(cardinality);
                heroFacing= Cardinality.valueOf(cardinality);

                String escaped = heroObject.get("escaped") == null ? null : heroObject.get("escaped").toString();
                heroEscaped = Boolean.parseBoolean(escaped);

                heroPositionReference = new Position(x,y);
            }
            case "monster" -> {
                JSONObject monsterObject = (JSONObject) jsonObject.get(resultStr);
                charRep = monsterObject.get("charRepresentation").toString();
                name = monsterObject.get("name").toString();
                healthPoints = Integer.parseInt(monsterObject.get("healthPoints").toString());
                attackPoints = Integer.parseInt(monsterObject.get("attackPoints").toString());
                if(monsterObject.get("phrases") != null) {
                    phrases = monsterObject.get("phrases").toString().split(",");
                } else {
                    phrases = null;
                }
                // TODO change the below two lines instead of add null to description
                if(monsterObject.get("description") != null){
                    description = monsterObject.get("description").toString();
                } else {
                    description = null;
                }
                Item dropItem = null;
                if(monsterObject.get("items") != null){
                    // store the item instead of the reference to the item
                    dropItem = (Item) monsterObject.get("item");
                } else {
                    dropItem = null;
                }
                //ArrayList<Item> items = (ArrayList<Item>) heroObject.get("items");
                Monster monster = new Monster(charRep.charAt(0), name, healthPoints, attackPoints, phrases, description, dropItem);
                x = Integer.parseInt(monsterObject.get("positionX").toString());
                y = Integer.parseInt(monsterObject.get("positionY").toString());

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
                x = Integer.parseInt(itemObject.get("positionX").toString());
                y = Integer.parseInt(itemObject.get("positionY").toString());
                map[x][y] = item;
            }
            default -> throw new RuntimeException("Invalid object type");
        }
    }

    /**
     * Collects the attributes and positions for each GameObject
     * on the map, transforms and saves a new JSON file to local storage,
     * named with the given fileName.
     *
     * @author Ethan Teber-Rossi
     *
     * @param fileName the name of the new JSON file.
     */
     private void saveEntitiesToJSON(String fileName){
        //Collect items, monsters, hero on board put them into array list.
        Hero hero = null;
        Position heroPosition = null;

        ArrayList<Monster> monsters = new ArrayList<>();
        ArrayList<Position> monsterPositions = new ArrayList<>();

        ArrayList<Item> itemsOnBoard = new ArrayList<>();
        ArrayList<Position> itemPositions = new ArrayList<>();

        //Traverse through board
         // TODO 29/09/2022 make the size of the board dynamic
        for (int i = 0; i < Y_SIZE; i++) {
            for (int j = 0; j < X_SIZE; j++) {

                // TODO: 24/09/2022   switch statement this
                //CASE: Hero
                if (map[i][j] instanceof Hero) {
                    hero = (Hero) map[i][j];
                    heroPosition = new Position(j, i);
                }
                //CASE: Monster
                else if (map[i][j] instanceof Monster) {
                    monsters.add((Monster) map[i][j]);
                    monsterPositions.add(new Position(i,j));
                }
                //CASE: Item:
                else if (map[i][j] instanceof Item) {
                    itemsOnBoard.add((Item) map[i][j]);
                    itemPositions.add(new Position(i,j));
                }
                // TODO CASE: Empty??
            }
        }

        //Construct array of all the objects for the JSON file.
        JSONArray jsonElements = new JSONArray();

        //Create hero json object.
        if(hero == null){
            throw new RuntimeException("Writing JSON file failed: no hero found on board.");
        } else{
            jsonElements.add(createJSONHero(hero, heroPosition));
        }
        //Create monster json objects;
        for (int i = 0; i < monsters.size(); i++) {
            jsonElements.add(createJSONMonster(monsters.get(i), monsterPositions.get(i)));
        }
        //Create items json objects.
        for (int i = 0; i < itemsOnBoard.size(); i++) {
            JSONObject itemObject = createJSONItem(itemsOnBoard.get(i), itemPositions.get(i));
            jsonElements.add(itemObject);
        }

        //Write to file.
        writeJSONtoStorage(jsonElements, fileName);
    }

    /**
     * Writes the JSONArray to the save folder.
     *
     * @author Ethan Teber-Rossi
     *
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
     * Constructs a JSON Object from a Hero Object,
     * including its attributes and its position on the board.
     *
     * @author Ethan Teber-Rossi
     *
     * @param hero the hero object on the board.
     * @param position the position of the hero object on the board.
     *
     * @return The JSON Encoding of the hero object.
     */
    private JSONObject createJSONHero(Hero hero, Position position){
        //Place the hero attributes & its position into a JSON Object.
        JSONObject heroAttributes = new JSONObject();
        heroAttributes.put("positionX", position.getX());
        heroAttributes.put("positionY", position.getY());
        heroAttributes.put("charRepresentation", String.valueOf(hero.getChar()));
        heroAttributes.put("name", hero.getName());
        heroAttributes.put("healthPoints", hero.getHealthPoints());
        heroAttributes.put("attackPoints", hero.getAttackPoints());
        heroAttributes.put("phrases", hero.getPhrases());
        heroAttributes.put("description", hero.getDescription());

        heroAttributes.put("cardinality", heroFacing.toString());
        if (heroEscaped == null)
            heroAttributes.put("escaped", null);
        else
            heroAttributes.put("escaped", heroEscaped.toString());
        //Items
        // jsonObject.put("items",hero.getItems());
        // TODO 29/09/2022 add items to hero
        // use a for loop to add each item to the array list, note that itemSize is always set as 4 in the Hero class.
        for (int i = 0; i < hero.getItemsSize(); i++) {
            heroAttributes.put("item"+i, createJSONItemForHero(hero.getItem(i)));
        }
        //Construct the nested hero JSON Object.
        JSONObject jsonHero = new JSONObject();
        String heroPlaceHolder = "hero";
        jsonHero.put(heroPlaceHolder, heroAttributes);
        return jsonHero;
    }


    /**
     *
     * @param monster
     * @param position
     *
     * @return
     */
    private static JSONObject createJSONMonster(Monster monster, Position position){
        //Place the monster attributes & its position into a JSON Object.
        JSONObject monsterDetails = new JSONObject();
        monsterDetails.put("positionX",position.getX());
        monsterDetails.put("positionY",position.getX());
        monsterDetails.put("charRepresentation",String.valueOf(monster.getChar()));
        monsterDetails.put("name",monster.getName());
        monsterDetails.put("healthPoints",monster.getHealthPoints());
        monsterDetails.put("attackPoints",monster.getAttackPoints());
        monsterDetails.put("phrases",monster.getPhrases());
        monsterDetails.put("description",monster.getDescription());
        // jsonObject.put("items",monster.getItems());
        Item itemDrop = monster.getItemToDrop();
        monsterDetails.put("itemToDrop", createJSONItem(itemDrop, null));
        //Construct the nested monster JSON Object.
        JSONObject jsonMonster = new JSONObject();
        String monsterPlaceHolder = "monster";
        jsonMonster.put(monsterPlaceHolder,monsterDetails);

        return jsonMonster;
    }


    private static JSONObject createJSONItem(Item item, Position position) {
        JSONObject itemAttributes = new JSONObject();

        if (item == null) {
            itemAttributes.put("positionX", null);
            itemAttributes.put("positionY", null);
            itemAttributes.put("charRepresentation", null);
            itemAttributes.put("name", null);
            itemAttributes.put("description", null);
            itemAttributes.put("actionEffects", null);
            itemAttributes.put("actionChange", null);

            JSONObject jsonItem = new JSONObject();
            String itemPlaceHolder = "item";
            jsonItem.put(itemPlaceHolder, itemAttributes);

            return jsonItem;
        }

        if (position != null)
            itemAttributes.put("positionX", position.getX());
        itemAttributes.put("positionY", position.getY());
        itemAttributes.put("charRepresentation", String.valueOf(item.getChar()));
        itemAttributes.put("name", item.getName());
        itemAttributes.put("description", item.getDescription());
        itemAttributes.put("actionEffects", item.getActionEffect().toString());
        itemAttributes.put("actionChange", item.getActionChange());

        //Construct the nested item JSON Object.
        JSONObject jsonItem = new JSONObject();
        String itemPlaceHolder = "item";
        jsonItem.put(itemPlaceHolder, itemAttributes);

        return jsonItem;
    }

    public static JSONObject createJSONItemForHero(Item item){
        JSONObject itemAttributes = new JSONObject();
        itemAttributes.put("charRepresentation",String.valueOf(item.getChar()));
        itemAttributes.put("name",item.getName());
        itemAttributes.put("description",item.getDescription());
        itemAttributes.put("actionEffects",item.getActionEffect().toString());
        itemAttributes.put("actionChange",item.getActionChange());

        //Construct the nested item JSON Object.
        JSONObject jsonItem = new JSONObject();
        String itemPlaceHolder = "item";
        jsonItem.put(itemPlaceHolder,itemAttributes);
        return jsonItem;
    }

    /**
     * deprecated
     *
     * Returns the GameState as a string ready to display in terminal
     *
     * @param x the x coordinate of the middle cell to be displayed
     * @param y the y coordinate of the middle cell to be displayed
     * @param size the number of rows and columns to display around x,y
     *             (will always be square, but we could change this)
     *
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
        return map[0].length;
    }

    public int getHeight() {
        return map.length;
    }

    public static Position getHeroPositionReference() {
        return heroPositionReference;
    }

    /**
     * Returns the src.objects.GameObject in the map at the provided position
     *
     * @param position the position to return the object of
     *
     * @throws ArrayIndexOutOfBoundsException if the position is out of bounds.
     *
     * @return the src.objects.GameObject at the given position
     */
    public GameObject getObjectAt(Position position) throws ArrayIndexOutOfBoundsException {
        return map[position.getY()][position.getX()];
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
     * To replace an object, first use the clearPosition function, then
     * call this function.
     *
     * @param position the position to place the object
     * @param object the object to place
     *
     * @return true if successfully placed, otherwise false
     */
    public boolean setObjectAt(Position position, GameObject object) {
        boolean set = false;
        if (isEmpty(position)) {
            map[position.getY()][position.getX()] = object;
            set = true;
        }

        return set;
    }

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

    /**
     * Sets the given position to null.
     * Note that this does so *safely*.
     * If the position is off the board nothing will happen
     *
     * @param position the position to set to null.
     */
    public void clearPosition(Position position) {
        try {
            map[position.getY()][position.getX()] = null;
        } catch (Exception ignored) {
        }
    }

    /**
     * Returns whether the position given is on the board
     *
     * @author Andrew Howes
     *
     * @param position the position to check
     *
     * @return whether the position is on the board
     */
    private boolean isOnBoard(Position position) {
        return (position.getX() < X_SIZE &&
                position.getY() < Y_SIZE &&
                position.getX() >= 0 &&
                position.getY() >= 0);
    }

    /**
     * Returns a boolean describing whether an object exists at the position.
     * NOTE: this function will return false if the position is off the board.
     * Use isEmpty to check if there is NO object and the cell exists.
     *
     * @author Andrew Howes
     *
     * @param position the position to check
     *
     * @return true if an object exists, otherwise false
     */
    private boolean hasObject(Position position) {
        if (!isOnBoard(position))
            return false;
        else
            return getObjectAt(position) != null;
    }

    /**
     * Checks first that the position is on the board and has no objects.
     * If it is off the board, the function returns false
     *
     * @author Andrew Howes
     *
     * @param position the position to check for emptiness
     *
     * @return whether the position is on the board and empty
     */
    public boolean isEmpty(Position position) {
        return !hasObject(position) && isOnBoard(position);
        // isOnBoard is not redundant as hasObject will return false when a
        // position is off the board, so !hasObject will return true but if this
        // function is given a position that is off the board it should return
        // false
    }

    /**
     * Moves the hero in the cardinality/direction they are facing.
     * If hero is facing any other objects or the edge of the board,
     * the hero will not move and the function will return False.
     *
     * @return true if the hero was successfully moved forwards, else returns
     * false
     */
    public boolean moveHero() {
        // If the position is empty (it exists, and it does not already have
        // an object)
        Position inFront = positionInFrontOfHero();
        if (isEmpty(inFront) && isOnBoard(inFront)) {
            // Copy the hero to that point in the array
            setObjectAt(positionInFrontOfHero(), getObjectAt(heroPositionReference));

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
     * Turns the hero to face west and tries to move left.
     * Will ALWAYS turn the hero. Will also moveHero if west square is empty
     *
     * @return whether the hero successfully moved left after turning to face west
     */
    public boolean goLeft() {
        heroFacing = Cardinality.WEST;
        return moveHero();
    }

    /**
     * Turns the hero to face north and tries to move upwards.
     * Will ALWAYS turn the hero. Will also moveHero if north square is empty
     *
     * @return whether the hero successfully moved up after turning to face north
     */
    public boolean goUp() {
        heroFacing = Cardinality.NORTH;
        return moveHero();
    }

    /**
     * Turns the hero to face east and tries to move right.
     * Will ALWAYS turn the hero. Will also moveHero if east square is empty
     *
     * @return whether the hero successfully moved right after turning to face east
     */
    public boolean goRight() {
        heroFacing = Cardinality.EAST;
        return moveHero();
    }

    /**
     * Turns the hero to face south and tries to move downwards.
     * Will ALWAYS turn the hero. Will also moveHero if south square is empty
     *
     * @return whether the hero successfully moved down after turning to face south
     */
    public boolean goDown() {
        heroFacing = Cardinality.SOUTH;
        return moveHero();
    }

    /**
     * Returns the hero.
     * Will fail if the heroPositionReference is not pointing at the
     * hero position on the map.
     *
     * @return the Hero.
     */
    public Hero getHero() {
        return (Hero) getObjectAt(heroPositionReference);
    }

    /**
     * Picks up the item directly in front of the player in the direction they
     * are facing
     *
     * @author Andrew Howes
     *
     * @return true if the object is successfully picked up, otherwise false
     */
    public boolean pickUpItem() {
        try {
            Item item = (Item) getObjectAt(positionInFrontOfHero());
            boolean success = getHero().pickUpItem(item);
            checkHeroDead(); // Items can kill a Hero.

            // If you've picked up the item, and it's the Amulet, you have won!
            if (success && item.getName().equals("Amulet")) {
                heroEscaped = true;
            }

            // Remove the item from the map
            if (success) {
                clearPosition(positionInFrontOfHero());
            }

            // regardless of whether you've won or died or just still going
            // returns success because you picked up an item!
            return success;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks whether the health points of the hero have fallen to <= 0.
     * If they have set heroEscaped to false, which should flag game to end.
     *
     * @author Andrew Howes
     */
    public void checkHeroDead() {
        if (getHero().getHealthPoints() <= 0) {
            heroEscaped = false;
        }
    }

    /**
     * Discards the item at the nth index of the Hero's inventory and places it
     * on the map in front of the hero.
     *
     * Returns true if successfully discarded. Will return false if:
     * - There is no object at that index in the array, or
     * - There is no position in front of the hero (is the edge of the map), or
     * - There is an object already in that place
     *
     * @author Andrew Howes
     *
     * @param n the number of the item to discard (starting at 0)
     *
     * @return whether the item could be discarded
     */
    public boolean discardItem(int n) {
        if (isEmpty(positionInFrontOfHero())) {
            Item item = getHero().discardItem(n);
            checkHeroDead(); // discarding an item can kill a hero.
            if (item != null) {
                setObjectAt(positionInFrontOfHero(), item);
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
     *
     * @return true if the monster is hit, otherwise if there is no monster, returns false
     */
    public boolean attack() {
        try {
            Position monstersPosition = positionInFrontOfHero();
            Monster monster = (Monster) getObjectAt(monstersPosition);

            // Reduce the monsters health points by the hero's attack points.
            // If the monster is still alive after this the hero gets
            // attacked by the monster and return true
            if (monster.reduceHealth(getHero().getAttackPoints())) {
                // Hero is attacked by the monster
                getHero().reduceHealth(monster.getAttackPoints());
                checkHeroDead();
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

    /**
     * Produces all possible moves that the player can take, given their position
     * on the board
     *
     * @author Andrew Howes
     *
     * @return an arrayList of strings describing possible moves
     */
    public ArrayList<String> allPossibleActions() {
        ArrayList<String> allPossibleActions = new ArrayList<>();

        // Stores information about the valid positions
        class PositionRef {
            public final Position position;
            public final Cardinality fromHero;
            public final boolean isFacing;

            public PositionRef(Position position, Cardinality fromHero, boolean isFacing) {
                this.position = position;
                this.fromHero = fromHero;
                this.isFacing = isFacing;
            }
        }

        Main.listeners.clear();
        Main.listeners.add(c -> {
            switch(Character.toLowerCase(c)) {
                case 'w':
                    goUp();
                    return true;
                case 's':
                    goDown();
                    return true;
                case 'a':
                    goLeft();
                    return true;
                case 'd':
                    goRight();
                    return true;
                case 'p':
                    return pickUpItem();
                case 'o':
                    return attack();
                case '1':
                    return discardItem(0);
                case '2':
                    return discardItem(1);
                case '3':
                    return discardItem(2);
                case '4':
                    return discardItem(3);
                case 'q':
                    System.out.println("Please enter a save name: ");
                    String userSaveName = scanner.next();
                    if (userSaveName == null || userSaveName.isBlank()) {
                        save(123,"save");
                    } else {
                        save(123, userSaveName);
                    }

                    Main.main(new String[]{});
                default:
                    return false;
            }
        });

//      Getting valid positions surrounding the hero (those on the board)
        ArrayList<PositionRef> validPositions = new ArrayList<>();
        Cardinality[] cards = {Cardinality.NORTH, Cardinality.EAST, Cardinality.SOUTH, Cardinality.WEST};

        for (Cardinality card : cards) {
            Position pos = getHeroPositionReference().positionInFront(card);
            if (isOnBoard(pos)) {
                PositionRef posRef = new PositionRef(pos, card, card == heroFacing);
                validPositions.add(posRef);
            }
        }

        for (PositionRef posRef : validPositions) {
            String actionAtPosition;

            // if there is nothing in that position
            if (isEmpty(posRef.position)) {
                // add that you can move in that direction
                allPossibleActions.add(Position.actions(posRef.fromHero,"Move"));
            }

            // if there is something in the position, and the hero is facing it
            else if (posRef.isFacing) {
                // Get the string of what you can do when facing that option
                actionAtPosition = getObjectAt(posRef.position).actionOptions(getHero(), true);

                // If there is action text add it
                if (!Objects.equals(actionAtPosition, "")) {
                    allPossibleActions.add(actionAtPosition);
                }
            }

            // if there is something in the position, but the hero isn't facing
            else {
                // Returns the way the character needs to move to face the object
                String directionToMove = Position.actions(posRef.fromHero, "Turn");
                // Get the string of what you can do if you turned to face it
                actionAtPosition = getObjectAt(posRef.position).actionOptions(getHero(), false);
                // If there is action text add it
                if (!Objects.equals(actionAtPosition, "")) {
                    allPossibleActions.add(directionToMove + actionAtPosition);
                }
            }
        }
        return allPossibleActions;
    }

    public GameObject[][] getMap() {
        return map;
    }

    public int getXSize() {
        return X_SIZE;
    }

    public int getYSize() {
        return Y_SIZE;
    }

    /**
     * Iterates over each row and column to generate a string representation
     * of the map
     *
     * @author Andrew Howes
     *
     * @return the map as a string.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < Y_SIZE; y ++) {
            for (int x = 0; x < X_SIZE; x++) {
                if (map[y][x] == null) {stringBuilder.append(" ");}
                else {stringBuilder.append(map[y][x].getChar());}
            }
            if (y < Y_SIZE-1) {stringBuilder.append("\n");}
        }

        return stringBuilder.toString();
    }

    /**
     * Compares two maps. However, as map is static, it cannot be compared.
     * Really only assess heroPositionReference, heroFacing and heroEscaped.
     *
     * @param o the object to compare
     *
     * @return whether heroPositionReference, heroFacing and heroEscaped are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Map map = (Map) o;
        return Objects.equals(heroPositionReference, map.heroPositionReference) && heroFacing == map.heroFacing && Objects.equals(heroEscaped, map.heroEscaped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heroPositionReference, heroFacing, heroEscaped);
    }
}
