package Unit.src.configuration;

import configuration.NoSuchElementInConfigurationException;
import configuration.SoundConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import static org.junit.Assert.assertEquals;

public class SoundConfigurationTest {

    private SoundConfiguration s;

    @Before
    public void setUp(){
        s = SoundConfiguration.getInstance();
    }

    @Test
    public void testSoundConfigurationIsASingleton(){
        SoundConfiguration s1 = SoundConfiguration.getInstance();
        assertEquals(s1, s);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testHealSoundThrowsExceptionIfNotPresent() throws NoSuchElementInConfigurationException, SlickException {
        s.getHealSound("no");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testPowerUpSoundThrowsExceptionIfNotPresent() throws NoSuchElementInConfigurationException, SlickException {
        s.getPowerUpSound("no");
    }

}
