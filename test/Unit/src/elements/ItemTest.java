package Unit.src.elements;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Item;
import elements.NullAnimationException;
import managers.Wall;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemTest {

    @Mock private ItemConfiguration conf;

    private static final String id = "test";
    private static final int height = 10;
    private static final int width = 10;

    @Before
    public void setUp() {
        conf = Mockito.mock(ItemConfiguration.class);
        try {
            Mockito.when(conf.getHeight(id)).thenReturn(height);
            Mockito.when(conf.getWidth(id)).thenReturn(width);
            Mockito.when(conf.getItemAnimation(id)).thenReturn(new Animation());
        } catch (NoSuchElementInConfigurationException e) {
            e.printStackTrace();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIdIsReturnedCorrectly() throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        Item i = new Item(conf, id);
        assertEquals(id, i.getID());
    }

    @Test
    public void intersectsTest() throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        Item i = new Item(conf, id);
        i.setLocation(10, 10);
        Rectangle r=new Rectangle(10,10,10,10);
        assertTrue(i.intersects(r));
        r=new Rectangle(21,21,10,10);
        assertFalse(i.intersects(r));
        r=new Rectangle(0,10,10,10);
        assertFalse(i.intersects(r));
        r=new Rectangle(10,0,10,10);
        assertFalse(i.intersects(r));
        r=new Rectangle(20,10,10,10);
        assertFalse(i.intersects(r));
        r=new Rectangle(10,20,10,10);
        assertFalse(i.intersects(r));
    }

}
