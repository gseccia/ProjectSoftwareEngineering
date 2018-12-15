package attacks.states;

import elements.Enemy;

import java.util.Set;

public class DamageEnemiesState implements SpecialAttackState {

    private Set<Enemy> targets;
    private boolean finished = false;
    private int damage;
    private SpecialAttackState next;

    public DamageEnemiesState(Set<Enemy> targets, int damage) {
        this.targets = targets;
        this.damage = damage;
        this.next = null;
    }

    public DamageEnemiesState(Set<Enemy> targets, int damage, SpecialAttackState next) {
        this.damage = damage;
        this.targets = targets;
        this.next = next;
    }

    /**
     * Executes the current state action
     */
    @Override
    public void execute() {
        for(Enemy e : targets){
            e.damage(damage);
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
