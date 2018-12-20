package attacks.states;

import attacks.ultras.SpecialAttack;
import elements.Enemy;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

/**
 * This state draws the SpecialAttack on a certain enemy playing a sound
 */
public class DrawOnTargetWithSoundState implements SpecialAttackState {

    private SpecialAttack animation;
    private Enemy target;
    private Sound sfx;
    private SpecialAttackState next;
    private boolean executed = false;
    private int shiftX, shiftY;

    /**
     * Constructor
     * @param animation the SpecialAttack object
     * @param target the target Enemy
     * @param shiftX the x shift of the map
     * @param shiftY the y shift of the map
     * @param sfx the Sound to play
     * @param next the following SpecialAttackState
     */
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
            sfx.play(1, SoundStore.get().getMusicVolume());
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
