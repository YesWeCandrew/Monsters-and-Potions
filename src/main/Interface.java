
import src.objects.GameObject;
import src.ui.Pane;
import src.ui.ViewPort;

public class Interface {

    Pane main = null;
    Map map;

    public Interface(Map map) {
        this.map = map;
        this.main = createMain();
    }

    private Pane getMain() {
        return main == null ? createMain() : main;
    }

    private Pane createMain() {
        Pane main = new Pane();

        ViewPort mapView = new ViewPort(map, 3, true);
        // inventory + stats view
        // setX of inventory
        // interactions + tooltip view


        main.addElement(mapView);

        return main;
    }

    public void displayUI() {
        getMain();
        String[] render = main.getStringRender();
        String result = String.join(System.lineSeparator(), render);

        System.out.println(result);
        System.out.flush();
    }

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

        map.turnHeroLeft();
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
