package test.Unit.elements;

import elements.Mob;
import elements.NullAnimationException;
import org.junit.Test;
import org.newdawn.slick.SlickException;

import static org.junit.Assert.assertEquals;

public class MobTest {

    @Test
    public void testHpIsReadCorrectly(){
        try {
            Mob dummy = Mob.generate("test");
            assertEquals(dummy.getHp(), 100);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHpIsSetCorrectly(){
        try {
            Mob dummy = Mob.generate("test");
            int change = 10;
            dummy.setHp(change);
            assertEquals(dummy.getHp(), change);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMaxHpIsReadCorrectly(){
        try {
            Mob dummy = Mob.generate("test");
            assertEquals(dummy.getMaxHp(), 100);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMaxHpIsSetCorrectly(){
        try {
            Mob dummy = Mob.generate("test");
            int change = 10;
            dummy.setMaxHp(change);
            assertEquals(dummy.getMaxHp(), change);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAttackDamageIsReadCorrectly(){
        try {
            Mob dummy = Mob.generate("test");
            assertEquals(dummy.getAttackDamage(), 100);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAttackDamageIsSetCorrectly(){
        try {
            Mob dummy = Mob.generate("test");
            int change = 10;
            dummy.setAttackDamage(change);
            assertEquals(dummy.getAttackDamage(), change);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDamageIsCalculatedCorrectly(){
        try {
            Mob dummy = Mob.generate("test");
            int life = dummy.getHp();
            int damage = 10;
            dummy.damage(damage);
            assertEquals(dummy.getHp(), life-damage);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDamageDoesNotAffectMaxHp(){
        try {
            Mob dummy = Mob.generate("test");
            int startingLife = dummy.getMaxHp();
            dummy.damage(10);
            assertEquals(dummy.getMaxHp(), startingLife);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMobIsPositionedAtOrigin(){
        try {
            Mob dummy = Mob.generate("test");
            assertEquals(dummy.getX(), 0.0);
            assertEquals(dummy.getY(), 0.0);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMobIsPositionedCorrectly(){
        try {
            int x = 10;
            int y = 20;
            Mob dummy = Mob.generate("test", x, y);
            assertEquals(dummy.getX(), x);
            assertEquals(dummy.getY(), y);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHeightIsReadCorrectly(){
        try {
            Mob dummy = Mob.generate("test");
            assertEquals(dummy.getHeight(), 42);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWidthIsReadCorrectly(){
        try {
            Mob dummy = Mob.generate("test");
            assertEquals(dummy.getWidth(), 17);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMobIsRepositionedCorrectly(){
        try {
            int x = 22;
            int y = 33;
            Mob dummy = Mob.generate("test");
            dummy.setPosition(x, y);
            assertEquals(dummy.getX(), x);
            assertEquals(dummy.getY(), y);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMobCanMoveOnXAxis(){
        try {
            int x = 0;
            int y = 0;
            int dx = 22;
            Mob dummy = Mob.generate("test", x, y);
            dummy.moveX(dx);
            assertEquals(dummy.getX(), x+dx);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMobCanMoveOnYAxis(){
        try {
            int x = 0;
            int y = 0;
            int dy = 22;
            Mob dummy = Mob.generate("test", x, y);
            dummy.moveY(dy);
            assertEquals(dummy.getY(), y+dy);
        } catch (SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }
}
