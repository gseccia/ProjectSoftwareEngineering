/**
 * This class represent a structural element, that visually is a static image
 */
public class StructuralElement extends Element{

    private Image texture;

    /**
     * The constructor receives as input the path of an image, height and width are
     * the one of the images
     * @param path
     */
    public StructuralElement(String path){
        super();
        texture = new Image(path);
        setHeight(texture.getHeight())
        setWidth(texture.getWidth())
    }

    /**
     * Changes the image specifying a new path, height and width change accordingly
     * @param path
     */
    public void setImage(String path){
        super();
        texture = new Image(path);
        setHeight(texture.getHeight());
        setWidth(texture.getWidth());
    }

    /**
     * Draw an image at a defined point.
     * @param x
     * @param y
     */
    @Override
    public void draw(int x, int y){
        texture.draw(x, y);
        setX(x);
        setY(y);
    }

}