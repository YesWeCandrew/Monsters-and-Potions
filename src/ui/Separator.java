package ui;

public class Separator extends Element {

    int x, y, length;
    boolean horizontal;
    char character;

    public Separator(int x, int y, int length, boolean horizontal, char character) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.horizontal = horizontal;
        this.character = character;
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
}
