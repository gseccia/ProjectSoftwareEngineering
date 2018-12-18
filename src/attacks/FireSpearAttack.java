package attacks;

import configuration.AttackConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Mob;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class FireSpearAttack extends RangedAttack {

    private final static String ID = "firespear";

    private Sound sfx;

    public FireSpearAttack(Mob caster) {
        super(ID, caster);
        AttackConfiguration attackconf = AttackConfiguration.getInstance();
        try {
            sfx = attackconf.getAttackSound(ID);
        } catch (SlickException | NoSuchElementInConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Signal that the attack was used
     */
    @Override
    public void attack() {
        sfx.play();
    }

    @Override
    protected float setXUp() {
        return caster.getX();
    }

    @Override
    protected float setXDown() {
        return caster.getX();
    }

    @Override
    protected float setXLeft() {
        return caster.getX() - caster.getWidth()*4;
    }

    @Override
    protected float setXRight() {
        return caster.getX();
    }

    @Override
    protected float setYUp() {
        return caster.getY() - caster.getHeight()*2;
    }

    @Override
    protected float setYDown() {
        return caster.getY();
    }

    @Override
    protected float setYLeft() {
        return caster.getY();
    }

    @Override
    protected float setYRight() {
        return caster.getY();
    }

    @Override
    protected float setHeightHorizontal() {
        return caster.getHeight();
    }

    @Override
    protected float setWidthHorizontal() {
        return caster.getWidth() * 5;
    }

    @Override
    protected float setHeightVertical() {
        return caster.getHeight() * 3;
    }

    @Override
    protected float setWidthVertical() {
        return caster.getWidth();
    }

    @Override
    protected float setXBiasUp() {
        return 0;
    }

    @Override
    protected float setXBiasDown() {
        return 0;
    }

    @Override
    protected float setXBiasLeft() {
        return 0;
    }

    @Override
    protected float setXBiasRight() {
        return caster.getWidth()*3/4;
    }

    @Override
    protected float setYBiasUp() {
        return 0;
    }

    @Override
    protected float setYBiasDown() {
        return caster.getHeight();
    }

    @Override
    protected float setYBiasLeft() {
        return 0;
    }

    @Override
    protected float setYBiasRight() {
        return 0;
    }
}
