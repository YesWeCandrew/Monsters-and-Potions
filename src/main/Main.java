package main;

import map.Map;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Interface display;
    public static ArrayList<KeyListener> listeners = new ArrayList<>();

    public static Scanner scanner = new Scanner(System.in);

    public interface KeyListener {

        /**
         * Completes some action given the key
         *
         * @param c the key that was input
         *
         * @return true if the key resulted in an action, false if it is ignored
         * @author Mitchell Barker
         */
        boolean action(char c);
    }

    /**
     * Runs a title sequence, lets the user view instructions or load a game
     * Then creates the Map and the Interface
     *
     * @author Andrew Howes
     *
     * @param args none
     */
    public static void main(String[] args) {

        // Displaying a title sequence
        Menu.titleSequence();

        // Displaying options which loop until a map is chosen
        Map map = Menu.displayOptionsAndChooseMap();

        // Map will be null if quit option (q) is called from menu
        // Stops people getting stuck looping in displayOptionsAndChooseMap()
        if (map == null) {
            System.exit(0);
        }

        // Creating the new interface
        display = new Interface(map);
        map.heroEscaped = null;

        display.displayUI();
        System.out.println();
        System.out.print("command input: ");
        while (map.heroEscaped == null) {
            if(getKeyEvent()) {
                for(int i = 0; i < 10; i++)
                    System.out.println(); // visual spacing between frames

                display.displayUI();
                System.out.println();
            }
            System.out.print("command input: ");
        }

        // Only set to true if the Amulet is picked up
        if (map.heroEscaped) {
            System.out.println("Congratulations! You just won!");
        }

        // Set to false if the player is killed
        else {
            System.out.println(
                    "I'm afraid you have died. Better luck next time..."
            );
        }
    }

    /**
     * waits for a console input then, runs all key listeners
     *
     * @return true if a listener did an action given the input, otherwise false
     * @author Mitchell Barker
     */
    public static boolean getKeyEvent() {
        String s = scanner.next();
        boolean render = false;

        if(s != null) {
            if(s.length() > 0) {
                char c = s.charAt(0);
                for (KeyListener listener : listeners) {
                    boolean used = listener.action(c);
                    render = render || used;
                }
            }
        }

        return render;
    }
}
