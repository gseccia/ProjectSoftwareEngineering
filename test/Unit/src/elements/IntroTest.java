package Unit.src.elements;

import elements.Intro;
import elements.NullAnimationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Animation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntroTest {

    @Mock private Animation mockAnimation;

    private Intro intro;

    @Before
    public void setUp(){

        mockAnimation = Mockito.mock(Animation.class);

        try {
            intro = new Intro(mockAnimation);
        } catch (NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPlayingIsFalseByDefault() {
        assertFalse(intro.playing());
    }

    @Test
    public void testPlayingIsSetToTrueCorrectly() {
        intro.setPlaying(true);
        assertTrue(intro.playing());
    }

    @Test
    public void testPlayingIsSetToFalseCorrectly() {
        intro.setPlaying(false);
        assertFalse(intro.playing());
    }

}
