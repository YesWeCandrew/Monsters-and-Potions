package main;

import map.Map;
import map.Position;
import objects.GameObject;
import objects.Hero;
import objects.Item;
import objects.Wall;
import ui.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Interface {

    Pane main = null;

    Map map;
    ViewPort mapView;
    Pane inv_stats, interact_tooltip;

    public Interface(Map map) {
        this.map = map;

        this.main = createMain();
    }

    public Interface() {
        this(null);
    }

    private Pane getMain() {
        return main == null ? createMain() : main;
    }

    private Pane createMain() {
        Pane main = new Pane();
        int viewPortRadius = 5;

        int x = 0;
        // inventory + stats
        this.inv_stats = new Pane();
        createPlayerStatistics(1, 0, 23);
        createInventory(1, 3, 25);
        this.inv_stats.relocate(0, 0);
        this.inv_stats.setWidth(25);

        Separator leftSeperator = new Separator(viewPortRadius * 2 + 3, false, '|');
        leftSeperator.relocate(inv_stats.getX() + inv_stats.getWidth() + 1, 0);

        // map view
        this.mapView = new ViewPort(map, viewPortRadius, true);
        mapView.relocate(leftSeperator.getX() + 2, 0);
        mapView.maximizeSize();

        Separator rightSeperator = new Separator(viewPortRadius * 2 + 3, false, '|');
        rightSeperator.relocate(mapView.getX() + mapView.getWidth() + 1, 0);

        // interactions + tooltip view
        this.interact_tooltip = new Pane();
        createInteractions(1, 0, 25, 4);
        createToolTip(1, 5, 25);
        interact_tooltip.relocate(rightSeperator.getX() + 1, 0);
        interact_tooltip.maximizeSize();

        main.addElements(inv_stats, leftSeperator, interact_tooltip, rightSeperator, mapView);

        return main;
    }

    private void createPlayerStatistics(int x, int y, int maxWidth) {
        Hero hero = map.getHero();
        StatisticsBar healthPoints = new StatisticsBar("HP", 0, 100, 10, '*', '-', maxWidth);
        healthPoints.setListener(() -> hero.getHealthPoints());
        healthPoints.relocate(x, y);

        StatisticsBar actionPoints = new StatisticsBar("AP", 0, 100, 10, '*', '-', maxWidth);
        actionPoints.setListener(() -> hero.getAttackPoints());
        actionPoints.relocate(x, y + 1);

        this.inv_stats.addElements(healthPoints, actionPoints);
    }

    private void createInventory(int x, int y, int maxWidth) {
        Hero hero = map.getHero();
        TextField inventoryText = new TextField("Inventory:");
        inventoryText.relocate(x, y);

        Separator leftSeperator = new Separator(hero.getMaxItemsSize(), false, '|');
        leftSeperator.relocate(x, y + 1);

        TextList inventoryList = new TextList(hero.getMaxItemsSize(), maxWidth - 4);
        inventoryList.setListener(() -> {
            String[] items = new String[hero.getItemsSize()];
            for(int i = 0; i < items.length; i++)
                items[i] = hero.getItem(i).getName();

            return items;
        });
        inventoryList.maximizeSize();
        inventoryList.relocate(x + 2, y + 1);

        Separator rightSeperator = new Separator(hero.getMaxItemsSize(), false, '|');
        rightSeperator.relocate(x + inventoryList.getWidth(), y + 1);

        this.inv_stats.addElements(inventoryText, leftSeperator, inventoryList, rightSeperator);
    }

    private void createInteractions(int x, int y, int maxWidth, int maxHeight) {
        String text = "testing testing, one, two, three. hello everybody, here is something"; // TODO need to get interactions text (i.e. quests, phrases, ...)

        TextField textField = new TextField(maxWidth, maxHeight);
        textField.setText(text);
        textField.relocate(x, y);

        this.interact_tooltip.addElement(textField);
    }

    private void createToolTip(int x, int y, int maxWidth) {
        TextField toolTipText = new TextField("Options:");
        toolTipText.relocate(x, y);

        Separator leftSeperator = new Separator(5, false, '|');
        leftSeperator.relocate(x, y + 1);

        ArrayList<String> optionStrings = new ArrayList<>(Arrays.asList("(W) Move North", "(S) Move South", "(D) Move East", "(A) Move West")); // TODO need to get options from map (i.e. move left, attack, flee)
        TextList options = new TextList(optionStrings.size(), maxWidth - 4);
        options.relocate(x + 2, y + 1);

        Separator rightSeperator = new Separator(5, false, '|');
        rightSeperator.relocate(x + options.getWidth(), y + 1);

        for(String optionText : optionStrings) {
            options.addText(optionText);
        }

        this.interact_tooltip.addElements(toolTipText, leftSeperator, options, rightSeperator);
    }

    public void displayUI() {
        getMain();
        String[] render = main.getStringRender();
        String result = String.join(System.lineSeparator(), render);

        System.out.println(result);
        System.out.flush();
    }

    /**
     * test main method, temporary
     *
     * @param args
     */
    public static void main(String[] args) {
        GameObject[][] area = new GameObject[5][5];
        area[1][0] = new Wall(); // in front to the left
        area[3][0] = new Wall(); // in front to the right
        area[2][3] = new Wall(); // directly behind the hero

        Map map = new Map(area, new Position(2, 2));
        Interface display = new Interface(map);
        display.displayUI();
        System.out.println();

        map.moveHero();
        display.displayUI();
        System.out.println();

        map.getHero().pickUpItem(new Item('P', "Health Potion", "Test potion", "health", 0));
        map.goLeft();
        map.moveHero();
        display.displayUI();
        System.out.println();
    }

//    /**
//     * displays a complete frame of the full UI of the the game (inventory and tool-tips included)
//     *
//     */
//    public void displayUI() {
//        int paddingSize = 1, mapSize = 5;
//        Position heroPos = map.getHeroPositionReference();
//
//        char[][] inventory, map, side, padding;
//
//        // getting structures
//        inventory = inventoryToArray();
//        map = mapToArray(heroPos.getX(), heroPos.getY(), mapSize);
//        side = sideToArray();
//        padding = paddingToArray(paddingSize);
//        int uiHeight = mapSize * 2 + 1;
//
//        // combining structures into single grid
//        char[][][] layout = {inventory, padding, map, padding, side};
//        char[][] ui = new char[layout.length][uiHeight];
//
//        int i = 0, lowerBound = 0, upperBound = 0;
//        for(int j = 0; j < layout.length; j++) {
//            upperBound += layout[j].length;
//            for(; i < upperBound; i++) {
//                ui[i] = layout[j][i - lowerBound];
//            }
//            lowerBound = upperBound;
//        }
//
//        // merging columns and rows into strings
//        String result = "";
//        for(int row = 0; row < ui.length; row++) {
//            for(int column = 0; column < ui[row].length; column++) {
//                result += ui[row][column];
//            }
//            result += System.lineSeparator();
//        }
//
//        System.out.println();
//        System.out.println(result);
//        System.out.println();
//    }
//
//    private char[][] inventoryToArray() {
//        char[][] area = new char[this.map.getHeight()][15];
//
//        char borderChar = '@';
//        char[] horizontalBorder = getDuplicateCharArray(borderChar, area[0].length);
//
//        int i = 1, j = 0;
//        area[0] = horizontalBorder.clone(); // Top Border
//        for(; i < area.length; i++) {
//            area[i][j] = borderChar; // Left Border
//
//            for(; j < area[i].length ; j++) {
//                area[i][j] = ' ';
//            }
//
//            area[i][j] = borderChar; // Right Border + New Line
//        }
//        area[i] = horizontalBorder.clone(); // Bottom Border
//
//        return area;
//    }
//
//    /**
//     * the map as visable to player given the hero's x and y position and a specified size
//     *
//     * @param x
//     * @param y
//     * @param size
//     * @return
//     */
//    private char[][] mapToArray(int x, int y, int size) {
//        assert size >= 1;
//        assert x >= 0 && x <= map.getWidth();
//        assert y >= 0 && y <= map.getHeight();
//
//        int d = size * 2 + 1;
//        char borderChar = '@';
//        char[] horizontalBorder = getDuplicateCharArray(borderChar, d + 2);
//        // char to display if location is outside of map
//        // (i.e. player is near an edge or corner of the map and can see outside the map bounds)
//        char OutOfBoundsChar = (char)(new Wall().getChar());
//
//        char[][] area = new char[d + 2][d + 2];
//        int a = 0, b = 0;
//
//        area[a] = horizontalBorder.clone(); // Top Border
//        a++;
//        for(int i = x - size; i <= d; i++) {
//            area[b][a] += borderChar; // Left Border
//
//            for(int j = y - size; j <= d; j++) {
//                b++;
//                area[b][a] = j < 0 ? OutOfBoundsChar :
//                             i < 0 ? OutOfBoundsChar :
//                             j > map.getWidth() ? OutOfBoundsChar :
//                             i > map.getHeight() ? OutOfBoundsChar :
//                             map.getObjectAt(new Position(j, i)).getChar();
//            }
//
//            area[b][a] = borderChar; // Right Border + New Line
//            a++;
//        }
//        area[a] = horizontalBorder.clone(); // Bottom Border
//
//        return area;
//    }
//
//    /**
//     * used for tool-tips and speech interactions, as well as options tab
//     *
//     * @return
//     */
//    private char[][] sideToArray() {
//        char[][] area = new char[this.map.getHeight()][25];
//
//        char borderChar = '@';
//        char[] horizontalBorder = getDuplicateCharArray(borderChar, area[0].length);
//
//        int i = 0, j = 0;
//        area[0] = horizontalBorder.clone(); // Top Border
//        for(; i < area.length; i++) {
//            area[i][j] += borderChar; // Left Border
//
//            for(; j < area[i].length ; j++) {
//                area[i][j] += ' ';
//            }
//
//            area[i][j] = borderChar; // Right Border + New Line
//        }
//        area[i] = horizontalBorder.clone(); // Bottom Border
//
//        return area;
//    }
//
//    /**
//     * aesthetic padding between ui sections for clarity
//     *
//     * @param paddingSize
//     * @return
//     */
//    private char[][] paddingToArray(int paddingSize) {
//        char[][] area = new char[this.map.getHeight() + 2][paddingSize];
//        char[] blankColumn = getDuplicateCharArray(' ', this.map.getHeight());
//
//        for(int i = 0; i < paddingSize; i++)
//            area[i] = blankColumn;
//
//        return area;
//    }
//
//    /**
//     * returns and array of duplicate chars
//     *
//     * @param duplicate
//     * @param size
//     * @return
//     */
//    private char[] getDuplicateCharArray(char duplicate, int size) {
//        return String.valueOf(duplicate).repeat(size).toCharArray();
//    }
}
