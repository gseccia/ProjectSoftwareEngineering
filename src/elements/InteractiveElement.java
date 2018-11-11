/**
 * This class represent an element that is not visually static on the screen
 */
public class InteractiveElement extends Element{
    private Animation current;

    public InteractiveElement(Animation a){
        super();
        this.current = a;
    }

    public InteractiveElement(Animation a, int width, int height){
        super(height, width);
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