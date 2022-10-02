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

    /**
     * Views a square section of the map centred around the hero, ViewPort does not modify the Map object provided.
     * ViewPort will show walls where a coordinate is out of bounds (i.e. if the play is close to a corner edge of the
     * map)
     *
     * @param map the map to view
     * @param viewRadius the radius of the ViewPort (width = height = viewRadius * 2 + 1 + (bordered ? 2 : 0))
     * @param bordered adds a border around the ViewPort when rendering to String
     * @auther Mitchell Barker
     */
    public ViewPort(Map map, int viewRadius, boolean bordered) {
        this.map = map;
        this.viewRadius = viewRadius;
        this.bordered = bordered;

        int diameter = viewRadius * 2 + 1;
        setWidth(bordered ? diameter + 2 : diameter);
        setHeight(bordered ? diameter + 2 : diameter);
    }

    /**
     * Views a square section of the map centred around the hero, ViewPort does not modify the Map object provided.
     * ViewPort will show walls where a coordinate is out of bounds (i.e. if the play is close to a corner edge of the
     * map).
     *
     * border is assumed to be true and viewRadius is left to be determined later.
     *
     * @param map the map the view port will view from
     * @author Mitchell Barker
     */
    public ViewPort(Map map) {
        this(map, 0, true);
    }

    public void setViewRadius(int viewRadius) {
        this.viewRadius = viewRadius;
    }

    @Override
    public String[] getStringRender() {
        int d = viewRadius * 2 + 1;
        Position centre = map.getHeroPositionReference();
        char OutOfBoundsChar = (char)(new Wall().getChar());

        String[] view = new String[bordered ? d + 2 : d];
        int i = 0;
        if(bordered) {
            String horizontalBorder = String.valueOf(borderChar).repeat(d + 2);
            view[i] = horizontalBorder;
            view[d + 1] = horizontalBorder;
            i++;
        }

        for(int y = centre.getY() - viewRadius; y <= centre.getY() + viewRadius; y++) {
            String row = bordered ? String.valueOf(borderChar) : "";
            for(int x = centre.getX() - viewRadius; x <= centre.getX() + viewRadius; x++) {
                boolean inMapBounds = x >= 0 && x < map.getWidth() && y >= 0 && y < map.getHeight();

                char value = OutOfBoundsChar;
                if(inMapBounds) {
                    GameObject obj = map.getObjectAt(new Position(x, y));
                    value = obj != null ? obj.getChar() : ' ';
                }

                row += value;
            }
            row += bordered ? borderChar : "";
            view[i] = row;
            i++;
        }

        return view;
    }

    @Override
    protected Bounds getMaximizedSize() {
        return new Bounds(bordered ? viewRadius * 2 + 3 : viewRadius * 2 + 1, bordered ? viewRadius * 2 + 3 : viewRadius * 2 + 1);
    }
}
