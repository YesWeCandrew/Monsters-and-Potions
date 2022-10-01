package main;

import map.Map;

public class Main {

    static Interface display;

    /**
     * Runs a title sequence, lets the user view instructions or load a game
     * Then creates the Map and the Interface
     * @param args none
     * @author Andrew Howes
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

        // Not sure if we want the loop  here, but this is just a basic way we
        // can have the game check if the player won or lost
        while (map.heroEscaped == null) {
            display.displayUI();
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
}
