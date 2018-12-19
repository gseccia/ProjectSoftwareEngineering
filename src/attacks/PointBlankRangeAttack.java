package attacks;

import elements.AnimatedElement;
import elements.Mob;

/**
 * A class that implements the point blank range attack
 */
public class PointBlankRangeAttack extends AnimatedElement implements Attack {

    private Mob caster;

    /**
     * Constructor
     * @param caster the Mob object that casts the attack
     */
    public PointBlankRangeAttack(Mob caster) {
        super();
        this.caster = caster;
    }

    /**
     * Sets the hitbox of the attack (equal to the hitbox of the caster)
     */
    @Override
    public void setHitbox() {
        setX(caster.getX());
        setY(caster.getY());
        setWidth(caster.getWidth());
        setHeight(caster.getHeight());
    }

    /**
     * Signal that the attack was used
     */
    @Override
    public void attack() {

    }

}
