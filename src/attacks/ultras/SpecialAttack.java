package attacks.ultras;

import blocks.Block;
import org.newdawn.slick.Image;

/**
 * An interface that defines all the basic operations of a special attack
 */
public interface SpecialAttack {

    /**
     * Recharges the attack
     */
    void reload();

    /**
     * @return true if the SpecialAttack is ready to be used
     */
    boolean isReady();

    /**
     * @return true if the SpecialAttack blocks the normal course of the game
     */
    boolean isBlocking();

    /**
     * @return true if the SpecialAttack is currently active
     */
    boolean isActive();

    /**
     * @return the icon of the SpecialAttack
     */
    Image getIcon();

    /**
     * Activates the SpecialAttack
     * @param b the current Block
     */
    void activate(Block b);

    /**
     * Executes an iteration step of the move
     */
    void iterationStep();

    /**
     * Draws the special attack
     */
    void draw();

    /**
     * Defines if the special attack has to be drawable or not
     * @param flag true if drawable
     */
    void setDrawable(boolean flag);

    /**
     * @return true if the attack is drawable
     */
    boolean isDrawable();

    /**
     * Sets the location of the special attack animation
     * @param x the x coordinate
     * @param y the y coordinate
     */
    void setLocation(float x, float y);
}
