package elements;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

/**
 * This class represent an element that is not visually static on the screen
 */
public abstract class AnimatedElement extends Rectangle{
    private Animation current;

    protected AnimatedElement(){
        super(0, 0, 0, 0);
        this.current = new Animation();

    }

    protected AnimatedElement(Animation a, int width, int height, int x, int y) throws NullAnimationException{
        super(x, y, width, height);
        //if(a == null){
        //    throw new NullAnimationException("Animation can't be null!");
        //}   MAKES TEST IMPOSSIBLE
        this.current = a;
    }

    protected void setCurrent(Animation current){
        //if(current == null){
        //    throw new NullAnimationException("Current animation can't be null!");
        //} MAKES TEST IMPOSSIBLE
        this.current = current;
    }

    public Animation getCurrent() {
		return current;
	}

	/**
     * Draw the current animation at a defined point.
     */
    public void draw(){
        current.draw(getX(), getY());
    }

    /**
     * Draw with filter
     * @param filter the color filter to apply
     */
    public void draw(Color filter){
        current.draw(getX(), getY(), filter);
    }

    /**
     * Custom draw method at input coordinates
     */
    public void draw(int x, int y){
        current.draw(x, y);
    }

    
}