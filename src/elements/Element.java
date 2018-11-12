/**
 * This class represents a generic element on the screen
 */
public class Element extends Shape {

    private int height, width;

    /**
     * No parameters constructor
     */
    public Element(){
        super();
    }

    /**
     * Constructor, height and width must be specified
     * @param height
     * @param width
     */
    public Element(int height, int width){
        super();
        this.height = height;
        this.width = width;
        setMaxX(width);
        setMaxY(height)
    }

    /**
     * Draw an Element at a defined point
     * @param x
     * @param y
     */
    public void draw(int x, int y) {
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


    public int getHeight() {
        return height;
    }

    /**
     * Sets a new height for the element, handles the maxY change
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
        setMaxY(getY() + height);
    }

    public int getWidth() {
        return width;
    }

    /**
     * Sets a new width for the element, handles the maxX change
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
        setMaxX(getX() + width)
    }
}