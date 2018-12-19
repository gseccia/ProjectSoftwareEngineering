package attacks.states;

import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

/**
 * This state plays a sound
 */
public class SoundState implements SpecialAttackState{

    private Sound sound;
    private boolean executed = false;
    private SpecialAttackState next;

    /**
     * Constructor
     * @param sound the Sound to play
     * @param next the following SpecialAttackState
     */
    public SoundState(Sound sound, SpecialAttackState next) {
        this.sound = sound;
        this.next = next;
    }

    /**
     * Executes the current state action
     */
    @Override
    public void execute() {
        if(!sound.playing()){
            sound.play(1, SoundStore.get().getMusicVolume());
            executed = true;
        }
    }

    /**
     * @return true if the action was executed and finished
     */
    @Override
    public boolean finished() {
        return executed && !sound.playing();
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
        return next;
    }
}
