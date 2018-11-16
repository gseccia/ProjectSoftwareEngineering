package test.Unit.elements;

import elements.Mob;
import elements.NotPositiveValueException;
import elements.NullAnimationException;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import static org.junit.Assert.assertEquals;

public class MobTest {

    //HP TEST

    @Test
    public void testHpIsReadCorrectly() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        assertEquals(dummy.getHp(), 100);
    }

    @Test
    public void testHpIsSetCorrectly() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        int change = 10;
        dummy.setHp(change);
        assertEquals(dummy.getHp(), change);
    }

    @Test
    public void testHpValueCannotBeLessThanZero() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        int change = -1;
        dummy.setHp(change);
        assertEquals(dummy.getHp(), 0);
    }

    @Test
    public void testHpValueCanBeZero() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        int change = 0;
        dummy.setHp(change);
        assertEquals(dummy.getHp(), 0);
    }

    //MAX HP TESTS

    @Test
    public void testMaxHpIsReadCorrectly() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        assertEquals(dummy.getMaxHp(), 100);
    }

    @Test
    public void testMaxHpIsSetCorrectly() throws NotPositiveValueException, SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        int change = 10;
        dummy.setMaxHp(change);
        assertEquals(dummy.getMaxHp(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeLessThanZero() throws NotPositiveValueException, SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        int change = -1;
        dummy.setMaxHp(change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeEqualToZero() throws NotPositiveValueException, SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        int change = 0;
        dummy.setMaxHp(change);
    }

    //ATTACK DAMAGE TESTS

    @Test
    public void testAttackDamageIsReadCorrectly() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        assertEquals(dummy.getAttackDamage(), 100);
    }

    @Test
    public void testAttackDamageIsSetCorrectly() throws SlickException, NullAnimationException, NotPositiveValueException {
        Mob dummy = Mob.generate("test");
        int change = 10;
        dummy.setAttackDamage(change);
        assertEquals(dummy.getAttackDamage(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testAttackDamageCannotBeLessThanZero() throws NotPositiveValueException, SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        int change = -1;
        dummy.setAttackDamage(change);
    }

    @Test
    public void testAttackDamageCanBeZero() throws NotPositiveValueException, SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        int change = 0;
        dummy.setAttackDamage(change);
        assertEquals(dummy.getAttackDamage(), 0);
    }

    //DAMAGE CALCULATION TESTS

    @Test
    public void testDamageIsCalculatedCorrectly() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        int life = dummy.getHp();
        int damage = 10;
        dummy.damage(damage);
        assertEquals(dummy.getHp(), life - damage);
    }

    @Test
    public void testDamageDoesNotAffectMaxHp() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        int startingLife = dummy.getMaxHp();
        dummy.damage(10);
        assertEquals(dummy.getMaxHp(), startingLife);
    }

    @Test
    public void testIfDamageExceedsHpThenHpEqualsZero() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        int damage = dummy.getMaxHp() + 10;
        dummy.damage(damage);
        assertEquals(dummy.getHp(), 0);
    }

    //POSITIONING TESTS

    @Test
    public void testMobIsPositionedAtOrigin() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        assertEquals(dummy.getX(), 0.0);
        assertEquals(dummy.getY(), 0.0);
    }

    @Test
    public void testMobIsPositionedCorrectly() throws SlickException, NullAnimationException {
        int x = 10;
        int y = 20;
        Mob dummy = Mob.generate("test", x, y);
        assertEquals(dummy.getX(), x);
        assertEquals(dummy.getY(), y);
    }

    @Test
    public void testMobCanBePositionedAtNegativeCoordinates() throws SlickException, NullAnimationException {
        int x = -10;
        int y = -20;
        Mob dummy = Mob.generate("test", x, y);
        assertEquals(dummy.getX(), x);
        assertEquals(dummy.getY(), y);
    }

    //HEIGHT TESTS

    @Test
    public void testHeightIsReadCorrectly() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        assertEquals(dummy.getHeight(), 42);
    }

    //WIDTH TEST

    @Test
    public void testWidthIsReadCorrectly() throws SlickException, NullAnimationException {
        Mob dummy = Mob.generate("test");
        assertEquals(dummy.getWidth(), 17);
    }

    //REPOSITIONING TESTS

    @Test
    public void testMobIsRepositionedCorrectlyWithPostiveXAndPositiveY() throws SlickException, NullAnimationException {
        int x = 22;
        int y = 33;
        Mob dummy = Mob.generate("test");
        dummy.setPosition(x, y);
        assertEquals(dummy.getX(), x);
        assertEquals(dummy.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyWithPostiveXAndNegativeY() throws SlickException, NullAnimationException {
        int x = 22;
        int y = -33;
        Mob dummy = Mob.generate("test");
        dummy.setPosition(x, y);
        assertEquals(dummy.getX(), x);
        assertEquals(dummy.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyWithNegativeXAndNegativeY() throws SlickException, NullAnimationException {
        int x = -22;
        int y = -33;
        Mob dummy = Mob.generate("test");
        dummy.setPosition(x, y);
        assertEquals(dummy.getX(), x);
        assertEquals(dummy.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyWithNegativeXAndPositiveY() throws SlickException, NullAnimationException {
        int x = -22;
        int y = 33;
        Mob dummy = Mob.generate("test");
        dummy.setPosition(x, y);
        assertEquals(dummy.getX(), x);
        assertEquals(dummy.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyAtOrigin() throws SlickException, NullAnimationException {
        int x = 0;
        int y = 0;
        Mob dummy = Mob.generate("test");
        dummy.setPosition(x, y);
        assertEquals(dummy.getX(), x);
        assertEquals(dummy.getY(), y);
    }


    //X MOVEMENT TESTING

    @Test
    public void testMobCanMoveOnXAxisWithPositiveDelta() throws SlickException, NullAnimationException {
        int x = 0;
        int y = 0;
        int dx = 22;
        Mob dummy = Mob.generate("test", x, y);
        dummy.moveX(dx);
        assertEquals(dummy.getX(), x + dx);
    }

    @Test
    public void testMobCanMoveOnXAxisWithNegativeDelta() throws SlickException, NullAnimationException {
        int x = 0;
        int y = 0;
        int dx = -22;
        Mob dummy = Mob.generate("test", x, y);
        dummy.moveX(dx);
        assertEquals(dummy.getX(), x + dx);
    }

    @Test
    public void testMobCanMoveOnXAxisWithZeroDelta() throws SlickException, NullAnimationException {
        int x = 0;
        int y = 0;
        int dx = 0;
        Mob dummy = Mob.generate("test", x, y);
        dummy.moveX(dx);
        assertEquals(dummy.getX(), x + dx);
    }

    //Y-AXIS MOVEMENT TESTING

    @Test
    public void testMobCanMoveOnYAxisWithPositiveDelta() throws SlickException, NullAnimationException {
        int x = 0;
        int y = 0;
        int dy = 22;
        Mob dummy = Mob.generate("test", x, y);
        dummy.moveY(dy);
        assertEquals(dummy.getY(), x + dy);
    }

    @Test
    public void testMobCanMoveOnYAxisWithNegativeDelta() throws SlickException, NullAnimationException {
        int x = 0;
        int y = 0;
        int dy = -22;
        Mob dummy = Mob.generate("test", x, y);
        dummy.moveY(dy);
        assertEquals(dummy.getY(), x + dy);
    }

    @Test
    public void testMobCanMoveOnYAxisWithZeroDelta() throws SlickException, NullAnimationException {
        int x = 0;
        int y = 0;
        int dy = 0;
        Mob dummy = Mob.generate("test", x, y);
        dummy.moveY(dy);
        assertEquals(dummy.getY(), x + dy);
    }
    
}
