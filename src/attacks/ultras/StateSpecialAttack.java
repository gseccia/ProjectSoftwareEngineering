package attacks.ultras;

import attacks.states.SpecialAttackState;
import configuration.NoSuchElementInConfigurationException;
import configuration.SpecialAttackConfiguration;
import elements.AnimatedElement;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * An abstraction to to define a state-based special attack
 */
public abstract class StateSpecialAttack extends AnimatedElement implements SpecialAttack{

    private int RELOADING_TIME;
    private int remaining = 0;
    private Image icon;
    private boolean drawable;

    protected SpecialAttackState current;
    boolean active;

    /**
     * Constructor
     * @param RELOADING_TIME the cooldown time (in fps)
     * @param id the special attack id
     */
    StateSpecialAttack(int RELOADING_TIME, String id) {
        this.RELOADING_TIME = RELOADING_TIME;
        try {
            icon = SpecialAttackConfiguration.getInstance().getIcon(id);
        } catch (NoSuchElementInConfigurationException | SlickException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recharges the attack
     */
    @Override
    public void reload() {
        if(remaining > 0){
            remaining--;
        }
    }

    /**
     * @return true if the SpecialAttack is currently active
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * @return the icon of the SpecialAttack
     */
    @Override
    public Image getIcon() {
        return icon;
    }

    /**
     * @return true if the SpecialAttack is ready to be used
     */
    @Override
    public boolean isReady() {
        return remaining == 0;
    }

    @Override
    public void setDrawable(boolean flag) {
        drawable = flag;
    }

    @Override
    public boolean isDrawable() {
        return drawable;
    }

    /**
     * Executes an iteration step of the move
     */
    @Override
    public void iterationStep() {
        if(isActive() && current != null) {

            if (!current.executed()) current.execute();

            if (current.executed() && current.finished()) current = current.next();
        }
        else{
            active = false;
            remaining = RELOADING_TIME;
        }
    }

}
