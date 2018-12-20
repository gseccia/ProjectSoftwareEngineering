package attacks;

import elements.AnimatedElement;
import elements.Mob;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

/**
 * A class that implements the point blank range attack
 */
public class PointBlankRangeAttack extends AnimatedElement implements Attack {

    private Mob caster;
    private Sound sfx;

    /**
     * Constructor
     * @param caster the Mob object that casts the attack
     */
    public PointBlankRangeAttack(Mob caster) {
        super();
        this.caster = caster;
    }

    /**
     * Constructor
     * @param caster the Mob object that casts the attack
     * @param sfx the sound to play
     */
    public PointBlankRangeAttack(Mob caster, Sound sfx) {
        super();
        this.caster = caster;
        this.sfx = sfx;
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
        if(sfx != null) sfx.play(1, SoundStore.get().getMusicVolume());
    }

}
