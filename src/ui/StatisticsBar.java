package ui;

public class StatisticsBar extends Element {

    String field;
    Listener<Integer> listener;
    int min, max, value, barDivisor, maxWidth;
    char barValueChar, barEmptyChar;

    public StatisticsBar(String field, int min, int max, int barDivisor, char barValueChar, char barEmptyChar, int maxWidth) {
        this.field = field;
        this.min = min;
        this.max = max;
        this.barDivisor = barDivisor;
        this.barValueChar = barValueChar;
        this.barEmptyChar = barEmptyChar;
        this.maxWidth = maxWidth;
    }

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
    public void maximizeSize() {
        setWidth(maxWidth);
        setHeight(1);
    }

    public boolean setValue(int value) {
        boolean inBounds = value >= min && value <= max;
        this.value = Math.min(this.max, Math.max(this.min, value));

        return inBounds;
    }

    public void setListener(Listener<Integer> listener) {
        this.listener = listener;
    }

    public int getValue() {
        return value;
    }

    private String getRatio() {
        return "(" + value + "/" + max + ")";
    }

    private String getBar() {
        String bar = "";
        if(barValueChar != Character.MIN_VALUE) {
            bar = String.valueOf(barValueChar).repeat(value / barDivisor) + String.valueOf(barEmptyChar).repeat((max - value) / barDivisor);
        }

        return bar;
    }
}
