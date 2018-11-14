package elements;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

/**
 * This class represent an element that is not visually static on the screen
 */
public abstract class AnimatedElement extends Rectangle implements Movable{
    private Animation current;

    protected AnimatedElement(Animation a, int width, int height, int x, int y){
        super(x, y, height, width);
        this.current = a;
    }

    public void setCurrent(Animation current) {
        this.current = current;
    }

    /**
     * Draw the current animation at a defined point.
     * @param x
     * @param y
     */
    public void draw(){
        current.draw(getX(), getY());
    }
}