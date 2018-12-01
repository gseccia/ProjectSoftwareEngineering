package attacks;

import elements.AnimatedElement;
import elements.Mob;

public class PointBlankRangeAttack extends AnimatedElement implements Attack {

    private Mob caster;

    public PointBlankRangeAttack(Mob caster) {
        super();
        this.caster = caster;
    }

    /**
     * Sets the hitbox of the attack
     */
    @Override
    public void setHitbox() {
        setX(caster.getX());
        setY(caster.getY());
        setWidth(caster.getWidth());
        setHeight(caster.getHeight());
    }
}
