package Unit.src.configuration;

import configuration.NoSuchElementInConfigurationException;
import configuration.SpecialAttackConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import static org.junit.Assert.assertEquals;

public class SpecialAttackConfigurationTest {

    private SpecialAttackConfiguration s;

    @Before
    public void setUp(){
        s = SpecialAttackConfiguration.getInstance();
    }

    @Test
    public void testSpecialAttackConfigurationIsASingleton(){
        SpecialAttackConfiguration s1 = SpecialAttackConfiguration.getInstance();
        assertEquals(s1, s);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testGetIntroThrowsExceptionIfIDIsNotValid() throws SlickException, NoSuchElementInConfigurationException {
        s.getIntro("no");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testGetIconThrowsExceptionIfIDIsNotValid() throws SlickException, NoSuchElementInConfigurationException {
        s.getIcon("no");
    }


}
