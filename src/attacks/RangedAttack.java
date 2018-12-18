package attacks;

import configuration.AttackConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.AnimatedElement;
import elements.Mob;
import managers.Directions;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

public abstract class RangedAttack extends AnimatedElement implements Attack {

    private Map<String, Animation> animations;
    private float drawX, drawY;

    protected Mob caster;

    public RangedAttack(String id, Mob caster){
        animations = new HashMap<>();
        AttackConfiguration attackconf = AttackConfiguration.getInstance();
        try {
            animations.put("right", attackconf.getRightAnimation(id));
            animations.put("left", attackconf.getLeftAnimation(id));
            animations.put("down", attackconf.getDownAnimation(id));
            animations.put("up", attackconf.getUpAnimation(id));

        } catch (SlickException | NoSuchElementInConfigurationException e) {
            e.printStackTrace();
        }
        this.caster = caster;
    }

    /**
     * Draw the current animation at a defined point.
     */
    @Override
    public void draw() {
        super.draw((int)drawX, (int)drawY);
    }

    protected abstract float setXUp();

    protected abstract float setXDown();

    protected abstract float setXLeft();

    protected abstract float setXRight();

    protected abstract float setYUp();

    protected abstract float setYDown();

    protected abstract float setYLeft();

    protected abstract float setYRight();

    protected abstract float setHeightHorizontal();

    protected abstract float setWidthHorizontal();

    protected abstract float setHeightVertical();

    protected abstract float setWidthVertical();

    protected abstract float setXBiasUp();

    protected abstract float setXBiasDown();

    protected abstract float setXBiasLeft();

    protected abstract float setXBiasRight();

    protected abstract float setYBiasUp();

    protected abstract float setYBiasDown();

    protected abstract float setYBiasLeft();

    protected abstract float setYBiasRight();

    /**
     * Sets the hitbox of the attack
     */
    @Override
    public void setHitbox() {
        float x, y, height, width;
        switch (caster.getCurrentDirection()){
            case Directions.LEFT:
                x = setXLeft();
                y = setYLeft();
                height = setHeightHorizontal();
                width = setWidthHorizontal();
                setCurrent(animations.get("left"));
                drawX = x + setXBiasLeft();
                drawY = y + setYBiasLeft();
                break;

            case Directions.RIGHT:
                x = setXRight();
                y = setYRight();
                height = setHeightHorizontal();
                width = setWidthHorizontal();
                setCurrent(animations.get("right"));
                drawX = x + setXBiasRight();
                drawY = y + setYBiasRight();
                break;

            case Directions.UP:
                x = setXUp();
                y = setYUp();
                height = setHeightVertical();
                width = setWidthVertical();
                setCurrent(animations.get("up"));
                drawX = x + setXBiasUp();
                drawY = y + setYBiasUp();
                break;

            case Directions.DOWN:
                x = setXDown();
                y = setYDown();
                height = setHeightVertical();
                width = setWidthVertical();
                setCurrent(animations.get("down"));
                drawX = x + setXBiasDown();
                drawY = y + setYBiasDown();
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
