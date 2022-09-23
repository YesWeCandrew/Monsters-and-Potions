package ui;

import java.util.ArrayList;

public class Pane extends Element {

    ArrayList<Element> elements = new ArrayList<Element>(); // in render order (first is rendered first)

    public Pane(int width, int height) {
        super(width, height);
    }

    public Pane() {

    }

    public void addElement(ui.Element element) {
        elements.add(element);
    }

    public void removeElement(Element element) {
        elements.remove(element);
    }

    public int getElementsSize() {
        return elements.size();
    }

    @Override
    public String[] getStringRender() {
        if(getWidth() == -1 || getHeight() == -1) { // if size of pane is dynamic, get size from elements
            int maxWidth = 0, maxHeight = 0;
            for(Element element : elements) {
                maxWidth = Math.max(maxWidth, element.getX() + element.getWidth());
                maxHeight = Math.max(maxHeight, element.getY() + element.getHeight());
            }

            setWidth(maxWidth);
            setHeight(maxHeight);
        }

        String[] area = new String[getHeight()];
        String blankWidth = (" ").repeat(getWidth());

        for(int i = 0; i < area.length; i++) {
            area[i] = blankWidth;
        }

        for(Element element : elements) {
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
        for(; y < original.length; y++) {
            original[y] = insertString(original[y], insert[i], x);
            i++;
        }

        return original;
    }
}
