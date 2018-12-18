package attacks.states;


import managers.ResourceManager;

public class HandleMusicState implements SpecialAttackState {

    private SpecialAttackState next;
    private ResourceManager rs;
    private int state;
    private boolean executed = false;

    public HandleMusicState(ResourceManager rs, int state, SpecialAttackState next) {
        this.next = next;
        this.rs = rs;
        this.state = state;
    }

    /**
     * Executes the current state action
     */
    @Override
    public void execute() {
        rs.setState(state);
        executed = true;
    }

    /**
     * @return true if the action was executed and finished
     */
    @Override
    public boolean finished() {
        return executed;
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
