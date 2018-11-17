package Unit.elements;

import elements.Mob;
import elements.NotPositiveValueException;
import elements.NullAnimationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MobTest {

    //HP TEST

    @Test
    public void testHpIsReadCorrectly() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        assertEquals(dummy.getHp(), 100);
    }

    @Test
    public void testHpIsSetCorrectly() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        int change = 10;
        dummy.setHp(change);
        assertEquals(dummy.getHp(), change);
    }

    @Test
    public void testHpValueCannotBeLessThanZero() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        int change = -1;
        dummy.setHp(change);
        assertEquals(dummy.getHp(), 0);
    }

    @Test
    public void testHpValueCanBeZero() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        int change = 0;
        dummy.setHp(change);
        assertEquals(dummy.getHp(), 0);
    }

    //MAX HP TESTS

    @Test
    public void testMaxHpIsReadCorrectly() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        assertEquals(dummy.getMaxHp(), 100);
    }

    @Test
    public void testMaxHpIsSetCorrectly() throws NotPositiveValueException, NullAnimationException {
        Mob dummy = Mob.mockGenerate("test");
        int change = 10;
        dummy.setMaxHp(change);
        assertEquals(dummy.getMaxHp(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeLessThanZero() throws NotPositiveValueException, NullAnimationException {
        Mob dummy = Mob.mockGenerate("test");
        int change = -1;
        dummy.setMaxHp(change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeEqualToZero() throws NotPositiveValueException, NullAnimationException {
        Mob dummy = Mob.mockGenerate("test");
        int change = 0;
        dummy.setMaxHp(change);
    }

    //ATTACK DAMAGE TESTS

    @Test
    public void testAttackDamageIsReadCorrectly() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        assertEquals(dummy.getAttackDamage(), 100);
    }

    @Test
    public void testAttackDamageIsSetCorrectly() throws NullAnimationException, NotPositiveValueException {
        Mob dummy = Mob.mockGenerate("test");
        int change = 10;
        dummy.setAttackDamage(change);
        assertEquals(dummy.getAttackDamage(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testAttackDamageCannotBeLessThanZero() throws NotPositiveValueException, NullAnimationException {
        Mob dummy = Mob.mockGenerate("test");
        int change = -1;
        dummy.setAttackDamage(change);
    }

    @Test
    public void testAttackDamageCanBeZero() throws NotPositiveValueException, NullAnimationException {
        Mob dummy = Mob.mockGenerate("test");
        int change = 0;
        dummy.setAttackDamage(change);
        assertEquals(dummy.getAttackDamage(), 0);
    }

    //DAMAGE CALCULATION TESTS

    @Test
    public void testDamageIsCalculatedCorrectly() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        int life = dummy.getHp();
        int damage = 10;
        dummy.damage(damage);
        assertEquals(dummy.getHp(), life - damage);
    }

    @Test
    public void testDamageDoesNotAffectMaxHp() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        int startingLife = dummy.getMaxHp();
        dummy.damage(10);
        assertEquals(dummy.getMaxHp(), startingLife);
    }

    @Test
    public void testIfDamageExceedsHpThenHpEqualsZero() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        int damage = dummy.getMaxHp() + 10;
        dummy.damage(damage);
        assertEquals(dummy.getHp(), 0);
    }

    //POSITIONING TESTS

    @Test
    public void testMobIsPositionedAtOrigin() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        assertEquals((int)dummy.getX(), 0);
        assertEquals((int)dummy.getY(), 0);
    }

    @Test
    public void testMobIsPositionedCorrectly() throws NullAnimationException{
        int x = 10;
        int y = 20;
        Mob dummy = Mob.mockGenerate("test", x, y);
        assertEquals((int)dummy.getX(), x);
        assertEquals((int)dummy.getY(), y);
    }

    @Test
    public void testMobCanBePositionedAtNegativeCoordinates() throws NullAnimationException{
        int x = -10;
        int y = -20;
        Mob dummy = Mob.mockGenerate("test", x, y);
        assertEquals((int)dummy.getX(), x);
        assertEquals((int)dummy.getY(), y);
    }

    //HEIGHT TESTS

    @Test
    public void testHeightIsReadCorrectly() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        assertEquals((int)dummy.getHeight(), 42);
    }

    //WIDTH TEST

    @Test
    public void testWidthIsReadCorrectly() throws NullAnimationException{
        Mob dummy = Mob.mockGenerate("test");
        assertEquals((int)dummy.getWidth(), 17);
    }

    //REPOSITIONING TESTS

    @Test
    public void testMobIsRepositionedCorrectlyWithPositiveXAndPositiveY() throws NullAnimationException{
        int x = 22;
        int y = 33;
        Mob dummy = Mob.mockGenerate("test");
        dummy.setPosition(x, y);
        assertEquals((int)dummy.getX(), x);
        assertEquals((int)dummy.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyWithPositiveXAndNegativeY() throws NullAnimationException{
        int x = 22;
        int y = -33;
        Mob dummy = Mob.mockGenerate("test");
        dummy.setPosition(x, y);
        assertEquals((int)dummy.getX(), x);
        assertEquals((int)dummy.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyWithNegativeXAndNegativeY() throws NullAnimationException{
        int x = -22;
        int y = -33;
        Mob dummy = Mob.mockGenerate("test");
        dummy.setPosition(x, y);
        assertEquals((int)dummy.getX(), x);
        assertEquals((int)dummy.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyWithNegativeXAndPositiveY() throws NullAnimationException{
        int x = -22;
        int y = 33;
        Mob dummy = Mob.mockGenerate("test");
        dummy.setPosition(x, y);
        assertEquals((int)dummy.getX(), x);
        assertEquals((int)dummy.getY(), y);
    }

    @Test
    public void testMobIsRepositionedCorrectlyAtOrigin() throws NullAnimationException{
        int x = 0;
        int y = 0;
        Mob dummy = Mob.mockGenerate("test");
        dummy.setPosition(x, y);
        assertEquals((int)dummy.getX(), x);
        assertEquals((int)dummy.getY(), y);
    }


    //X MOVEMENT TESTING

    @Test
    public void testMobCanMoveOnXAxisWithPositiveDelta() throws NullAnimationException{
        int x = 0;
        int y = 0;
        int dx = 22;
        Mob dummy = Mob.mockGenerate("test", x, y);
        dummy.moveX(dx);
        assertEquals((int)dummy.getX(), x + dx);
    }

    @Test
    public void testMobCanMoveOnXAxisWithNegativeDelta() throws NullAnimationException{
        int x = 0;
        int y = 0;
        int dx = -22;
        Mob dummy = Mob.mockGenerate("test", x, y);
        dummy.moveX(dx);
        assertEquals((int)dummy.getX(), x + dx);
    }

    @Test
    public void testMobCanMoveOnXAxisWithZeroDelta() throws NullAnimationException{
        int x = 0;
        int y = 0;
        int dx = 0;
        Mob dummy = Mob.mockGenerate("test", x, y);
        dummy.moveX(dx);
        assertEquals((int)dummy.getX(), x + dx);
    }

    //Y-AXIS MOVEMENT TESTING

    @Test
    public void testMobCanMoveOnYAxisWithPositiveDelta() throws NullAnimationException{
        int x = 0;
        int y = 0;
        int dy = 22;
        Mob dummy = Mob.mockGenerate("test", x, y);
        dummy.moveY(dy);
        assertEquals((int)dummy.getY(), x + dy);
    }

    @Test
    public void testMobCanMoveOnYAxisWithNegativeDelta() throws NullAnimationException{
        int x = 0;
        int y = 0;
        int dy = -22;
        Mob dummy = Mob.mockGenerate("test", x, y);
        dummy.moveY(dy);
        assertEquals((int)dummy.getY(), x + dy);
    }

    @Test
    public void testMobCanMoveOnYAxisWithZeroDelta() throws NullAnimationException{
        int x = 0;
        int y = 0;
        int dy = 0;
        Mob dummy = Mob.mockGenerate("test", x, y);
        dummy.moveY(dy);
        assertEquals((int)dummy.getY(), x + dy);
    }

}
