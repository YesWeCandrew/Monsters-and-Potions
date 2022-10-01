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

    public Interface(Map map) {
        this.map = map;
        this.main = createMain();
    }

    public Interface() {
        this(null);
    }

    /**
     * singleton function for ensuring that the main variable exists and is not overwritten
     *
     * @return
     */
    private Pane getMain() {
        return main == null ? createMain() : main;
    }

    /**
     * main function which determines the hard coded placement of each element relative to the main pane,
     *
     *
     * @return the main pane
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
        String text = "Objective: " + System.lineSeparator() + " Escape the maze by finding the magic Amulet."; // TODO need to get interactions text (i.e. quests, phrases, ...)

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
     */
    public String displayUI() {
        getMain();
        String[] render = main.getStringRender();
        String result = String.join(System.lineSeparator(), render);

        System.out.println(result);
        System.out.flush();

        return result;
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
        map.goDown();

        System.out.println();
        System.out.print("command input: ");
        while (map.heroEscaped == null) {
            if(Main.getKeyEvent()) {
                for(int i = 0; i < 10; i++)
                    System.out.println(); // visual spacing between frames

                display.displayUI();
                System.out.println();
            }
            System.out.print("command input: ");
        }
    }
}
