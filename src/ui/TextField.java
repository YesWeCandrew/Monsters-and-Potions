package ui;

import java.util.ArrayList;

public class TextField extends Element {

    String text;
    Listener<String> listener;
    String[] wrappedText = null;

    /**
     * Simple text that can be placed in a Pane and displayed
     *
     * @param textMaxWidth maximum text width (by characters)
     * @param lineNum amount of line number allocated to the TextField
     * @author Mitchell Barker
     */
    public TextField(int textMaxWidth, int lineNum) {
        super(textMaxWidth, lineNum);
    }

    /**
     * Simple text that can be placed in a Pane and displayed
     *
     * @param text the text
     * @author Mitchell Barker
     */
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
    protected Bounds getMaximizedSize() {
        if(this.wrappedText == null) {
            if (listener != null) {
                this.wrappedText = wrapText(listener.retrieve());
            } else if (text != null) {
                this.wrappedText = wrapText(text);
            }
        }

        int maxWidth = 0, maxHeight = this.wrappedText.length;
        for(String s : this.wrappedText) {
            maxWidth = s != null ? Math.max(maxWidth, s.length()) : maxWidth;
        }

        return new Bounds(maxWidth, maxHeight);
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

    /**
     * converts plane text to a String array, where each String element in the string array is of the same length,
     * it does this by inserting spaces at the end of each line to fill in space if the proceeding word could not fit.
     *
     * note: adding a System.lineSeparator() (separated by spaces as if it was a word) between two words will also
     * create a new line (append a new string element) to the resulting wrapped text
     *
     * note: this method will approximate the width of the element and set the width if it is the default width of -1.
     * it does this by getting the length of the greatest string separated by new lines.
     *
     * @param text the text to format
     * @return the formatted text, each line representing a element of the array
     * @author Mitchell Barker
     */
    private String[] wrapText(String text) {
        if(getWidth() == -1) {
            String[] split = text.split(System.lineSeparator());
            int maxWidth = 0;
            for(String string : split) {
                String[] spaceSplit = string.split(" ");
                maxWidth = Math.max(maxWidth, string.length() + (spaceSplit.length <= 1 ? -1 : 0));
            }

            setWidth(maxWidth);
        }

        ArrayList<String> wrapped = new ArrayList<>();
        wrapped.add("");
        String[] words = text.split(" ");
        int index = 0, length = 0;
        for(String word : words) {
            if((length + word.length() + (wrapped.get(index).equals("") ? 0 : 1) > getWidth() && index < getHeight()) || word.equals(System.lineSeparator())) {
                wrapped.set(index, wrapped.get(index) + (" ").repeat(getWidth() - length));
                wrapped.add("");
                length = 0;
                index++;
            }

            if(!word.equals(System.lineSeparator())) {
                String prev = wrapped.get(index);
                wrapped.set(index, prev + (prev.equals("") ? "" : " ") + word);
                length += word.length() + (prev.equals("") ? 0 : 1);
            }
        }

        if(getWidth() != -1 && length < getWidth()) {
            wrapped.set(index, wrapped.get(index) + (" ").repeat(getWidth() - length));
        }

        if(getHeight() != -1) {
            for (int i = index; index < getHeight(); index++) {
                wrapped.add((" ").repeat(getWidth()));
            }
        }

        return (String[]) (wrapped.toArray(new String[wrapped.size()]));
    }
}
