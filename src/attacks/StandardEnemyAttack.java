package attacks;

import elements.AnimatedElement;
import elements.Mob;

public class StandardEnemyAttack extends AnimatedElement implements Attack {

    private Mob caster;

    public StandardEnemyAttack(Mob caster) {
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
