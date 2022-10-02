package ui;

public class StatisticsBar extends Element {

    String field;
    Listener<Integer> listener;
    int min, max, value, barDivisor, maxWidth;
    char barValueChar, barEmptyChar;

    /**
     * simple statistics bar for displaying health and action points. displays the name, ratio and a bar representing
     * the current value as compared to the maximum value
     *
     * @param field the statistic's name
     * @param min minimum value
     * @param max maximum value
     * @param barDivisor the amount of points the bar character represents
     * @param barValueChar the character that represents the bar
     * @param barEmptyChar the character that represents the missing amount of points
     * @author Mitchell Barker
     */
    public StatisticsBar(String field, int min, int max, int barDivisor, char barValueChar, char barEmptyChar, int maxWidth) {
        this.field = field;
        this.min = min;
        this.max = max;
        this.barDivisor = barDivisor;
        this.barValueChar = barValueChar;
        this.barEmptyChar = barEmptyChar;
        this.maxWidth = maxWidth;
    }

    /**
     * simple statistics bar for displaying health and action points. displays the name and ratio of current points
     * to maximum points
     *
     * @param field the statistic's name
     * @param min minimum value
     * @param max maximum value
     * @param maxWidth the maximum width (in characters) of the statistics element when it is rendered
     * @author Mitchell Barker
     */
    public StatisticsBar(String field, int min, int max, int maxWidth) {
        this(field, min, max, 1, Character.MIN_VALUE, Character.MIN_VALUE, maxWidth);
    }

    @Override
    public String[] getStringRender() {
        if(listener != null) {
            this.value = listener.retrieve();
        }
        String ratio = getRatio(), bar = getBar();
        int reqLength = field.length() + getRatio().length() + getBar().length() + 1;
        return new String[]{field + (" ").repeat(maxWidth - reqLength) + getRatio() + " " + getBar()};
    }

    @Override
    protected Bounds getMaximizedSize() {
        return new Bounds(maxWidth, 1);
    }

    /**
     * sets the value of the statistics bar which will be rounded to fit within the allowed bounds of the statistics bar
     *
     * @param value the value to be set
     * @return true if the value was within the bounds, false if the value had to be adjusted to fit within the bounds
     * @author Mitchell Barker
     */
    public boolean setValue(int value) {
        boolean inBounds = value >= min && value <= max;
        this.value = Math.min(this.max, Math.max(this.min, value));

        return inBounds;
    }

    /**
     * Sets a listener which can get a value
     *
     * @param listener which defines the function to retrieve the current value the statistics bar should represent
     * @author Mitchell Barker
     */
    public void setListener(Listener<Integer> listener) {
        this.listener = listener;
    }

    public int getValue() {
        return value;
    }

    /**
     * gets the ratio of value to max value in a string form i.e
     *
     * "(56/100)" or "(14/100)"
     *
     * @return the string ratio of the value and the max value
     * @author Mitchell Barker
     */
    private String getRatio() {
        return "(" + value + "/" + max + ")";
    }

    /**
     * returns a visual representation of the current value that statistics bar represents using the characters declared
     * when the object was created, the 
     *
     * @return the string representation of the visual bar
     * @author Mitchell Barker
     */
    private String getBar() {
        String bar = "";
        if(barValueChar != Character.MIN_VALUE) {
            bar = String.valueOf(barValueChar).repeat(value / barDivisor) + String.valueOf(barEmptyChar).repeat((max - value) / barDivisor);
        }

        return bar;
    }
}
