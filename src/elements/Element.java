package elements;

import org.newdawn.slick.geom.*;

/**
 * This class represents a generic element on the screen
 */
public class Element extends Rectangle {

    /**
     * Constructor, height and width must be specified
     * @param height
     * @param width
     */
    public Element(int x, int y, int height, int width){
        super(x, y, height, width);
    }

    /**
     * Draw an Element at a defined point
     * @param x
     * @param y
     */
    public void init(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * Changes the x and y of the image by an increment
     * @param dx
     * @param dy
     */
    public void move(int dx, int dy){
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public void moveX(int dx){
        move(dx, 0);
    }

    public void moveY(int dy){
        move(0, dy);
    }
}