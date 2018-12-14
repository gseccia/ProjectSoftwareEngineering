package attacks.states;

import org.newdawn.slick.Sound;

public class SoundState implements SpecialAttackState{

    private Sound sound;
    private boolean executed = false;
    private SpecialAttackState next;

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
            sound.play();
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
