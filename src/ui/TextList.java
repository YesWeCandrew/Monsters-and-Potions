package ui;

public class TextList extends Pane {

    TextField[] texts;
    String seperator;
    boolean horizontal;

    public TextList(boolean horizontal, String seperator, int maxSize) {
        super();
        this.horizontal = horizontal;
        this.seperator = seperator;
        this.texts = new TextField[maxSize];
    }

    public TextField getTextField(int index) {
        return index < texts.length && index >= 0 ? texts[index] : null;
    }

    private void renderToPane(boolean withSeparators) {
        int x = 0, y = 0;
        for(TextField text : texts) {
            text.setX(x);
            text.setY(y);
//            if(horizontal) {
//                if(withSeparators) {
//                    x += text.getWidth();
//                    Separator s = new Separator(x, y,  horizontal, )
//                }
//            }
//            else {
//
//            }
        }
    }
}
