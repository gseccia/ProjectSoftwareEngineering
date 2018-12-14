package attacks.states;

public interface SpecialAttackState {

    /**
     * Executes the current state action
     */
    void execute();

    /**
     * @return true if the action was executed and finished
     */
    boolean finished();

    /**
     * @return the next state
     */
    SpecialAttackState next();

    /**
     * @return if the state was executed
     */
    boolean executed();

}
