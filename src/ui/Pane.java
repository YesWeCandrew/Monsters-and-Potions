package ui;

import java.util.ArrayList;
import java.util.Arrays;

public class Pane extends Element {

    ArrayList<Element> elementsOrder = new ArrayList<Element>(); // in render order (first is rendered first)

    public Pane(int width, int height) {
        super(width, height);
    }

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

    public int getElementsSize() {
        return elementsOrder.size();
    }

    @Override
    public void maximizeSize() {
        int maxWidth = 0, maxHeight = 0;
        for(Element element : elementsOrder) {
            if(element instanceof Pane)
                element.maximizeSize();

            maxWidth = Math.max(maxWidth, element.getX() + element.getWidth());
            maxHeight = Math.max(maxHeight, element.getY() + element.getHeight());
        }

        setWidth(maxWidth);
        setHeight(maxHeight);
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

    private String insertString(String original, String insert, int index) {
        int insertLength = Math.min(original.length() - index, insert.length());
        return original.substring(0, index) + insert.substring(0, insertLength) + original.substring(index + insertLength);
    }

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
