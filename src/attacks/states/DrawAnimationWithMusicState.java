package attacks.states;

import elements.AnimatedElement;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Sound;

public class DrawAnimationWithMusicState implements SpecialAttackState {

    private AnimatedElement animation;
    private Sound sfx;
    private boolean executed = false;
    private float x, y;
    private SpecialAttackState next;

    public DrawAnimationWithMusicState(AnimatedElement animation, Sound sfx, float x, float y, SpecialAttackState next) {
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
            sfx.play();
            animation.setLocation(x, y);
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
     * @return the next state
     */
    @Override
    public SpecialAttackState next() {
        return next;
    }

    /**
     * @return if the state was executed
     */
    @Override
    public boolean executed() {
        return executed;
    }
}
