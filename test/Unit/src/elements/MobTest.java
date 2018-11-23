package Unit.src.elements;


import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Mob;
import elements.NotPositiveValueException;
import elements.NullAnimationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import java.lang.reflect.InvocationTargetException;

public class MobTest {
    @Mock private MobConfiguration mobconf;

    private String id = "test";
    private Mob mob;
    //HP TEST
    @Before
    public void setUp(){
        this.mobconf = Mockito.mock(MobConfiguration.class);
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
            this.mob = new Mob(this.mobconf,this.id);
        } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testHpIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals(this.mob.getHp(), 100);
    }

    @Test
    public void testHpIsSetCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        int change = 10;
        this.mob.setHp(change);
        Assert.assertEquals(this.mob.getHp(), change);
    }

    @Test
    public void testHpValueCannotBeLessThanZero() throws NullAnimationException, NoSuchElementInConfigurationException {
        int change = -1;
        this.mob.setHp(change);
        Assert.assertEquals(this.mob.getHp(), 0);
    }

    @Test
    public void testHpValueCanBeZero() throws NullAnimationException, NoSuchElementInConfigurationException {
        int change = 0;
        this.mob.setHp(change);
        Assert.assertEquals(this.mob.getHp(), 0);
    }

    //MAX HP TESTS

    @Test
    public void testMaxHpIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals(this.mob.getMaxHp(), 100);
    }

    @Test
    public void testMaxHpIsSetCorrectly() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = 10;
        this.mob.setMaxHp(change);
        Assert.assertEquals(this.mob.getMaxHp(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeLessThanZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = -1;
        this.mob.setMaxHp(change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeEqualToZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = 0;
        this.mob.setMaxHp(change);
    }

    //ATTACK DAMAGE TESTS

    @Test
    public void testAttackDamageIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals(this.mob.getAttackDamage(), 100);
    }

    @Test
    public void testAttackDamageIsSetCorrectly() throws NullAnimationException, NotPositiveValueException, NoSuchElementInConfigurationException {
        int change = 10;
        this.mob.setAttackDamage(change);
        Assert.assertEquals(this.mob.getAttackDamage(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testAttackDamageCannotBeLessThanZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = -1;
        this.mob.setAttackDamage(change);
    }

    @Test
    public void testAttackDamageCanBeZero() throws NotPositiveValueException, NullAnimationException, NoSuchElementInConfigurationException {
        int change = 0;
        this.mob.setAttackDamage(change);
        Assert.assertEquals(this.mob.getAttackDamage(), 0);
    }

    //DAMAGE CALCULATION TESTS

    @Test
    public void testDamageIsCalculatedCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        int life = this.mob.getHp();
        int damage = 10;
        this.mob.damage(damage);
        Assert.assertEquals(this.mob.getHp(), life - damage);
    }

    @Test
    public void testDamageDoesNotAffectMaxHp() throws NullAnimationException, NoSuchElementInConfigurationException {
        int startingLife = this.mob.getMaxHp();
        this.mob.damage(10);
        Assert.assertEquals(this.mob.getMaxHp(), startingLife);
    }

    @Test
    public void testIfDamageExceedsHpThenHpEqualsZero() throws NullAnimationException, NoSuchElementInConfigurationException {
        int damage = this.mob.getMaxHp() + 10;
        this.mob.damage(damage);
        Assert.assertEquals(this.mob.getHp(), 0);
    }

    //POSITIONING TESTS

    @Test
    public void testMobIsPositionedAtOrigin() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals((int) this.mob.getX(), 0);
        Assert.assertEquals((int) this.mob.getY(), 0);
    }

    @Test
    public void testMobIsPositionedCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 10;
        int y = 20;
        this.mob = new Mob(this.mobconf,this.id, x, y);
        Assert.assertEquals((int) this.mob.getX(), x);
        Assert.assertEquals((int) this.mob.getY(), y);
    }

    @Test
    public void testMobCanBePositionedAtNegativeCoordinates() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = -10;
        int y = -20;

        this.mob = new Mob(this.mobconf,this.id, x, y);
        Assert.assertEquals((int) this.mob.getX(), x);
        Assert.assertEquals((int) this.mob.getY(), y);
    }

    //HEIGHT TESTS

    @Test
    public void testHeightIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals((int) this.mob.getHeight(), 42);
    }

    //WIDTH TEST

    @Test
    public void testWidthIsReadCorrectly() throws NullAnimationException, NoSuchElementInConfigurationException {
        Assert.assertEquals((int) this.mob.getWidth(), 17);
    }

    //REPOSITIONING TESTS

    @Test
    public void testMobIsRepositionedCorrectlyWithPositiveXAndPositiveY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = 22;
        int y = 33;
        this.mob.setLocation(x, y);
        assertEquals((int) this.mob.getX(), x);
        assertEquals((int) this.mob.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyWithPositiveXAndNegativeY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = 22;
        int y = -33;
        this.mob.setLocation(x, y);
        Assert.assertEquals((int) this.mob.getX(), x);
        Assert.assertEquals((int) this.mob.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyWithNegativeXAndNegativeY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = -22;
        int y = -33;
        this.mob.setLocation(x, y);
        Assert.assertEquals((int) this.mob.getX(), x);
        Assert.assertEquals((int) this.mob.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyWithNegativeXAndPositiveY() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = -22;
        int y = 33;
        this.mob.setLocation(x, y);
        Assert.assertEquals((int) this.mob.getX(), x);
        Assert.assertEquals((int) this.mob.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyAtOrigin() throws NullAnimationException, NoSuchElementInConfigurationException {
        int x = 0;
        int y = 0;
        this.mob.setLocation(x, y);
        Assert.assertEquals((int) this.mob.getX(), x);
        Assert.assertEquals((int) this.mob.getY(), y);
    }


    //X MOVEMENT TESTING

    @Test
    public void testMobCanMoveOnXAxisWithPositiveDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dx = 22;
        this.mob = new Mob(this.mobconf,this.id, x, y);
        this.mob.moveX(dx);
        Assert.assertEquals((int) this.mob.getX(), x + dx);
    }

    @Test
    public void testMobCanMoveOnXAxisWithNegativeDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dx = -22;
        this.mob = new Mob(this.mobconf,this.id, x, y);
        this.mob.moveX(dx);
        Assert.assertEquals((int) this.mob.getX(), x + dx);
    }

    @Test
    public void testMobCanMoveOnXAxisWithZeroDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dx = 0;
        this.mob = new Mob(this.mobconf,this.id, x, y);
        this.mob.moveX(dx);
        Assert.assertEquals((int) this.mob.getX(), x + dx);
    }

    //Y-AXIS MOVEMENT TESTING

    @Test
    public void testMobCanMoveOnYAxisWithPositiveDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dy = 22;
        this.mob = new Mob(this.mobconf,this.id, x, y);
        this.mob.moveY(dy);
        Assert.assertEquals((int) this.mob.getY(), y + dy);
    }

    @Test
    public void testMobCanMoveOnYAxisWithNegativeDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dy = -22;
        this.mob = new Mob(this.mobconf,this.id, x, y);
        this.mob.moveY(dy);
        Assert.assertEquals((int) this.mob.getY(), y + dy);
    }

    @Test
    public void testMobCanMoveOnYAxisWithZeroDelta() throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        int x = 0;
        int y = 0;
        int dy = 0;
        this.mob = new Mob(this.mobconf,this.id, x, y);
        this.mob.moveY(dy);
        Assert.assertEquals((int) this.mob.getY(), y + dy);
    }

}
