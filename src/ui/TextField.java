package ui;

import java.util.ArrayList;

public class TextField extends Element {

    String text;

    public TextField(int textMaxLength, int lineNum) {
        super(textMaxLength, lineNum);
    }

    @Override
    public String[] getStringRender() {
        return wrapText(text);
    }

    public void setString(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    private String[] wrapText(String text) {
        ArrayList<String> wrapped = new ArrayList<>();

        String[] words = text.split(" ");
        int index = 0, length = 0;
        for(String word : words) {
            if((length + word.length() + 1 > getWidth() && index < getHeight() && getWidth() != -1) || word.equals(System.lineSeparator())) {
                wrapped.set(index, wrapped.get(index) + (" ").repeat(getWidth() - length));
                wrapped.add("");
                length = 0;
                index++;
            }
            wrapped.set(index, wrapped.get(index) + word);
            length += word.length();
        }

        return (String[]) wrapped.toArray();
    }
}
