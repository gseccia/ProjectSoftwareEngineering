package Unit.src.elements;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Enemy;
import managers.Directions;
import elements.NullAnimationException;
import elements.Player;
import main.java.Block;

@RunWith(MockitoJUnitRunner.class)
class EnemyTest {
	@Mock private MobConfiguration mobconf;

    private String id = "test";
    private Enemy enemy;
    private Block block;
    private Player p;
    
    @Before
    public void setUp(){
        this.mobconf = Mockito.mock(MobConfiguration.class);
        this.block = Mockito.mock(Block.class);
        this.p = Mockito.mock(Player.class);
        try {
            Mockito.when(this.mobconf.getAttack(this.id)).thenReturn(100);
            Mockito.when(this.mobconf.getHeight(this.id)).thenReturn(42);
            Mockito.when(this.mobconf.getWidth(this.id)).thenReturn(17);
            Mockito.when(this.mobconf.getHp(this.id)).thenReturn(100);
            Mockito.when(this.mobconf.getFaceDown(this.id)).thenReturn(new Animation());
            Mockito.when(this.mobconf.getFaceStill(this.id)).thenReturn(new Animation());
            Mockito.when(this.mobconf.getFaceUp(this.id)).thenReturn(new Animation());
            Mockito.when(this.mobconf.getFaceLeft(this.id)).thenReturn(new Animation());
            Mockito.when(this.mobconf.getFaceRight(this.id)).thenReturn(new Animation());
            this.enemy = new Enemy(this.mobconf,this.id, this.block, this.p);
        } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testID() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals((String)this.enemy.getID(), "test");
    }

  //POSITIONING TESTS

    @Test
    public void testEnemyIsPositionedAtOrigin() throws NullAnimationException, NoSuchElementInConfigurationException {
    	this.enemy.init(42, 42);
        Assert.assertEquals((int) this.enemy.getX(), 42);
        Assert.assertEquals((int) this.enemy.getY(), 42);
    }

    @Test
    public void testEnemyInitialDirection() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        this.enemy.init(0, 0);
        Assert.assertEquals((int) this.enemy.getDirection(), Directions.LEFT);
    }


}
