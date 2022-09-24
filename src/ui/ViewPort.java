package ui;

import map.Map;
import map.Position;
import objects.GameObject;
import objects.Wall;

public class ViewPort extends Element {

    Map map;
    int viewRadius;
    boolean bordered;
    char borderChar = '@';

    public ViewPort(Map map, int viewRadius, boolean bordered) {
        this.map = map;
        this.viewRadius = viewRadius;

        int diameter = viewRadius * 2 + 1;
        setWidth(diameter + 2);
        setHeight(diameter + 2);
    }

    public ViewPort(Map map) {
        this(map, 0, true);
    }

    public void setViewRadius(int viewRadius) {
        this.viewRadius = viewRadius;
    }

    // TODO create none wall border for aesthetic reasons
    @Override
    public String[] getStringRender() {
        int d = viewRadius * 2 + 1;
        Position centre = map.getHeroPositionReference();
        char OutOfBoundsChar = (char)(new Wall().getChar());

        String[] view = new String[d + 2];
        String horizontalBorder = String.valueOf(borderChar).repeat(d + 2);
        view[0] = horizontalBorder;
        // TODO include Cardinality (rotate view port with rotation of hero by changing bounds of loops, counter, and getObjectAt() call parameters)
        int i = 1;
        for(int y = centre.getY() - viewRadius; y <= centre.getY() + viewRadius; y++) {
            String row = String.valueOf(borderChar);
            for(int x = centre.getX() - viewRadius; x <= centre.getX() + viewRadius; x++) {
                boolean inMapBounds = x >= 0 && x < map.getWidth() && y >= 0 && y < map.getHeight();

                char value = OutOfBoundsChar;
                if(inMapBounds) {
                    GameObject obj = map.getObjectAt(new Position(x, y));
                    value = obj != null ? obj.getChar() : ' ';
                }

                row += value;
            }
            row += borderChar;
            view[i] = row;
            i++;
        }
        view[i] = horizontalBorder;

        return view;
    }
}
