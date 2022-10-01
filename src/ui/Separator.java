package ui;

public class Separator extends Element {

    int length;
    boolean horizontal;
    char character;

    /**
     * returns a visual separator to separate different elements on a pane
     *
     * @param length the length of a separator
     * @param horizontal whether the separator is horizontal or vertical
     * @param character the separator character which will be displayed
     */
    public Separator(int length, boolean horizontal, char character) {
        this.length = length;
        this.horizontal = horizontal;
        this.character = character;
        maximizeSize();
    }

    @Override
    public String[] getStringRender() {
        String[] separator = null;
        if(horizontal) {
            separator = new String[]{String.valueOf(character).repeat(length)};
        }
        else {
            separator = new String[length];
            for(int i = 0; i < length; i++)
                separator[i] = String.valueOf(character);
        }
        return separator;
    }

    @Override
    protected Bounds getMaximizedSize() {
        return new Bounds(horizontal ? length : 1, horizontal ? 1 : length);
    }
}
