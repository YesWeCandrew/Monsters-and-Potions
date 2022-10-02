package ui;

import java.util.ArrayList;

public class TextField extends Element {

    String text;
    Listener<String> listener;
    String[] wrappedText = null;

    /**
     *
     *
     * @param textMaxWidth
     * @param lineNum
     */
    public TextField(int textMaxWidth, int lineNum) {
        super(textMaxWidth, lineNum);
    }

    /**
     *
     *
     * @param text
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
     *
     *
     * @param text
     * @return
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
