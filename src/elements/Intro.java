package elements;

import org.newdawn.slick.Animation;

/**
 * Intro object for the special attacks.
 */
public class Intro extends AnimatedElement {

    private boolean playing;

    /**
     * Constructor
     * @param a the animation to display
     * @throws NullAnimationException if the animation is null
     */
    public Intro(Animation a) throws NullAnimationException {
        super(a, 0, 0 ,0, 0);
    }

    /**
     * @return true if the intro must be visible
     */
    public boolean playing(){
        return playing;
    }

    /**
     * Sets the playing status
     * @param playing the playing status
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
