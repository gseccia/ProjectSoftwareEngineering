/**
 * The collidable interface defines all the methods to check if two objects collide
 */
public interface Collidable{

    /**
     * Defines if two objects collided
     * @param c an object that implements Collidable
     * @return true if the two objects collide, false otherwise
     */
    public boolean collision(Collidable c);

    /**
     * Returns the position of the object in terms of coordinates
     * @return a map whose keys are "x" and "y"
     */
    public Map<String, Integer> getPosition(void);

    /**
     * Returns the width and the height of the object
     * @return a map whose keys are "height" and "width"
     */
    public Map<String, Integer> getDimentions(void);

}