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
public class EnemyTest {
	@Mock private MobConfiguration enemyconf;

    private Enemy enemy;

    @Before
    public void setUp(){
        this.enemyconf = Mockito.mock(MobConfiguration.class);
        Block block = Mockito.mock(Block.class);
        TiledMap map = Mockito.mock(TiledMap.class);
        Player p = Mockito.mock(Player.class);
        try {
            String id = "test";
            Mockito.when(this.enemyconf.getAttack(id)).thenReturn(100);
            Mockito.when(this.enemyconf.getHeight(id)).thenReturn(42);
            Mockito.when(this.enemyconf.getWidth(id)).thenReturn(17);
            Mockito.when(this.enemyconf.getHp(id)).thenReturn(100);
            Mockito.when(this.enemyconf.getFaceDown(id)).thenReturn(new Animation());
            Mockito.when(this.enemyconf.getFaceStill(id)).thenReturn(new Animation());
            Mockito.when(this.enemyconf.getFaceUp(id)).thenReturn(new Animation());
            Mockito.when(this.enemyconf.getFaceLeft(id)).thenReturn(new Animation());
            Mockito.when(this.enemyconf.getFaceRight(id)).thenReturn(new Animation());
            this.enemy = new Enemy(this.enemyconf, id, block, p);
            Mockito.when(block.getMap()).thenReturn(map);
            Mockito.when(block.getShiftX()).thenReturn(0);
            Mockito.when(block.getShiftY()).thenReturn(0);
            Mockito.when(map.getTileHeight()).thenReturn(16);
            Mockito.when(map.getTileWidth()).thenReturn(16);
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
    
    /**********************/
    @Test
    public void testHpIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals(this.enemy.getHp(), 100);
    }

    @Test
    public void testHpIsSetCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        int change = 10;
        this.enemy.setHp(change);
        Assert.assertEquals(this.enemy.getHp(), change);
    }

    @Test
    public void testHpValueCannotBeLessThanZero() throws NullAnimationException, NoSuchElementInConfigurationException {
        int change = -1;
        this.enemy.setHp(change);
        Assert.assertEquals(this.enemy.getHp(), 0);
    }

    @Test
    public void testHpValueCanBeZero() throws NullAnimationException, NoSuchElementInConfigurationException {
        int change = 0;
        this.enemy.setHp(change);
        Assert.assertEquals(this.enemy.getHp(), 0);
    }

    //MAX HP TESTS

    @Test
    public void testMaxHpIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals(this.enemy.getMaxHp(), 100);
    }

    @Test
    public void testMaxHpIsSetCorrectly() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = 10;
        this.enemy.setMaxHp(change);
        Assert.assertEquals(this.enemy.getMaxHp(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeLessThanZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = -1;
        this.enemy.setMaxHp(change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeEqualToZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = 0;
        this.enemy.setMaxHp(change);
    }

    //ATTACK DAMAGE TESTS

    @Test
    public void testAttackDamageIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals(this.enemy.getAttackDamage(), 100);
    }

    @Test
    public void testAttackDamageIsSetCorrectly() throws NullAnimationException, NotPositiveValueException, NoSuchElementInConfigurationException {
        int change = 10;
        this.enemy.setAttackDamage(change);
        Assert.assertEquals(this.enemy.getAttackDamage(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testAttackDamageCannotBeLessThanZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = -1;
        this.enemy.setAttackDamage(change);
    }

    @Test
    public void testAttackDamageCanBeZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = 0;
        this.enemy.setAttackDamage(change);
        Assert.assertEquals(this.enemy.getAttackDamage(), 0);
    }

    //DAMAGE CALCULATION TESTS

    @Test
    public void testDamageIsCalculatedCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        int life = this.enemy.getHp();
        int damage = 10;
        this.enemy.damage(damage);
        Assert.assertEquals(this.enemy.getHp(), life - damage);
    }

    @Test
    public void testDamageDoesNotAffectMaxHp() throws NullAnimationException, NoSuchElementInConfigurationException {
        int startingLife = this.enemy.getMaxHp();
        this.enemy.damage(10);
        Assert.assertEquals(this.enemy.getMaxHp(), startingLife);
    }

    @Test
    public void testIfDamageExceedsHpThenHpEqualsZero() throws NullAnimationException, NoSuchElementInConfigurationException {
        int damage = this.enemy.getMaxHp() + 10;
        this.enemy.damage(damage);
        Assert.assertEquals(this.enemy.getHp(), 0);
    }

    //HEIGHT TESTS

    @Test
    public void testHeightIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals((int) this.enemy.getHeight(), 42);
    }

    //WIDTH TEST

    @Test
    public void testWidthIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals((int) this.enemy.getWidth(), 17);
    }

    //REPOSITIONING TESTS

    @Test
    public void testEnemyIsRepositionedCorrectlyWithPositiveXAndPositiveY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = 22;
        int y = 33;
        this.enemy.setLocation(x, y);
        assertEquals((int) this.enemy.getX(), x);
        assertEquals((int) this.enemy.getY(), y);
    }

    @Test
    public void testEnemyIsRepositionedCorrectlyWithPositiveXAndNegativeY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = 22;
        int y = -33;
        this.enemy.setLocation(x, y);
        Assert.assertEquals((int) this.enemy.getX(), x);
        Assert.assertEquals((int) this.enemy.getY(), y);
    }

    @Test
    public void testEnemyIsRepositionedCorrectlyWithNegativeXAndNegativeY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = -22;
        int y = -33;
        this.enemy.setLocation(x, y);
        Assert.assertEquals((int) this.enemy.getX(), x);
        Assert.assertEquals((int) this.enemy.getY(), y);
    }

    @Test
    public void testEnemyIsRepositionedCorrectlyWithNegativeXAndPositiveY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = -22;
        int y = 33;
        this.enemy.setLocation(x, y);
        Assert.assertEquals((int) this.enemy.getX(), x);
        Assert.assertEquals((int) this.enemy.getY(), y);
    }

    @Test
    public void testEnemyIsRepositionedCorrectlyAtOrigin() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = 0;
        int y = 0;
        this.enemy.setLocation(x, y);
        Assert.assertEquals((int) this.enemy.getX(), x);
        Assert.assertEquals((int) this.enemy.getY(), y);
    }


    //X MOVEMENT TESTING

    @Test
    public void testEnemyCanMoveOnXAxisWithPositiveDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dx = 22;
        this.enemy.moveX(dx);
        Assert.assertEquals((int) this.enemy.getX(), x + dx);
    }

    @Test
    public void testEnemyCanMoveOnXAxisWithNegativeDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dx = -22;
        this.enemy.moveX(dx);
        Assert.assertEquals((int) this.enemy.getX(), x + dx);
    }

    @Test
    public void testEnemyCanMoveOnXAxisWithZeroDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dx = 0;
        this.enemy.moveX(dx);
        Assert.assertEquals((int) this.enemy.getX(), x + dx);
    }

    //Y-AXIS MOVEMENT TESTING

    @Test
    public void testEnemyCanMoveOnYAxisWithPositiveDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dy = 22;
        this.enemy.moveY(dy);
        Assert.assertEquals((int) this.enemy.getY(), y + dy);
    }

    @Test
    public void testEnemyCanMoveOnYAxisWithNegativeDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dy = -22;
        this.enemy.moveY(dy);
        Assert.assertEquals((int) this.enemy.getY(), y + dy);
    }

    @Test
    public void testEnemyCanMoveOnYAxisWithZeroDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dy = 0;
        this.enemy.moveY(dy);
        Assert.assertEquals((int) this.enemy.getY(), y + dy);
    }


}
