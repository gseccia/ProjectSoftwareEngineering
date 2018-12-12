package blocks;

import blocks.Block;
import main.GameStates;
import managers.Directions;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ConcreteBlock extends Block {

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
        return in.isKeyDown(Directions.DOWN);
    }

    /**
     * Check if the user wants to go up
     *
     * @param in the Input object
     * @return true if the player wants to go up
     */
    @Override
    protected boolean goUp(Input in) {
        return in.isKeyDown(Directions.UP);
    }

    /**
     * Check if the user wants to go right
     *
     * @param in the Input object
     * @return true if the player wants to go right
     */
    @Override
    protected boolean goRight(Input in) {
        return in.isKeyDown(Directions.RIGHT);
    }

    /**
     * Check if the user wants to go left
     *
     * @param in the Input object
     * @return true if the player wants to go left
     */
    @Override
    protected boolean goLeft(Input in) {
        return in.isKeyDown(Directions.LEFT);
    }

    /**
     * Check if the user wants to attack
     *
     * @param in the Input object
     * @return true if the player wants to attack
     */
    @Override
    protected boolean attack(Input in) {
        return in.isKeyDown(Input.KEY_M);
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
        } catch (SlickException e) {
            e.printStackTrace();
        }
        currentGame.enterState(GameStates.PLAY.getState());
    }
}