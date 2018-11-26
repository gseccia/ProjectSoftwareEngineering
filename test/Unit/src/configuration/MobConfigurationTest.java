package Unit.src.configuration;

import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import static org.junit.Assert.assertEquals;

public class MobConfigurationTest {

    @Test
    public void testMobConfigurationIsASingleton(){
        MobConfiguration conf1 = MobConfiguration.getEnemyInstance();
        MobConfiguration conf2 = MobConfiguration.getEnemyInstance();
        assertEquals(conf1, conf2);
    }

    @Test
    public void testHpAreReadCorrectly() throws NoSuchElementInConfigurationException {
        MobConfiguration conf = MobConfiguration.getEnemyInstance();
        int hp = 100;
        assertEquals(conf.getHp("test"), hp);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testHpThrowException() throws NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getHp("test_error");
    }

    @Test
    public void testHeightIsReadCorrectly() throws NoSuchElementInConfigurationException {
        MobConfiguration conf = MobConfiguration.getEnemyInstance();
        int height = 42;
        assertEquals(conf.getHeight("test"), height);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testHeightThrowsException() throws NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getHeight("test_error");
    }


    @Test
    public void testWidthIsReadCorrectly() throws NoSuchElementInConfigurationException {
        MobConfiguration conf = MobConfiguration.getEnemyInstance();
        int width = 17;
        assertEquals(conf.getWidth("test"), width);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testWidthThrowsException() throws NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getWidth("test_error");
    }


    @Test
    public void testAttackIsReadCorrectly() throws NoSuchElementInConfigurationException {
        MobConfiguration conf = MobConfiguration.getEnemyInstance();
        int attack = 100;
        assertEquals(conf.getAttack("test"), attack);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testAttackThrowsException() throws NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getAttack("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceDownThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getFaceDown("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceUpThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getFaceUp("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceLeftThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getFaceLeft("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceRightThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getFaceRight("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testFaceStillThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getFaceStill("test_error");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceUpThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getFaceUp("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceRightThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getFaceRight("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceLeftThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getFaceLeft("test_not_found");
    }

    @Test(expected = RuntimeException.class)
    public void testFaceDownThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        MobConfiguration.getEnemyInstance().getFaceDown("test_not_found");
    }


}
