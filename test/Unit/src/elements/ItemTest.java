package Unit.src.elements;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Enemy;
import elements.Item;
import elements.NullAnimationException;
import elements.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ItemTest {

    @Mock private ItemConfiguration conf;
    @Mock private Player mockPlayer;
    @Mock private Enemy mockEnemy;

    private static final int POINTS = 100;
    private static final String ID = "test";
    private static final int HEIGHT = 10;
    private static final int WIDTH = 10;

    private Item item;
    private Set<Enemy> mockSet;

    @Before
    public void setUp() {
        mockPlayer = Mockito.mock(Player.class);

        mockEnemy = Mockito.mock(Enemy.class);

        mockSet = new HashSet<>();
        mockSet.add(mockEnemy);

        conf = Mockito.mock(ItemConfiguration.class);
        try {
            Mockito.when(conf.getHeight(ID)).thenReturn(HEIGHT);
            Mockito.when(conf.getWidth(ID)).thenReturn(WIDTH);
            Mockito.when(conf.getItemAnimation(ID)).thenReturn(new Animation());
            Mockito.when(conf.getItemPoints(ID)).thenReturn(POINTS);

            item = new Item(conf, ID);

        } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIdIsReturnedCorrectly() {
        assertEquals(ID, item.getID());
    }

    @Test
    public void intersectsTest() {
        item.setLocation(10, 10);
        Rectangle r=new Rectangle(10,10,10,10);
        assertTrue(item.intersects(r));
        r=new Rectangle(21,21,10,10);
        assertFalse(item.intersects(r));
        r=new Rectangle(0,10,10,10);
        assertFalse(item.intersects(r));
        r=new Rectangle(10,0,10,10);
        assertFalse(item.intersects(r));
        r=new Rectangle(20,10,10,10);
        assertFalse(item.intersects(r));
        r=new Rectangle(10,20,10,10);
        assertFalse(item.intersects(r));
    }

    @Test
    public void testItemPointsReturnedCorrectly() {
        assertEquals(POINTS, item.getItemPoints());
    }

    @Test
    public void testAcceptDoesNotThrowExceptionIfNotSet() {
        item.accept(mockPlayer);
    }

    @Test
    public void testItemIsNotATrap() {
        assertFalse(item.isTrap());
    }

    @Test
    public void testItemIsATrap() throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        Item i = new Item(conf, ID, null, true);
        assertTrue(i.isTrap());
    }

    @Test
    public void testGetSpawnsReturnsAnEmptySetIfNotSet() {
        assertEquals(0, item.getSpawns().size());
    }

    @Test
    public void testGetSpawnsReturnCorrectly() {
        item.setSpawns(mockSet);
        assertEquals(mockSet, item.getSpawns());
    }

}
