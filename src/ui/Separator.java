package ui;

public class Separator extends Element {

    int length;
    boolean horizontal;
    char character;

    public Separator(int length, boolean horizontal, char character) {
        this.length = length;
        this.horizontal = horizontal;
        this.character = character;
        maximizeSize();
    }

    @Override
    public String[] getStringRender() {
        String[] separator = null;
        if (horizontal) {
            separator = new String[]{ String.valueOf(character).repeat(length) };
        }
        else {
            separator = new String[length];
            for (int i = 0; i < length; i++)
                separator[i] = String.valueOf(character);
        }
        return separator;
    }

    @Override
    public void maximizeSize() {
        setWidth(horizontal ? length : 1);
        setHeight(horizontal ? 1 : length);
    }
}
