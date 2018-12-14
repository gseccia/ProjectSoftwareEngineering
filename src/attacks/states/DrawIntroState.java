package attacks.states;

import elements.Intro;
import org.newdawn.slick.Sound;

public class DrawIntroState implements SpecialAttackState {

    private Intro intro;
    private Sound sfx;
    private boolean executed = false;
    private float x, y;
    private SpecialAttackState next;

    public DrawIntroState(Intro intro, Sound sfx, float x, float y, SpecialAttackState next) {
        this.intro = intro;
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
            intro.setLocation(x, y);
            intro.setPlaying(true);
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
        intro.setPlaying(false);
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
