package Unit.src.configuration;

import configuration.NoSuchElementInConfigurationException;
import configuration.SpecialAttackConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SpecialAttackConfigurationTest {

    private SpecialAttackConfiguration s;
    private Set<String> names;

    @Before
    public void setUp(){
        s = SpecialAttackConfiguration.getInstance();
        names = new HashSet<>();
        names.add("horahora");
        names.add("sparagmos");
        names.add("iuf");
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

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testGetNameThrowsExceptionIfIDIsNotValid() throws NoSuchElementInConfigurationException {
        s.getSpecialAttackName("no");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testGetDescriptionThrowsExceptionIfIDIsNotValid() throws NoSuchElementInConfigurationException {
        s.getSpecialAttackDescription("no");
    }

    @Test
    public void testAttackNamesAreReadCorrectly() {
        assertEquals(names, s.getSpecialAttackNames());
    }


}
