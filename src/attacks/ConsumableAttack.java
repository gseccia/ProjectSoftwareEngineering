package attacks;

import elements.NotPositiveValueException;
import elements.Player;
import org.newdawn.slick.geom.Shape;

/**
 * The object of this class buff the player attack, allowing it to use a different attack for a limited number of uses
 */
public class ConsumableAttack implements Attack {

    private Player caster;
    private Attack normal, current;
    private int uses, damage, baseDamage;

    /**
     * Constructor
     * @param caster the Player object that casts the attack
     * @param special the limited-uses attack object
     * @param normal the default attack object
     * @param uses the number of uses
     * @param multiplier the attack multiplier
     */
    public ConsumableAttack(Player caster, Attack special, Attack normal, int uses, int multiplier) {
        this.caster = caster;
        this.current = special;
        this.normal = normal;
        this.uses = uses;
        this.baseDamage = caster.getAttackDamage();
        this.damage = caster.getAttackDamage() * multiplier;
    }

    /**
     * The attack must have an intersection method to interact with the other objects
     *
     * @param s the shape object to test
     */
    @Override
    public boolean intersects(Shape s) {
        return current.intersects(s);
    }

    /**
     * Sets the hitbox of the attack
     */
    @Override
    public void setHitbox() {
        current.setHitbox();
    }

    /**
     * Draws the animation of the attack
     */
    @Override
    public void draw() {
        current.draw();
    }

    /**
     * Signal that the attack was used
     */
    @Override
    public void attack() {
        if(uses > 0){
            try {
                caster.setAttackDamage(damage);
            } catch (NotPositiveValueException e) {
                e.printStackTrace();
            }
            uses--;
        }
        else{
            try {
                caster.setAttackDamage(baseDamage);
            } catch (NotPositiveValueException e) {
                e.printStackTrace();
            }
            current = normal;
        }
        current.attack();
    }
}
