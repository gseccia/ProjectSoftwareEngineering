package Unit.src.configuration;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ItemConfigurationTest {

    private ItemConfiguration conf;
    private Set<String> npcNames, misionNames, names;

    @Before
    public void setUp(){
        try {
            Constructor<ItemConfiguration> constructor = ItemConfiguration.class.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            this.conf = constructor.newInstance(System.getProperty("user.dir") + "/resource/configurations/test.conf");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        npcNames = new HashSet<>();
        npcNames.add("test_npc");

        misionNames = new HashSet<>();
        misionNames.add("test_mission");

        names = new HashSet<>();
        names.add("test");
        names.add("test_error");
        names.add("test_not_found");
        names.add("test_npc");
        names.add("test_mission");
    }

    @Test
    public void testItemConfigurationIsASingleton(){
        ItemConfiguration conf1 = ItemConfiguration.getInstance();
        ItemConfiguration conf2 = ItemConfiguration.getInstance();
        assertEquals(conf1, conf2);
    }

    @Test
    public void testHeightIsReadCorrectly() throws NoSuchElementInConfigurationException {
        int height = 42;
        assertEquals(height, this.conf.getHeight("test"));
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testHeightThrowsException() throws NoSuchElementInConfigurationException {
        this.conf.getHeight("test_error");
    }

    @Test
    public void testPointsAreReadCorrectly() throws NoSuchElementInConfigurationException {
        int points = 50;
        assertEquals(points, this.conf.getItemPoints("test"));
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testPointsThrowsException() throws NoSuchElementInConfigurationException {
        this.conf.getHeight("test_error");
    }

    @Test
    public void testWidthIsReadCorrectly() throws NoSuchElementInConfigurationException {
        int width = 17;
        assertEquals(width, this.conf.getWidth("test"));
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testWidthThrowsException() throws NoSuchElementInConfigurationException {
        this.conf.getWidth("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testAnimationThrowsExceptionIfNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getItemAnimation("test_error");
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testAnimationThrowsExceptionIfImageNotPresent() throws SlickException, NoSuchElementInConfigurationException {
        this.conf.getItemAnimation("test_error");
    }

    @Test
    public void testNPCNamesAreReadCorrectly() {
        assertEquals(npcNames, conf.getNPCNames());
    }

    @Test
    public void testMissionNamesAreReadCorrectly() {
        assertEquals(misionNames, conf.getMissionItemNames());
    }

    @Test
    public void testItemNamesAreReadCorrectly() {
        assertEquals(names, conf.getItemNames());
    }

}
