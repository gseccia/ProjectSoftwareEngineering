package elements;

public interface Movable {

    /**
     * Move the character of a certain increment on the x axis based on the current position
     * @param dx the increment of x position
     */
    public void moveX(int dx);


    /**
     * Move the character of a certain position on the y axis based on the current position
     * @param dy the increment of the y position
     */
    public void moveY(int dy);

    /**
     * Set the absolute position of a charachter
     * @param x
     * @param y
     */
    public void setPosition(int x, int y);
}
