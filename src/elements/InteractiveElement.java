package elements;

import org.newdawn.slick.*;

/**
 * This class represent an element that is not visually static on the screen
 */
public class InteractiveElement extends Element {
    private Animation current;

    protected InteractiveElement(Animation a){
        super(0, 0, 0, 0);
        this.current = a;
    }

    protected InteractiveElement(Animation a, int width, int height){
        super(0, 0, height, width);
        this.current = a;
    }

    protected InteractiveElement(Animation a, int width, int height, int x, int y){
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
    @Override
    public void draw(int x, int y){
        current.draw(x, y);
        setX(x);
        setY(y);
    }
}