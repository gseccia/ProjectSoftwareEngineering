package Unit.src.elements;

import configuration.EnemyConfiguration;
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

import configuration.NoSuchElementInConfigurationException;
import blocks.Block;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EnemyTest {
	@Mock private EnemyConfiguration enemyconf;

    private Enemy enemy;

    @Before
    public void setUp()  {
        this.enemyconf = Mockito.mock(EnemyConfiguration.class);
        Block block = Mockito.mock(Block.class);
        Player p = Mockito.mock(Player.class);

        try {
            String id = "test";
            Mockito.when(this.enemyconf.getHeight(id)).thenReturn(42);
            Mockito.when(this.enemyconf.getWidth(id)).thenReturn(17);
            Mockito.when(this.enemyconf.getHp(id)).thenReturn(100);
            Mockito.when(this.enemyconf.getFaceDown(id)).thenReturn(new Animation());
            //Mockito.when(this.enemyconf.getFaceStill(id)).thenReturn(new Animation());
            Mockito.when(this.enemyconf.getFaceUp(id)).thenReturn(new Animation());
            Mockito.when(this.enemyconf.getFaceLeft(id)).thenReturn(new Animation());
            Mockito.when(this.enemyconf.getFaceRight(id)).thenReturn(new Animation());
            Mockito.when(this.enemyconf.getAttack(id)).thenReturn(100);

            //Mockito.when(block.getMap()).thenReturn(map);
            //Mockito.when(block.getShiftX()).thenReturn(0);
            //Mockito.when(block.getShiftY()).thenReturn(0);
            //Mockito.when(map.getTileHeight()).thenReturn(16);
            //Mockito.when(map.getTileWidth()).thenReturn(16);

            this.enemy = new Enemy(this.enemyconf, id, block, p);


        } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testID() {
        Assert.assertEquals("test", this.enemy.getID());
    }

    @Test
    public void testHpIsReadCorrectly() {
        Assert.assertEquals(this.enemy.getHp(), 100);
    }

    @Test
    public void testHpIsSetCorrectly() {
        int change = 10;
        this.enemy.setHp(change);
        Assert.assertEquals(this.enemy.getHp(), change);
    }

    @Test
    public void testHpValueCannotBeLessThanZero() {
        int change = -1;
        this.enemy.setHp(change);
        Assert.assertEquals(this.enemy.getHp(), 0);
    }

    @Test
    public void testHpValueCanBeZero() {
        int change = 0;
        this.enemy.setHp(change);
        Assert.assertEquals(this.enemy.getHp(), 0);
    }

    //MAX HP TESTS

    @Test
    public void testMaxHpIsReadCorrectly() {
        Assert.assertEquals(this.enemy.getMaxHp(), 100);
    }

    @Test
    public void testMaxHpIsSetCorrectly() throws NotPositiveValueException {
        int change = 10;
        this.enemy.setMaxHp(change);
        Assert.assertEquals(this.enemy.getMaxHp(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeLessThanZero() throws NotPositiveValueException {
        int change = -1;
        this.enemy.setMaxHp(change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeEqualToZero() throws NotPositiveValueException {
        int change = 0;
        this.enemy.setMaxHp(change);
    }

    //ATTACK DAMAGE TESTS

    @Test
    public void testAttackDamageIsReadCorrectly() {
        Assert.assertEquals(this.enemy.getAttackDamage(), 100);
    }

    @Test
    public void testAttackDamageIsSetCorrectly() throws NotPositiveValueException {
        int change = 10;
        this.enemy.setAttackDamage(change);
        Assert.assertEquals(this.enemy.getAttackDamage(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testAttackDamageCannotBeLessThanZero() throws NotPositiveValueException {
        int change = -1;
        this.enemy.setAttackDamage(change);
    }

    @Test
    public void testAttackDamageCanBeZero() throws NotPositiveValueException {
        int change = 0;
        this.enemy.setAttackDamage(change);
        Assert.assertEquals(this.enemy.getAttackDamage(), 0);
    }

    //DAMAGE CALCULATION TESTS

    @Test
    public void testDamageIsCalculatedCorrectly() {
        int life = this.enemy.getHp();
        int damage = 10;
        this.enemy.damage(damage);
        Assert.assertEquals(this.enemy.getHp(), life - damage);
    }

    @Test
    public void testDamageDoesNotAffectMaxHp() {
        int startingLife = this.enemy.getMaxHp();
        this.enemy.damage(10);
        Assert.assertEquals(this.enemy.getMaxHp(), startingLife);
    }

    @Test
    public void testIfDamageExceedsHpThenHpEqualsZero() {
        int damage = this.enemy.getMaxHp() + 10;
        this.enemy.damage(damage);
        Assert.assertEquals(this.enemy.getHp(), 0);
    }

    //HEIGHT TESTS

    @Test
    public void testHeightIsReadCorrectly() {
        Assert.assertEquals((int) this.enemy.getHeight(), 42);
    }

    //WIDTH TEST

    @Test
    public void testWidthIsReadCorrectly() {
        Assert.assertEquals((int) this.enemy.getWidth(), 17);
    }

    //REPOSITIONING TESTS

    @Test
    public void testEnemyIsRepositionedCorrectlyWithPositiveXAndPositiveY() {
        int x = 22;
        int y = 33;
        this.enemy.setLocation(x, y);
        assertEquals((int) this.enemy.getX(), x);
        assertEquals((int) this.enemy.getY(), y);
    }

    @Test
    public void testEnemyIsRepositionedCorrectlyWithPositiveXAndNegativeY() {
        int x = 22;
        int y = -33;
        this.enemy.setLocation(x, y);
        Assert.assertEquals((int) this.enemy.getX(), x);
        Assert.assertEquals((int) this.enemy.getY(), y);
    }

    @Test
    public void testEnemyIsRepositionedCorrectlyWithNegativeXAndNegativeY() {
        int x = -22;
        int y = -33;
        this.enemy.setLocation(x, y);
        Assert.assertEquals((int) this.enemy.getX(), x);
        Assert.assertEquals((int) this.enemy.getY(), y);
    }

    @Test
    public void testEnemyIsRepositionedCorrectlyWithNegativeXAndPositiveY() {
        int x = -22;
        int y = 33;
        this.enemy.setLocation(x, y);
        Assert.assertEquals((int) this.enemy.getX(), x);
        Assert.assertEquals((int) this.enemy.getY(), y);
    }

    @Test
    public void testEnemyIsRepositionedCorrectlyAtOrigin() {
        int x = 0;
        int y = 0;
        this.enemy.setLocation(x, y);
        Assert.assertEquals((int) this.enemy.getX(), x);
        Assert.assertEquals((int) this.enemy.getY(), y);
    }


    //X MOVEMENT TESTING

    @Test
    public void testEnemyCanMoveOnXAxisWithPositiveDelta() {
        int x = 0;
        int dx = 22;
        this.enemy.moveX(dx);
        Assert.assertEquals((int) this.enemy.getX(), x + dx);
    }

    @Test
    public void testEnemyCanMoveOnXAxisWithNegativeDelta() {
        int x = 0;
        int dx = -22;
        this.enemy.moveX(dx);
        Assert.assertEquals((int) this.enemy.getX(), x + dx);
    }

    @Test
    public void testEnemyCanMoveOnXAxisWithZeroDelta() {
        int x = 0;
        int dx = 0;
        this.enemy.moveX(dx);
        Assert.assertEquals((int) this.enemy.getX(), x + dx);
    }

    //Y-AXIS MOVEMENT TESTING

    @Test
    public void testEnemyCanMoveOnYAxisWithPositiveDelta() {
        int y = 0;
        int dy = 22;
        this.enemy.moveY(dy);
        Assert.assertEquals((int) this.enemy.getY(), y + dy);
    }

    @Test
    public void testEnemyCanMoveOnYAxisWithNegativeDelta() {
        int y = 0;
        int dy = -22;
        this.enemy.moveY(dy);
        Assert.assertEquals((int) this.enemy.getY(), y + dy);
    }

    @Test
    public void testEnemyCanMoveOnYAxisWithZeroDelta() {
        int y = 0;
        int dy = 0;
        this.enemy.moveY(dy);
        Assert.assertEquals((int) this.enemy.getY(), y + dy);
    }


}
