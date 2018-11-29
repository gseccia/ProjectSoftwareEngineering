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
    void faceStillDown() throws NullAnimationException;
    
    /**
     * Shows the still animation of the character
     */
    void faceStillUp() throws NullAnimationException;
    
    /**
     * Shows the still animation of the character
     */
    void faceStillRight() throws NullAnimationException;
    
    /**
     * Shows the still animation of the character
     */
    void faceStillLeft() throws NullAnimationException;
    
    /**
     * Shows the still animation of the character
     */
    void attackDowm() throws NullAnimationException;
    
    /**
     * Shows the still animation of the character
     */
    void attackUp() throws NullAnimationException;
    
    /**
     * Shows the still animation of the character
     */
    void attackLeft() throws NullAnimationException;
    
    /**
     * Shows the still animation of the character
     */
    void attackRight() throws NullAnimationException;
}