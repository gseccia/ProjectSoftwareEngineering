package elements;

public interface MultiAnimatable {

    /**
     * Shows the up animation of the character
     */
    void faceUp() throws NullAnimationException;

    /**
     * Shows the down animation of the character
     */
    void faceDown() throws NullAnimationException;

    /**
     * Shows the left animation of the character
     */
    void faceLeft() throws NullAnimationException;

    /**
     * Shows the right animation of the character
     */
    void faceRight() throws NullAnimationException;

    /**
     * Shows the still animation of the character
     */
    void faceStill() throws NullAnimationException;

}