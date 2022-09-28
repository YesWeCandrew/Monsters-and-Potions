package ui;

import java.util.ArrayList;

public class TextField extends Element {

    String text;
    Listener<String> listener;
    String[] wrappedText = null;

    public TextField(int textMaxWidth, int lineNum) {
        super(textMaxWidth, lineNum);
    }

    public TextField(String text) {
        super();
        this.text = text;
        maximizeSize();
    }

    @Override
    public String[] getStringRender() {
        return listener != null ? wrapText(listener.retrieve()) : wrapText(text);
    }

    @Override
    public void maximizeSize() {
        if(listener != null) {
            this.wrappedText = wrapText(listener.retrieve());
        }
        else if(text != null) {
            this.wrappedText = wrapText(text);
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setListener(Listener<String> listener) {
        this.listener = listener;
    }

    public String getText() {
        return text;
    }

    private String[] wrapText(String text) {
        ArrayList<String> wrapped = new ArrayList<>();
        wrapped.add("");
        String[] words = text.split(" ");
        int index = 0, length = 0;
        for(String word : words) {
            if((length + word.length() + 1 > getWidth() && index < getHeight() && getWidth() != -1 && getHeight() != -1) || word.equals(System.lineSeparator())) {
                wrapped.set(index, wrapped.get(index) + (" ").repeat(getWidth() - length));
                wrapped.add("");
                length = 0;
                index++;
            }

            String prev = wrapped.get(index);
            wrapped.set(index, prev + (prev.equals("") ? "" : " ") + word);
            length += word.length() + (prev.equals("") ? 0 : 1);
        }

        if(getWidth() != -1) {
            wrapped.set(index, wrapped.get(index) + (" ").repeat(getWidth() - length));
        }
        if(getHeight() != -1) {
            for (int i = index; index < getHeight(); index++) {
                wrapped.add((" ").repeat(getWidth()));
            }
        }

        return (String[])(wrapped.toArray(new String[wrapped.size()]));
    }
}
