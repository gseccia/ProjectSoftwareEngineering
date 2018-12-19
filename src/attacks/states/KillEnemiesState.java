package attacks.states;

import elements.Enemy;

import java.util.Set;

/**
 * This state set to 0 the HP of the enemies passed as parameter
 */
public class KillEnemiesState implements SpecialAttackState {

    private Set<Enemy> targets;
    private boolean finished = false;
    private SpecialAttackState next;

    /**
     * Constructor
     * @param targets a Set containing the enemies to kill
     */
    public KillEnemiesState(Set<Enemy> targets) {
        this.targets = targets;
        this.next = null;
    }

    /**
     * Constructor
     * @param targets a Set containing the enemies to kill
     * @param next the following SpecialAttackState
     */
    public KillEnemiesState(Set<Enemy> targets, SpecialAttackState next) {
        this.targets = targets;
        this.next = next;
    }

    /**
     * Executes the current state action
     */
    @Override
    public void execute() {
        for(Enemy e : targets){
            e.damage(e.getMaxHp());
        }
        finished = true;
    }

    /**
     * @return true if the action was executed and finished
     */
    @Override
    public boolean finished() {
        return finished;
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
        return finished;
    }
}
