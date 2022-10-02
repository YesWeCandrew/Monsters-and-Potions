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

    private final int viewPortRadius = 5;

    /**
     * used to display every element in a main pane, retrieved all information on the hero through the map
     *
     * @param map the map to be place in the interface
     * @author Mitchell Barker
     */
    public Interface(Map map) {
        this.map = map;
        this.main = createMain();
    }

    /**
     * singleton function for ensuring that the main variable exists and is not overwritten
     *
     * @return the main pane
     * @author Mitchell Barker
     */
    private Pane getMain() {
        return main == null ? createMain() : main;
    }

    /**
     * main function which determines the hard coded placement of each element relative to the main pane
     *
     * @return the main pane
     * @author Mitchell Barker
     */
    private Pane createMain() {
        Pane main = new Pane();

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
        createInteractions(2, 0, 25, 4);
        createToolTip(2, 5, 25);
        interact_tooltip.relocate(rightSeperator.getX() + 1, 0);
        interact_tooltip.maximizeSize();

        main.addElements(inv_stats, leftSeperator, interact_tooltip, rightSeperator, mapView);

        return main;
    }

    /**
     * displays the HP and AP statistics bars with the given x and y coordinates and the max width
     *
     * @param x the x coordinate relative to the left panes coordinate system
     * @param y the y coordinate relative to the left panes coordinate system
     * @param maxWidth the maximum width of the StatisticsBar (in characters)
     * @author Mitchell Barker
     */
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

    /**
     * creates the UI for the inventory of the Hero, updates whenever an item is picked up or discarded.
     *
     * @param x the x coordinate relative to the left panes coordinate system
     * @param y the y coordinate relative to the left panes coordinate system
     * @param maxWidth the maximum width of the TextField (in characters)
     * @author Mitchell Barker
     */
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

    /**
     * creates a text field on the top right corner of the displayed UI, which simply describes the current objective
     *
     * @param x the x coordinate relative to the left panes coordinate system
     * @param y the y coordinate relative to the left panes coordinate system
     * @param maxWidth the maximum width of the TextField (in characters)
     * @param maxHeight the maximum height of the TextField (in characters)
     * @author Mitchell Barker
     */
    private void createInteractions(int x, int y, int maxWidth, int maxHeight) {
        String text = "Objective: " + System.lineSeparator() + " Escape the maze by finding the magic Amulet."; // TODO need to get interactions text (i.e. quests, phrases, ...)

        TextField textField = new TextField(maxWidth, maxHeight);
        textField.setText(text);
        textField.relocate(x, y);

        this.interact_tooltip.addElement(textField);
    }

    /**
     * creates the options list, default to the left-side pane inside of the main pane. displays all possible actions
     * the player may take with the associated key that must be pressed to perform such action
     *
     * this includes a title "options" and two line separates for visual clarity
     *
     * @param x the x coordinate relative to the left panes coordinate system
     * @param y the y coordinate relative to the left panes coordinate system
     * @param maxWidth the maximum width of the options
     * @author Mitchell Barker
     */
    private void createToolTip(int x, int y, int maxWidth) {
        TextField toolTipText = new TextField("Options:");
        toolTipText.relocate(x, y);

        Separator leftSeperator = new Separator(5, false, '|');
        leftSeperator.relocate(x, y + 1);

        TextList options = new TextList(5, maxWidth - 4);
        options.setListener(() -> {
            ArrayList<String> optionStrings = map.allPossibleActions();
            String[] a = optionStrings.toArray(new String[optionStrings.size()]);
            return optionStrings.toArray(new String[optionStrings.size()]);
        });
        options.relocate(x + 2, y + 1);

        Separator rightSeperator = new Separator(5, false, '|');
        rightSeperator.relocate(x + options.getWidth(), y + 1);

        this.interact_tooltip.addElements(toolTipText, leftSeperator, options, rightSeperator);
    }

    /**
     * method to be called each frame to display the up-to-data UI in its entirety.
     * this function will display the main pane on its own but will return the string
     * which is printed for testing purposes.
     *
     * @return the resulting string to be rendered (for testing purposes)
     * @author Mitchell Barker
     */
    public String displayUI() {
        getMain();
        String[] render = main.getStringRender();
        String result = String.join(System.lineSeparator(), render);

        System.out.println(result);
        System.out.flush();

        return result;
    }
}
