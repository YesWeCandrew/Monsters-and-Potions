package ui;

import java.util.ArrayList;

public class TextList extends Element {

    TextField[] texts;
    Listener<String[]> listener;
    int size, maxSize, maxWidth;

    /**
     * a vertical list of TextFields that are exclusively one line height
     *
     * @param maxSize the max number of texts, also the max line numbers occupied by the TextList
     * @param maxWidth the max width of each element of the TextList
     * @author Mitchell Barker
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
     * adds the provided text at the provided index, shifting all elements tot he right of the index left by one.
     *
     * @param index the index of the inserted text
     * @param text the text
     * @return true if the text was added, false if not (index out of bounds or TextsList is full)
     * @author Mitchell Barker
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
     * appends the provided texts to the end of the array, will stop appending if the array is full
     *
     * @param texts the texts to be appended
     * @return true if all texts could be appended, otherwise false
     * @author Mitchell Barker
     */
    public boolean addAllText(ArrayList<String> texts) {
        boolean insertedAll = this.texts.length >= texts.size() + size;
        for(int i = size; i < Math.min(this.texts.length, texts.size() + size); i++) {
            TextField field = new TextField(getWidth(), 1);
            field.setText(texts.get(i - size));

            this.texts[i] = field;
            size++;
        }

        return insertedAll;
    }

    /**
     * removes the text at the index, shifting each proceeding text the the left by one. if the index is out of bounds
     * the the function returns false, otherwise true.
     *
     * note: will return true regardless of whether the value is null or not (i.e. size < index < maxSize)
     *
     * @param index the index of the text in the TextList to be removed
     * @return false if index is inbounds, other true
     * @author Mitchell Barker
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
     * checks if the index is in the bounds of the TextList array (i.e. 0 <= index < maxSize)
     *
     * note: function will return true regardless of whether the element at index is null or not
     * (i.e size < index < maxSize)
     *
     * @param index the index to check
     * @return if the index provided is within the max bounds
     * @author Mitchell Barker
     */
    private boolean inBounds(int index) {
        return index >= 0 && index < texts.length;
    }

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
