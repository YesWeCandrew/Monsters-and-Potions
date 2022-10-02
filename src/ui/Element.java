package ui;

public abstract class Element {

    // x, y is relative to the pane the element exists on
    private int x, y, width, height;

    /**
     * a tuple only used to get the maximized size of an element
     * @author Mitchell Barker
     */
    protected class Bounds {

        int width, height;

        public Bounds(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    /**
     * fully defined element given the provided parameters
     *
     * @param x x coordinate (relative to the pane the element is placed on)
     * @param y y coordinate (relative to the pane the element is placed on)
     * @param width the width of the element
     * @param height the height of the element
     * @author Mitchell Barker
     */
    public Element(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * constructs and element with provided width and height parameters, the elements position is defaulted to
     * x = 0, y = 0, and can be adjusted later.
     *
     * @param width
     * @param height
     * @author Mitchell Barker
     */
    public Element(int width, int height) {
        this(0, 0, width, height);
    }

    /**
     * constructs and element with undefined (-1) width and height and is positioned at x = 0, y = 0.
     * the width and height of this element could be defined later by the use of maximizeSize() function, which will at
     * least be called by a getStringRender() function in Pane class
     *
     * @author Mitchell Barker
     */
    public Element() {
        this(0, 0, -1, -1);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void relocate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * checks that each element string length is equal to the width attribute and the render's length is equal to the
     * height attribute
     *
     * @param render the string array render of this element
     * @return true if the render size matches the width and height attributes of itself
     * @author Mitchell Barker
     */
    private boolean checkRender(String[] render) {
        boolean valid = render.length == height && x >= 0 && y >= 0;
        for(String s : render) {
            valid = valid ? s.length() == width : false;
        }

        return valid;
    }

    /**
     * sets the width and height to fit the minimum required size that can display the entire element
     *
     * @author Mitchell Barker
     */
    public void maximizeSize() {
        Bounds b = getMaximizedSize();
        setWidth(b.width);
        setHeight(b.height);
    }

    /**
     * returns the string representation of the element separated by new lines to resemble the element as it would be
     * displayed on the console
     *
     * @return the string represented of the element
     * @author Mitchell Barker
     */
    @Override
    public String toString() {
        return String.join(System.lineSeparator(), getStringRender());
    }

    /**
     * returns a string array, each element of the array represents a row to be displayed to the terminal
     * if an element contains sub-elements to be rendered, then this method is the method to be called to
     * render the all sub-elements being arranged in an order
     *
     * @return the string array representing each line to be displaced to the console
     * @author Mitchell Barker
     */
    public abstract String[] getStringRender();

    /**
     * returns a bounds object which returns the minimum size required for the element to be displayed in its entirety
     * this function is only called by the maximizeSize() function which is public.
     *
     * @return a bounds object representing the width and height the element needs to be to display itself
     * @author Mitchell Barker
     */
    protected abstract Bounds getMaximizedSize();
}
