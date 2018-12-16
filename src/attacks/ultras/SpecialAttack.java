package attacks.ultras;

import blocks.Block;
import elements.Intro;
import org.newdawn.slick.Image;


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

    void setDrawable(boolean flag);

    boolean isDrawable();

    void setLocation(float x, float y);

    Intro getIntro();
}
