package attacks;

import org.newdawn.slick.geom.Shape;

/**
 * An interface that defines all the common methods of an attack object
 */
public interface Attack {

    /**
     * The attack must have an intersection method to interact with the other objects
     */
    boolean intersects(Shape s);

    /**
     * Sets the hitbox of the attack
     */
    void setHitbox();

    /**
     * Draws the animation of the attack
     */
    void draw();

    /**
     * Signal that the attack was used
     */
    void attack();


}
