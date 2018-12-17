package attacks;

import configuration.AttackConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.AnimatedElement;
import elements.Mob;
import managers.Directions;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.HashMap;
import java.util.Map;

public class ShortRangeAttack extends AnimatedElement implements Attack {

    private final static String id = "shortrange";

    private Mob caster;
    private Map<String, Animation> animations;
    private float drawX, drawY;
    private Sound sfx;

    public ShortRangeAttack(Mob caster) {
        animations = new HashMap<>();
        AttackConfiguration attackconf = AttackConfiguration.getInstance();
        try {
            animations.put("right", attackconf.getRightAnimation(id));
            animations.put("left", attackconf.getLeftAnimation(id));
            animations.put("down", attackconf.getDownAnimation(id));
            animations.put("up", attackconf.getUpAnimation(id));

            sfx = attackconf.getAttackSound(id);
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
                setCurrent(animations.get("left"));
                drawX = x + caster.getHeight()/4;
                drawY = y;
                break;

            case Directions.RIGHT:
                x = caster.getX();
                y = caster.getY();
                height = caster.getHeight();
                width = caster.getWidth() * 2;
                setCurrent(animations.get("right"));
                drawX = x + caster.getWidth()*3/4;
                drawY = y;
                break;

            case Directions.UP:
                x = caster.getX();
                y = caster.getY() - caster.getHeight();
                height = caster.getHeight() * 2;
                width = caster.getWidth();
                setCurrent(animations.get("up"));
                drawX = x;
                drawY = y + caster.getHeight()/4;
                break;

            case Directions.DOWN:
                x = caster.getX();
                y = caster.getY();
                height = caster.getHeight() * 2;
                width = caster.getWidth();
                setCurrent(animations.get("down"));
                drawX = x;
                drawY = y + caster.getHeight();
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

    /**
     * Signal that the attack was used
     */
    @Override
    public void attack() {
        sfx.play();
    }

}
