package ui;

import java.util.ArrayList;

public class TextList extends Element {

    TextField[] texts;
    Listener<String[]> listener;
    int size, maxSize, maxWidth;

    /**
     *
     *
     * @param maxSize
     * @param maxWidth
     */
    public TextList(int maxSize, int maxWidth) {
        super();
        this.maxWidth = maxWidth;
        this.maxSize = maxSize;
        this.texts = new TextField[maxSize];
        maximizeSize();
    }

    public void setListener(Listener<String[]> listener) {
        this.listener = listener;
    }

    public boolean addText(String text) {
        return addText(size, text);
    }

    /**
     *
     *
     * @param index
     * @param text
     * @return
     */
    public boolean addText(int index, String text) {
        boolean inserted = false;
        if(size < texts.length && inBounds(index)) {
            for (int i = texts.length - 1; i > index; i--) {
                texts[i] = texts[i - 1];
            }
            TextField field = new TextField(getWidth(), 1);
            field.setText(text);
            texts[index] = field;
            size++;

            inserted = true;
        }

        return inserted;
    }

    /**
     *
     *
     * @param texts
     * @return
     */
    public boolean addAllText(ArrayList<String> texts) {
        boolean insertedAll = this.texts.length >= texts.size() + size;
        for(int i = size; i < Math.min(this.texts.length, texts.size() + size); i++) {
            TextField field = new TextField(getWidth(), 1);
            field.setText(texts.get(i));

            this.texts[i] = field;
            size++;
        }

        return insertedAll;
    }

    /**
     *
     *
     * @param index
     * @return
     */
    public boolean removeText(int index) {
        boolean removed = false;
        if(inBounds(index)) {
            for(int i = index; i < size; i++) {
                texts[i] = i + 1 < texts.length ? texts[i + 1] : new TextField(maxWidth, 1);
            }
            removed = true;
            size--;
        }

        return removed;
    }

    public void clearTexts() {
        texts = new TextField[maxSize];
        size = 0;
    }

    /**
     *
     *
     * @param index
     * @return
     */
    private boolean inBounds(int index) {
        return index >= 0 && index < texts.length;
    }

    /**
     *
     *
     * @param index
     * @return
     */
    public TextField getTextField(int index) {
        return index < texts.length && index >= 0 ? texts[index] : null;
    }

    @Override
    public String[] getStringRender() {
        if(listener != null) {
            String[] retrieved = listener.retrieve();
            clearTexts();
            for (int i = 0; i < Math.min(retrieved.length, texts.length); i++) {
                addText(i, retrieved[i]);
            }
        }

        String[] render = new String[getHeight()];
        maximizeSize();
        for(int i = 0; i < render.length; i++) {
            render[i] = texts[i] != null ? texts[i].getStringRender()[0] : (" ").repeat(getWidth()); // will only get the first line of text
        }

        return render;
    }

    @Override
    protected Bounds getMaximizedSize() {
        return new Bounds(maxWidth, texts.length);
    }
}
