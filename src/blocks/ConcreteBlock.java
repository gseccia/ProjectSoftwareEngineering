package blocks;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import configuration.PlayerCommands;
import main.gamestates.GameStates;

public class ConcreteBlock extends Block {
	private PlayerCommands pc = PlayerCommands.getPlayerCommandsInstance();
    protected ConcreteBlock(int state, String mapName) {
        super(state, mapName);
    }

    /**
     * Check if the game is to be paused
     *
     * @param in the Input object
     * @return true if the game has to enter in the paused state
     */
    @Override
    protected boolean isPaused(Input in) {
        return in.isKeyPressed(Input.KEY_P);
    }

    /**
     * Check if the user wants to go down
     *
     * @param in the Input object
     * @return true if the player wants to go down
     */
    @Override
    protected boolean goDown(Input in) {
        return in.isKeyDown(pc.getDown());
    }

    /**
     * Check if the user wants to go up
     *
     * @param in the Input object
     * @return true if the player wants to go up
     */
    @Override
    protected boolean goUp(Input in) {
        return in.isKeyDown(pc.getUp());
    }

    /**
     * Check if the user wants to go right
     *
     * @param in the Input object
     * @return true if the player wants to go right
     */
    @Override
    protected boolean goRight(Input in) {
        return in.isKeyDown(pc.getRight());
    }

    /**
     * Check if the user wants to go left
     *
     * @param in the Input object
     * @return true if the player wants to go left
     */
    @Override
    protected boolean goLeft(Input in) {
        return in.isKeyDown(pc.getLeft());
    }

    /**
     * Check if the user wants to attack
     *
     * @param in the Input object
     * @return true if the player wants to attack
     */
    @Override
    protected boolean attack(Input in) {
        return in.isKeyDown(pc.getAttack1());
    }

    /**
     * Check if the user wants to perform the special attack
     *
     * @param in the Input object
     * @return true if the player wants to perform the special attack
     */
    @Override
    protected boolean special(Input in) {
        return in.isKeyDown(pc.getAttack2());
    }

    /**
     * Generate the next level
     *
     * @param gc the game container object
     * @param currentGame the current game
     */
    @Override
    public void generateNextLevel(GameContainer gc, StateBasedGame currentGame) {
        try {
            currentGame.init(gc);
            currentGame.enterState(GameStates.STARTING_POINT.getState());
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

	@Override
	protected void reset(StateBasedGame gs){
		gs.enterState(GameStates.GAMEOVER.getState());
	}
}
