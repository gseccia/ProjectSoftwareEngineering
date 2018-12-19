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

/**
 * An abstract class for the attack that are to be drawn differently based on the caster's direction.
 * Implemented as a Template Method
 */
public abstract class DirectionalAttack extends AnimatedElement implements Attack {

    private Map<String, Animation> animations;
    private float drawX, drawY;

    Mob caster;

    /**
     * Constructor
     * @param id the attack's id
     * @param caster the Player object that casts the attack
     */
    DirectionalAttack(String id, Mob caster){
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

    /**
     * Hook method to set the x coordinate when the direction is UP
     * @return the x coordinate
     */
    protected abstract float setXUp();

    /**
     * Hook method to set the x coordinate when the direction is DOWN
     * @return the x coordinate
     */
    protected abstract float setXDown();

    /**
     * Hook method to set the x coordinate when the direction is LEFT
     * @return the x coordinate
     */
    protected abstract float setXLeft();

    /**
     * Hook method to set the x coordinate when the direction is RIGHT
     * @return the x coordinate
     */
    protected abstract float setXRight();

    /**
     * Hook method to set the y coordinate when the direction is UP
     * @return the y coordinate
     */
    protected abstract float setYUp();

    /**
     * Hook method to set the y coordinate when the direction is DOWN
     * @return the y coordinate
     */
    protected abstract float setYDown();

    /**
     * Hook method to set the y coordinate when the direction is LEFT
     * @return the y coordinate
     */
    protected abstract float setYLeft();

    /**
     * Hook method to set the y coordinate when the direction is RIGHT
     * @return the y coordinate
     */
    protected abstract float setYRight();

    /**
     * Hook method to set the height when the direction is horizontal
     * @return the height
     */
    protected abstract float setHeightHorizontal();

    /**
     * Hook method to set the width when the direction is horizontal
     * @return the width
     */
    protected abstract float setWidthHorizontal();

    /**
     * Hook method to set the height when the direction is vertical
     * @return the height
     */
    protected abstract float setHeightVertical();

    /**
     * Hook method to set the width when the direction is vertical
     * @return the width
     */
    protected abstract float setWidthVertical();

    /**
     * Hook method to set an x bias when the direction is UP
     * @return the x bias
     */
    protected abstract float setXBiasUp();

    /**
     * Hook method to set an x bias when the direction is DOWN
     * @return the x bias
     */
    protected abstract float setXBiasDown();

    /**
     * Hook method to set an x bias when the direction is LEFT
     * @return the x bias
     */
    protected abstract float setXBiasLeft();

    /**
     * Hook method to set an x bias when the direction is RIGHT
     * @return the x bias
     */
    protected abstract float setXBiasRight();

    /**
     * Hook method to set an y bias when the direction is UP
     * @return the y bias
     */
    protected abstract float setYBiasUp();

    /**
     * Hook method to set an y bias when the direction is DOWN
     * @return the y bias
     */
    protected abstract float setYBiasDown();

    /**
     * Hook method to set an y bias when the direction is LEFT
     * @return the y bias
     */
    protected abstract float setYBiasLeft();

    /**
     * Hook method to set an y bias when the direction is RIGHT
     * @return the y bias
     */
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
