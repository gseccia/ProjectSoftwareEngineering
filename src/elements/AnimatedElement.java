package elements;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

/**
 * This class represent an element that is not visually static on the screen
 */
public abstract class AnimatedElement extends Rectangle implements Movable{
    private Animation current;

    protected AnimatedElement(Animation a, int width, int height, int x, int y) throws NullAnimationException{
        super(x, y, height, width);
        if(a == null){
            throw new NullAnimationException("Animation can't be null!");
        }
        this.current = a;
    }

    protected void setCurrent(Animation current) throws NullAnimationException{
        if(current == null){
            throw new NullAnimationException("Current animation can't be null!");
        }
        this.current = current;
    }

    /**
     * Draw the current animation at a defined point.
     */
    public void draw(){
        current.draw(getX(), getY());
    }
}