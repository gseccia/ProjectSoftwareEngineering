package attacks;

import org.newdawn.slick.geom.Shape;

public interface Attack {

    /**
     * The attack must have an intersection method to interact with the other objects
     */
    boolean intersects(Shape s);

    /**
     * Sets the hitbox of the attack
     */
    void setHitbox();

}
