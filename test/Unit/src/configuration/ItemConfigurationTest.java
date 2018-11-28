package Unit.src.configuration;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import static org.junit.Assert.assertEquals;

public class ItemConfigurationTest {

    private ItemConfiguration conf;

    @Before
    public void setUp(){
        this.conf = ItemConfiguration.getInstance();
    }

    @Test
    public void testItemConfigurationIsASingleton(){
        ItemConfiguration conf1 = ItemConfiguration.getInstance();
        ItemConfiguration conf2 = ItemConfiguration.getInstance();
        assertEquals(conf1, conf2);
    }

    @Test
    public void testHeightIsReadCorrectly() throws NoSuchElementInConfigurationException {
        int height = 28;
        assertEquals(this.conf.getHeight("pepsi"), height);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testHeightThrowsException() throws NoSuchElementInConfigurationException {
        this.conf.getHeight("test_error");
    }

    @Test
    public void testWidthIsReadCorrectly() throws NoSuchElementInConfigurationException {
        int width = 15;
        assertEquals(this.conf.getWidth("pepsi"), width);
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

}
