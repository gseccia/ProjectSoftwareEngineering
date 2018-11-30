package Unit.src.elements;

import elements.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import managers.Directions;
import main.Block;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {
    @Mock
    private MobConfiguration playerconf;

    private Player player;

    @Before
    public void setUp() {
        this.playerconf = Mockito.mock(MobConfiguration.class);
        Block block = Mockito.mock(Block.class);
        TiledMap map = Mockito.mock(TiledMap.class);
        try {
            String id = "test";
            Mockito.when(this.playerconf.getAttack(id)).thenReturn(100);
            Mockito.when(this.playerconf.getHeight(id)).thenReturn(42);
            Mockito.when(this.playerconf.getWidth(id)).thenReturn(17);
            Mockito.when(this.playerconf.getHp(id)).thenReturn(100);
            Mockito.when(this.playerconf.getFaceDown(id)).thenReturn(new Animation());
            //Mockito.when(this.playerconf.getFaceStill(id)).thenReturn(new Animation());
            Mockito.when(this.playerconf.getFaceUp(id)).thenReturn(new Animation());
            Mockito.when(this.playerconf.getFaceLeft(id)).thenReturn(new Animation());
            Mockito.when(this.playerconf.getFaceRight(id)).thenReturn(new Animation());
            //Mockito.when(block.getMap()).thenReturn(map);
            //Mockito.when(block.getShiftX()).thenReturn(0);
            //Mockito.when(block.getShiftY()).thenReturn(0);
            //Mockito.when(map.getTileHeight()).thenReturn(16);
            //Mockito.when(map.getTileWidth()).thenReturn(16);


            this.player = new Player(this.playerconf,id);

        } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }


    //POSITIONING TESTS

    /**********************/
    @Test
    public void testHpIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals(this.player.getHp(), 100);
    }

    @Test
    public void testHpIsSetCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        int change = 10;
        this.player.setHp(change);
        Assert.assertEquals(this.player.getHp(), change);
    }

    @Test
    public void testHpValueCannotBeLessThanZero() throws NullAnimationException, NoSuchElementInConfigurationException {
        int change = -1;
        this.player.setHp(change);
        Assert.assertEquals(this.player.getHp(), 0);
    }

    @Test
    public void testHpValueCanBeZero() throws NullAnimationException, NoSuchElementInConfigurationException {
        int change = 0;
        this.player.setHp(change);
        Assert.assertEquals(this.player.getHp(), 0);
    }

    //MAX HP TESTS

    @Test
    public void testMaxHpIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals(this.player.getMaxHp(), 100);
    }

    @Test
    public void testMaxHpIsSetCorrectly() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = 10;
        this.player.setMaxHp(change);
        Assert.assertEquals(this.player.getMaxHp(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeLessThanZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = -1;
        this.player.setMaxHp(change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeEqualToZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = 0;
        this.player.setMaxHp(change);
    }

    //ATTACK DAMAGE TESTS

    @Test
    public void testAttackDamageIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals(this.player.getAttackDamage(), 100);
    }

    @Test
    public void testAttackDamageIsSetCorrectly() throws NullAnimationException, NotPositiveValueException, NoSuchElementInConfigurationException {
        int change = 10;
        this.player.setAttackDamage(change);
        Assert.assertEquals(this.player.getAttackDamage(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testAttackDamageCannotBeLessThanZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = -1;
        this.player.setAttackDamage(change);
    }

    @Test
    public void testAttackDamageCanBeZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = 0;
        this.player.setAttackDamage(change);
        Assert.assertEquals(this.player.getAttackDamage(), 0);
    }

    //DAMAGE CALCULATION TESTS

    @Test
    public void testDamageIsCalculatedCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        int life = this.player.getHp();
        int damage = 10;
        this.player.damage(damage);
        Assert.assertEquals(this.player.getHp(), life - damage);
    }

    @Test
    public void testDamageDoesNotAffectMaxHp() throws NullAnimationException, NoSuchElementInConfigurationException {
        int startingLife = this.player.getMaxHp();
        this.player.damage(10);
        Assert.assertEquals(this.player.getMaxHp(), startingLife);
    }

    @Test
    public void testIfDamageExceedsHpThenHpEqualsZero() throws NullAnimationException, NoSuchElementInConfigurationException {
        int damage = this.player.getMaxHp() + 10;
        this.player.damage(damage);
        Assert.assertEquals(this.player.getHp(), 0);
    }

    //HEIGHT TESTS

    @Test
    public void testHeightIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals((int) this.player.getHeight(), 42);
    }

    //WIDTH TEST

    @Test
    public void testWidthIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals((int) this.player.getWidth(), 17);
    }

    //REPOSITIONING TESTS

    @Test
    public void testPlayerIsRepositionedCorrectlyWithPositiveXAndPositiveY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = 22;
        int y = 33;
        this.player.setLocation(x, y);
        assertEquals((int) this.player.getX(), x);
        assertEquals((int) this.player.getY(), y);
    }

    @Test
    public void testPlayerIsRepositionedCorrectlyWithPositiveXAndNegativeY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = 22;
        int y = -33;
        this.player.setLocation(x, y);
        Assert.assertEquals((int) this.player.getX(), x);
        Assert.assertEquals((int) this.player.getY(), y);
    }

    @Test
    public void testPlayerIsRepositionedCorrectlyWithNegativeXAndNegativeY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = -22;
        int y = -33;
        this.player.setLocation(x, y);
        Assert.assertEquals((int) this.player.getX(), x);
        Assert.assertEquals((int) this.player.getY(), y);
    }

    @Test
    public void testPlayerIsRepositionedCorrectlyWithNegativeXAndPositiveY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = -22;
        int y = 33;
        this.player.setLocation(x, y);
        Assert.assertEquals((int) this.player.getX(), x);
        Assert.assertEquals((int) this.player.getY(), y);
    }

    @Test
    public void testPlayerIsRepositionedCorrectlyAtOrigin() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = 0;
        int y = 0;
        this.player.setLocation(x, y);
        Assert.assertEquals((int) this.player.getX(), x);
        Assert.assertEquals((int) this.player.getY(), y);
    }


    //X MOVEMENT TESTING

    @Test
    public void testPlayerCanMoveOnXAxisWithPositiveDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dx = 22;
        this.player.moveX(dx);
        Assert.assertEquals((int) this.player.getX(), x + dx);
    }

    @Test
    public void testPlayerCanMoveOnXAxisWithNegativeDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dx = -22;
        this.player.moveX(dx);
        Assert.assertEquals((int) this.player.getX(), x + dx);
    }

    @Test
    public void testPlayerCanMoveOnXAxisWithZeroDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dx = 0;
        this.player.moveX(dx);
        Assert.assertEquals((int) this.player.getX(), x + dx);
    }

    //Y-AXIS MOVEMENT TESTING

    @Test
    public void testPlayerCanMoveOnYAxisWithPositiveDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dy = 22;
        this.player.moveY(dy);
        Assert.assertEquals((int) this.player.getY(), y + dy);
    }

    @Test
    public void testPlayerCanMoveOnYAxisWithNegativeDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dy = -22;
        this.player.moveY(dy);
        Assert.assertEquals((int) this.player.getY(), y + dy);
    }

    @Test
    public void testPlayerCanMoveOnYAxisWithZeroDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dy = 0;
        this.player.moveY(dy);
        Assert.assertEquals((int) this.player.getY(), y + dy);
    }
}