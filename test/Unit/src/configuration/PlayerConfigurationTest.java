package Unit.src.configuration;

import configuration.NoSuchElementInConfigurationException;
import configuration.PlayerConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

public class PlayerConfigurationTest {

    private PlayerConfiguration conf;

    @Before
    public void setUp(){

        try {
            Constructor<PlayerConfiguration> constructor = PlayerConfiguration.class.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            this.conf = constructor.newInstance(System.getProperty("user.dir") + "/resource/configurations/test.conf");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tesEnemyConfigurationIsASingleton(){
        PlayerConfiguration conf1 = PlayerConfiguration.getInstance();
        PlayerConfiguration conf2 = PlayerConfiguration.getInstance();
        assertEquals(conf1, conf2);
    }

    @Test
    public void testHpAreReadCorrectly() throws NoSuchElementInConfigurationException {
        int hp = 100;
        assertEquals(this.conf.getHp("test"), hp);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testHpThrowException() throws NoSuchElementInConfigurationException {
        this.conf.getHp("test_error");
    }

    @Test
    public void testHeightIsReadCorrectly() throws NoSuchElementInConfigurationException {
        int height = 42;
        assertEquals(this.conf.getHeight("test"), height);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testHeightThrowsException() throws NoSuchElementInConfigurationException {
        this.conf.getHeight("test_error");
    }


    @Test
    public void testWidthIsReadCorrectly() throws NoSuchElementInConfigurationException {
        int width = 17;
        assertEquals(this.conf.getWidth("test"), width);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testWidthThrowsException() throws NoSuchElementInConfigurationException {
        this.conf.getWidth("test_error");
    }


    @Test
    public void testAttackIsReadCorrectly() throws NoSuchElementInConfigurationException {
        int attack = 100;
        assertEquals(this.conf.getAttack("test"), attack);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testAttackThrowsException() throws NoSuchElementInConfigurationException {
        this.conf.getAttack("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceDownThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceDown("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceUpThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceUp("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceLeftThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceLeft("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceRightThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceRight("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceStillThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceStill("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceStillUpThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceStillUp("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceStillDownThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceStillDown("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceStillLeftThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceStillLeft("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceStillRightThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceStillRight("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceAttackUpThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getAttackUp("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testAttackDownThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getAttackDown("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testAttackLeftThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getAttackLeft("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testAttackRightThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getAttackRight("test_error");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceUpThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceUp("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceRightThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceRight("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceLeftThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceLeft("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceDownThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceDown("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceStillUpThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceStillUp("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceStillDownThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceStillDown("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceStillLeftThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceStillLeft("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceStillRightThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getFaceStillRight("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceAttackUpThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getAttackUp("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testAttackDownThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getAttackDown("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testAttackLeftThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getAttackLeft("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testAttackRightThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getAttackRight("test_not_found");
    }
}
