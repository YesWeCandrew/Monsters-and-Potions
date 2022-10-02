package main;

import map.Map;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static main.Main.scanner;

public class Menu {
    private static final String DEFAULT_CSV = "0-defaultgame";
    private static final String DEFAULT_JSON = "56-anotherSave";

    public static final String TITLE_1 =
        """

        ███╗   ███╗ ██████╗ ███╗   ██╗███████╗████████╗███████╗██████╗ ███████╗
        ████╗ ████║██╔═══██╗████╗  ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
        ██╔████╔██║██║   ██║██╔██╗ ██║███████╗   ██║   █████╗  ██████╔╝███████╗
        ██║╚██╔╝██║██║   ██║██║╚██╗██║╚════██║   ██║   ██╔══╝  ██╔══██╗╚════██║
        ██║ ╚═╝ ██║╚██████╔╝██║ ╚████║███████║   ██║   ███████╗██║  ██║███████║
        ╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝

        """;

    public static final String TITLE_2 =
        """
                                  ███╗   ██╗
                                  ████╗  ██║
                                  ██╔██╗ ██║
                                  ██║╚██╗██║
                                  ██║ ╚████║
                                  ╚═╝  ╚═══╝
        """;

    public static final String TITLE_3 =
        """
                       
             ██████╗  ██████╗ ████████╗██╗ ██████╗ ███╗   ██╗███████╗
             ██╔══██╗██╔═══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝
             ██████╔╝██║   ██║   ██║   ██║██║   ██║██╔██╗ ██║███████╗
             ██╔═══╝ ██║   ██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║
             ██║     ╚██████╔╝   ██║   ██║╚██████╔╝██║ ╚████║███████║
             ╚═╝      ╚═════╝    ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝

        """;

    public static final String INITIAL_INSTRUCTIONS =
        """
        Welcome to Monsters n Potions! Your mission, if you choose to
        accept it, is to find the magic Amulet from within the maze.
        
        Be careful! The maze is filled with monsters that want you dead.
        Keep an eye out for items. These can both help and harm you.

        Ready to begin?
        Choose from the options below.
        -----------------------------------------------------------------------""";

    public static final String CHOOSE_INSTRUCTIONS =
        """
         
         View instructions       = V
         Play defaultDungeon     = P
         Load saved game         = L
         Quit game               = Q
        
         Select from the options above and press ENTER.""";

    public static final String INSTRUCTIONS =
            """
            ------------------------------------------------------------------------
            
            Welcome to Monsters n Potions!
                    
            You are trapped in an expansive maze full of Monsters and Items.
            Your job - escape the maze by finding the magic Amulet. But be warned,
            the maze is full of monsters out to get you.
            
            Use items to increase your attack and health, before killing the
            monsters, finding the Amulet and escaping!
            
            ------------------------------------------------------------------------
                    
            ACTIONS:
            
            You play the Hero who can move through the maze using the arrow keys:
            W   |   Move or face up
            D   |   Move or face right
            S   |   Move or face down
            A   |   Move of face left
            
            When you are facing a monster or an item, you will see additional keys
            keys that you can press:
            P   |   Pick up the item in front of you
            O   |   Attack the monster in front of you
            
            Picking up an item will immediately apply its effects. Discarding the
            item will undo its effects. You can only hold four items at a time, so
            be careful what you select. To discard an item, press:
            1   |   Discards the first item in your inventory
            2   |   Discards the second item in your inventory
            3   |   Discards the third item in your inventory
            4   |   Discards the fourth item in your inventory
            
            ------------------------------------------------------------------------
            
            OBJECTS:
            
            Objects in the game are displayed as follows
            
            Hero            |       H
            Monsters        |       Can be any other character!
            Items           |       Can be any other character!
            Walls           |       #
            The Amulet      |       %
            
            ------------------------------------------------------------------------
            
            Press ENTER to return to the main menu.
            
                    """;

    /**
     * Displays the title sequence, including general instructions.
     *
     * @author Andrew Howes
     */
    public static void titleSequence() {
        // display the title
        System.out.println(Menu.TITLE_1);

        // pause for dramatic effect.
        // Not important so if fails, simply moves onto next print line,
        // ignoring error
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
        }

        System.out.println(Menu.TITLE_2);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
        }

        System.out.println(Menu.TITLE_3);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
        }

        System.out.println(INITIAL_INSTRUCTIONS);
    }

    /**
     * Lets the user select from a few basic options.
     * If it receives invalid input it cycles. Press Q to quit.
     * @author Andrew Howes
     */
    public static Map displayOptionsAndChooseMap() {
        System.out.println(CHOOSE_INSTRUCTIONS);

        String s = scanner.nextLine();

        if (s == null) {return tryOptionsAgain("Invalid input, try again");}
        else if (s.length() == 0) {return tryOptionsAgain("Invalid input, try again");}
        else {
                switch (s.charAt(0)) {
                    case 'V', 'v' -> { return displayInstructions(); }
                    case 'P', 'p' -> { return new Map(DEFAULT_CSV, DEFAULT_JSON); }
                    case 'L', 'l' -> { return loadGame(); }
                    case 'Q', 'q' -> { return null; }
                    default -> {
                        return tryOptionsAgain("Invalid input, try again");
                    }
                }
        }
    }

    /**
     * Calls displayOptionsAgain and notifies user of bad input.
     * @author Andrew Howes
     */
    public static Map tryOptionsAgain(String message) {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(message);
        return displayOptionsAndChooseMap();
    }


    /**
     * Runs the user through selecting a game to load.
     * @author Andrew Howes
     */
    public static Map loadGame() {
        System.out.println("Type the name of the CSV file you want to load");

        String csv = scanner.nextLine();

        System.out.println("Type the name of the JSON file you want to load");
        String json = scanner.nextLine();

        System.out.println("Loading...");

        // in case people enter the filename.csv
        if (csv.endsWith(".csv") || csv.endsWith(".CSV")) {
            csv = csv.substring(0,csv.length() - 4);
        }

        // in case people enter the filename.json
        if (json.endsWith(".json") || json.endsWith(".JSON")) {
            json = json.substring(0,json.length() - 5);
        }
        return new Map(csv,json);
    }

    /**
     * Displays detailed game instructions to the user and lets them return
     *
     * @author Andrew Howes
     */
    public static Map displayInstructions() {
        System.out.println(INSTRUCTIONS);

        Scanner in = new Scanner(System.in);
        in.nextLine();

        return Menu.tryOptionsAgain("Choose from the options below.");
    }
}
