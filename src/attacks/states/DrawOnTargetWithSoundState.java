package attacks.states;

import attacks.SpecialAttack;
import elements.Enemy;
import org.newdawn.slick.Sound;

public class DrawOnTargetWithSoundState implements SpecialAttackState {

    private SpecialAttack animation;
    private Enemy target;
    private Sound sfx;
    private SpecialAttackState next;
    private boolean executed = false;
    private int shiftX, shiftY;


    public DrawOnTargetWithSoundState(SpecialAttack animation, Enemy target, int shiftX, int shiftY, Sound sfx, SpecialAttackState next) {
        this.animation = animation;
        this.target = target;
        this.sfx = sfx;
        this.next = next;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    /**
     * Executes the current state action
     */
    @Override
    public void execute() {
        if(!sfx.playing()){
            animation.setLocation(target.getX()-shiftX*16-56, target.getY()-shiftY*16);
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
