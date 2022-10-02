package ui;

import java.util.ArrayList;
import java.util.Arrays;

public class Pane extends Element {

    // in render order (first is rendered first)
    ArrayList<Element> elementsOrder = new ArrayList<Element>();

    /**
     * a Pane element with an allocated size to display, the pane allows the display of multiple elements arranged at
     * different places within the panes relative coordinate space, and can render all elements and its self to a
     * String array
     *
     * @param width the width of the pane
     * @param height the width of the pane
     * @author Mitchell Barker
     */
    public Pane(int width, int height) {
        super(width, height);
    }

    /**
     * a Pane element with undefined width and height, which will be determined when rendering the pane element to the
     * console by finding the minimum required size to display all elements within the pane given there current sizes
     * and positions
     *
     * @author Mitchell Barker
     */
    public Pane() {
        super(-1, -1);
    }

    public void addElement(Element element) {
        elementsOrder.add(element);
    }

    public void addElements(Element... elements) {
        elementsOrder.addAll(new ArrayList<Element>(Arrays.asList(elements)));
    }

    public void removeElement(Element element) {
        elementsOrder.remove(element);
    }

    public void clearElements() {
        elementsOrder.clear();
    }

    public int getElementsSize() {
        return elementsOrder.size();
    }

    @Override
    protected Bounds getMaximizedSize() {
        int maxWidth = 0, maxHeight = 0;
        for(Element element : elementsOrder) {
            if(element instanceof Pane)
                element.maximizeSize();

            maxWidth = Math.max(maxWidth, element.getX() + element.getWidth());
            maxHeight = Math.max(maxHeight, element.getY() + element.getHeight());
        }

        return new Bounds(maxWidth, maxHeight);
    }

    @Override
    public String[] getStringRender() {
        if(getWidth() == -1 || getHeight() == -1) { // if size of pane is dynamic, get size from elements
            maximizeSize();
        }

        String[] area = new String[getHeight()];
        String blankWidth = (" ").repeat(getWidth());

        for(int i = 0; i < area.length; i++) {
            area[i] = blankWidth;
        }

        for(Element element : elementsOrder) {
            area = insertStringArray(area, element.getStringRender(), element.getX(), element.getY());
        }

        return area;
    }

    /**
     * helper function which overwrites the characters at an index of the original string with the characters of the
     * inserted string without changing the length of the original string e.g.
     *
     * original = "apples are good"
     * insert = "people"
     * index = 0
     * result = "people are good"
     *
     * original = "abcdefg"
     * insert = "xxxxx"
     * index = 4
     * result = "abcdexx"
     *
     * @param original the original string which the inserted string will be placed on
     * @param insert the string to be inserted
     * @param index the index of the original string which the first inserted string character will overwrite
     * @return the original string with the inserted string overwritten over it at an index
     * @author Mitchell Barker
     */
    private String insertString(String original, String insert, int index) {
        int insertLength = Math.min(original.length() - index, insert.length());
        return original.substring(0, index) + insert.substring(0, insertLength) + original.substring(index + insertLength);
    }

    /**
     * helper function which applies the insertString() function on each element of the inserted array on the original
     * array element at a particular coordinate.
     *
     * @param original the original string array
     * @param insert the inserted string array
     * @param x coordinate x
     * @param y coordinate y
     * @return the inserted array placed on the original array
     * @author Mitchell Barker
     */
    private String[] insertStringArray(String[] original, String[] insert, int x, int y) {
        int i = 0;
        int upper = insert.length + y;
        for(; y < Math.min(upper, original.length); y++) {
            original[y] = insertString(original[y], insert[i], x);
            i++;
        }

        return original;
    }
}
