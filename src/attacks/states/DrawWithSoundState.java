package attacks.states;

import attacks.ultras.SpecialAttack;
import org.newdawn.slick.Sound;

public class DrawWithSoundState implements SpecialAttackState {

    private SpecialAttack attack;
    private Sound sfx;
    private SpecialAttackState next;
    private boolean executed = false;

    public DrawWithSoundState(SpecialAttack attack, Sound sfx, SpecialAttackState next) {
        this.attack = attack;
        this.sfx = sfx;
        this.next = next;
    }

    /**
     * Executes the current state action
     */
    @Override
    public void execute() {
        if(!sfx.playing()){
            attack.setDrawable(true);
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
     * @return the next state
     */
    @Override
    public SpecialAttackState next() {
        attack.setDrawable(false);
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
