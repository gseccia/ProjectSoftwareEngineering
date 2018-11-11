/**
 * This class represent a structural element, that visually is an image
 */
public class StructuralElement extends Shape{

    private Image texture = null;
    private int height, width;

    /**
     * Constructor without an image, height and width must be specified
     * @param height
     * @param width
     */
    public StructuralElement(int height, int width){
        super();
        this.height = height;
        this.width = width;
        setMaxX(width);
        setMaxY(height)
    }

    /**
     * The constructor receives as input the path of an image, height and width are
     * the one of the images
     * @param path
     */
    public StructuralElement(String path){
        super();
        texture = new Image(path);
        height = texture.getHeight();
        width = texture.getWidth();
        setMaxX(width);
        setMaxY(height)
    }

    /**
     * Changes the image specifying a new path, height and width change accordingly
     * @param path
     */
    public void setImage(String path){
        super();
        texture = new Image(path);
        height = texture.getHeight();
        width = texture.getWidth();
        setMaxX(this.getX() + width);
        setMaxY(this.getY() + height)
    }

    /**
     * Draw an image in a defined point
     * @param x
     * @param y
     */
    public void draw(int x, int y){
        texture.draw(x, y);
        setX(x);
        setY(y);
        setMaxX(x + width);
        setMaxY(y + height);
    }

    /**
     * Changes the x and y of the image by an increment
     * @param dx
     * @param dy
     */
    public void move(int dx, int dy){
        setX(getX()+dx);
        setY(getY()+dy);
        setMaxX(getX()+dx+width);
        setMaxY(getY()+dy+height);
    }
}