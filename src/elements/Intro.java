package elements;

import org.newdawn.slick.Animation;

/**
 * Boh
 */
public class Intro extends AnimatedElement {

    private boolean playing;

    public Intro(Animation a) throws NullAnimationException {
        super(a, 0, 0 ,0, 0);
    }

    public boolean playing(){
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
