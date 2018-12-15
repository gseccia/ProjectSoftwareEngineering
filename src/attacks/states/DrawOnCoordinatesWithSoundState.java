package attacks.states;

import attacks.SpecialAttack;
import org.newdawn.slick.Sound;

public class DrawOnCoordinatesWithSoundState implements SpecialAttackState {

    private SpecialAttack animation;
    private boolean executed = false;
    private Sound sfx;
    private float x, y;
    private SpecialAttackState next;

    public DrawOnCoordinatesWithSoundState(SpecialAttack animation, Sound sfx, float x, float y) {
        this.animation = animation;
        this.sfx = sfx;
        this.x = x;
        this.y = y;
        this.next = null;
    }

    public DrawOnCoordinatesWithSoundState(SpecialAttack animation, Sound sfx, float x, float y, SpecialAttackState next) {
        this.animation = animation;
        this.sfx = sfx;
        this.x = x;
        this.y = y;
        this.next = next;
    }

    /**
     * Executes the current state action
     */
    @Override
    public void execute() {
        if(!sfx.playing()){
            animation.setLocation(x, y);
            animation.setDrawable(true);
            sfx.play();
            executed = true;
        }
    }

    /**
     * @return true if the action was executed and finished
     */
    @Override
    public boolean finished() {
        return executed && !sfx.playing();
    }

    /**
     * @return if the state was executed
     */
    @Override
    public boolean executed() {
        return executed;
    }

    /**
     * @return the next state
     */
    @Override
    public SpecialAttackState next() {
        animation.setDrawable(false);
        return next;
    }
}
