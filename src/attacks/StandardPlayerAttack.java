package attacks;

import elements.AnimatedElement;
import elements.Mob;
import managers.Directions;

public class StandardPlayerAttack extends AnimatedElement implements Attack {

    private Mob caster;

    public StandardPlayerAttack(Mob caster) {
        this.caster = caster;
    }

    /**
     * Sets the hitbox of the attack
     */
    @Override
    public void setHitbox(){
        float x, y, height, width;
        switch (caster.getCurrentDirection()){
            case Directions.LEFT:
                x = caster.getX() - caster.getWidth();
                y = caster.getY();
                height = caster.getHeight();
                width = caster.getWidth() * 2;
                break;

            case Directions.RIGHT:
                x = caster.getX();
                y = caster.getY();
                height = caster.getHeight();
                width = caster.getWidth() * 2;
                break;

            case Directions.UP:
                x = caster.getX();
                y = caster.getY() + caster.getHeight();
                height = caster.getHeight() * 2;
                width = caster.getWidth();
                break;

            case Directions.DOWN:
                x = caster.getX();
                y = caster.getY();
                height = caster.getHeight() * 2;
                width = caster.getWidth();
                break;

            default:
                x = 0;
                y = 0;
                height = 0;
                width = 0;
        }
        setX(x);
        setY(y);
        setHeight(height);
        setWidth(width);
    }
}
