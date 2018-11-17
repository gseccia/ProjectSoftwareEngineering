package Unit.configuration;

import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import static org.junit.Assert.assertEquals;

public class MobConfigurationTest {

    @Test
    public void testMobConfigurationIsASingleton(){
        MobConfiguration conf1 = MobConfiguration.getInstance();
        MobConfiguration conf2 = MobConfiguration.getInstance();
        assertEquals(conf1, conf2);
    }

    @Test
    public void testHpAreReadCorrectly() throws NoSuchElementInConfigurationException {
        MobConfiguration conf = MobConfiguration.getInstance();
        int hp = 100;
        assertEquals(conf.getHp("test"), hp);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testHpThrowException() throws NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getHp("test_error");
    }

    @Test
    public void testHeightIsReadCorrectly() throws NoSuchElementInConfigurationException {
        MobConfiguration conf = MobConfiguration.getInstance();
        int height = 42;
        assertEquals(conf.getHeight("test"), height);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testHeightThrowsException() throws NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getHeight("test_error");
    }


    @Test
    public void testWidthIsReadCorrectly() throws NoSuchElementInConfigurationException {
        MobConfiguration conf = MobConfiguration.getInstance();
        int width = 17;
        assertEquals(conf.getWidth("test"), width);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testWidthThrowsException() throws NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getWidth("test_error");
    }


    @Test
    public void testAttackIsReadCorrectly() throws NoSuchElementInConfigurationException {
        MobConfiguration conf = MobConfiguration.getInstance();
        int attack = 100;
        assertEquals(conf.getAttack("test"), attack);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testAttackThrowsException() throws NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getAttack("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceDownThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getFaceDown("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceUpThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getFaceUp("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceLeftThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getFaceLeft("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceRightThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getFaceRight("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceStillThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getFaceStill("test_error");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceUpThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getFaceUp("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceRightThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getFaceRight("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceLeftThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getFaceLeft("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceDownThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getInstance().getFaceDown("test_not_found");
    }


}
