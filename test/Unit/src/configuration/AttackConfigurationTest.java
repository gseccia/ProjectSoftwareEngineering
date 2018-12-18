package Unit.src.configuration;

import configuration.AttackConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

public class AttackConfigurationTest {

    private AttackConfiguration conf;

    @Before
    public void setUp(){

        try {
            Constructor<AttackConfiguration> constructor = AttackConfiguration.class.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            this.conf = constructor.newInstance(System.getProperty("user.dir") + "/resource/configurations/test.conf");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAttackConfigurationIsASingleton() {
        AttackConfiguration c1 = AttackConfiguration.getInstance();
        AttackConfiguration c2 = AttackConfiguration.getInstance();
        assertEquals(c1, c2);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceDownThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getDownAnimation("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceUpThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getUpAnimation("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceLeftThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getLeftAnimation("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceRightThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getRightAnimation("test_error");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceUpThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getUpAnimation("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceRightThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getRightAnimation("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceLeftThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getLeftAnimation("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceDownThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getDownAnimation("test_not_found");
    }

    @Test(expected = NullPointerException.class)
    public void testSoundThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getAttackSound("test_error");
    }

    @Test(expected = RuntimeException.class)
    public void testSoundThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getAttackSound("test_not_found");
    }

}
