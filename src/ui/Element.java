package ui;

public abstract class Element {

    // x, y is relative to the pane the element exists on
    private int x, y, width, height;

    /**
     * a tuple only used to get the maximized size of an element
     *
     */
    protected class Bounds {

        int width, height;

        public Bounds(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public Element(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Element(int width, int height) {
        this(0, 0, width, height);
    }

    /**
     * constructs and element with undefined width and height and a is positioned at x = 0, y = 0.
     * the width and height of this element could be defined later by the use of maximizeSize() function, which will at
     * least be called by a getStringRender() function in Pane class
     *
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
     * @param render
     * @return
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
     * @return
     */
    public abstract String[] getStringRender();

    /**
     * returns a bounds object which returns the minimum size required for the element to be displayed in its entirety
     * this function is only called by the maximizeSize() function which is public.
     *
     * @return
     */
    protected abstract Bounds getMaximizedSize();
}
