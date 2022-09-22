package src.ui;

public abstract class Element {

    private int x, y, width, height;

    public Element(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Element(int width, int height) {
        this(0, 0, width, height);
    }

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

    private boolean checkRender(String[] render) {
        boolean valid = render.length == height && x >= 0 && y >= 0;
        for(String s : render) {
            valid = valid ? s.length() == width : false;
        }

        return valid;
    }

    public abstract String[] getStringRender();
}
