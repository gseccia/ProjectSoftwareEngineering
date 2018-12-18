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
import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {
    @Mock
    private MobConfiguration playerconf;

    private Player player;

    @Before
    public void setUp() {
        this.playerconf = Mockito.mock(MobConfiguration.class);
        try {
            String id = "test";
            Mockito.when(this.playerconf.getAttack(id)).thenReturn(100);
            Mockito.when(this.playerconf.getHeight(id)).thenReturn(42);
            Mockito.when(this.playerconf.getWidth(id)).thenReturn(17);
            Mockito.when(this.playerconf.getHp(id)).thenReturn(100);
            Mockito.when(this.playerconf.getFaceDown(id)).thenReturn(new Animation());
            Mockito.when(this.playerconf.getFaceUp(id)).thenReturn(new Animation());
            Mockito.when(this.playerconf.getFaceLeft(id)).thenReturn(new Animation());
            Mockito.when(this.playerconf.getFaceRight(id)).thenReturn(new Animation());


            this.player = new Player(this.playerconf,id);

        } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
    }


    //POSITIONING TESTS

    /**********************/
    @Test
    public void testHpIsReadCorrectly() {
        Assert.assertEquals(this.player.getHp(), 100);
    }

    @Test
    public void testHpIsSetCorrectly()  {
        int change = 10;
        this.player.setHp(change);
        Assert.assertEquals(this.player.getHp(), change);
    }

    @Test
    public void testHpValueCannotBeLessThanZero()  {
        int change = -1;
        this.player.setHp(change);
        Assert.assertEquals(this.player.getHp(), 0);
    }

    @Test
    public void testHpValueCanBeZero() {
        int change = 0;
        this.player.setHp(change);
        Assert.assertEquals(this.player.getHp(), 0);
    }

    //MAX HP TESTS

    @Test
    public void testMaxHpIsReadCorrectly()  {
        Assert.assertEquals(this.player.getMaxHp(), 100);
    }

    @Test
    public void testMaxHpIsSetCorrectly() throws NotPositiveValueException {
        int change = 10;
        this.player.setMaxHp(change);
        Assert.assertEquals(this.player.getMaxHp(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeLessThanZero() throws NotPositiveValueException {
        int change = -1;
        this.player.setMaxHp(change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testMaxHpCannotBeEqualToZero() throws NotPositiveValueException{
        int change = 0;
        this.player.setMaxHp(change);
    }

    //ATTACK DAMAGE TESTS

    @Test
    public void testAttackDamageIsReadCorrectly() {
        Assert.assertEquals(this.player.getAttackDamage(), 100);
    }

    @Test
    public void testAttackDamageIsSetCorrectly() throws NotPositiveValueException {
        int change = 10;
        this.player.setAttackDamage(change);
        Assert.assertEquals(this.player.getAttackDamage(), change);
    }

    @Test(expected = NotPositiveValueException.class)
    public void testAttackDamageCannotBeLessThanZero() throws NotPositiveValueException {
        int change = -1;
        this.player.setAttackDamage(change);
    }

    @Test
    public void testAttackDamageCanBeZero() throws NotPositiveValueException {
        int change = 0;
        this.player.setAttackDamage(change);
        Assert.assertEquals(this.player.getAttackDamage(), 0);
    }

    //DAMAGE CALCULATION TESTS

    @Test
    public void testDamageIsCalculatedCorrectly() {
        int life = this.player.getHp();
        int damage = 10;
        this.player.damage(damage);
        Assert.assertEquals(this.player.getHp(), life - damage);
    }

    @Test
    public void testDamageDoesNotAffectMaxHp() {
        int startingLife = this.player.getMaxHp();
        this.player.damage(10);
        Assert.assertEquals(this.player.getMaxHp(), startingLife);
    }

    @Test
    public void testIfDamageExceedsHpThenHpEqualsZero() {
        int damage = this.player.getMaxHp() + 10;
        this.player.damage(damage);
        Assert.assertEquals(this.player.getHp(), 0);
    }

    //HEIGHT TESTS

    @Test
    public void testHeightIsReadCorrectly() {
        Assert.assertEquals((int) this.player.getHeight(), 42);
    }

    //WIDTH TEST

    @Test
    public void testWidthIsReadCorrectly() {
        Assert.assertEquals((int) this.player.getWidth(), 17);
    }

    //REPOSITIONING TESTS

    @Test
    public void testPlayerIsRepositionedCorrectlyWithPositiveXAndPositiveY() {
        int x = 22;
        int y = 33;
        this.player.setLocation(x, y);
        assertEquals((int) this.player.getX(), x);
        assertEquals((int) this.player.getY(), y);
    }

    @Test
    public void testPlayerIsRepositionedCorrectlyWithPositiveXAndNegativeY() {
        int x = 22;
        int y = -33;
        this.player.setLocation(x, y);
        Assert.assertEquals((int) this.player.getX(), x);
        Assert.assertEquals((int) this.player.getY(), y);
    }

    @Test
    public void testPlayerIsRepositionedCorrectlyWithNegativeXAndNegativeY() {
        int x = -22;
        int y = -33;
        this.player.setLocation(x, y);
        Assert.assertEquals((int) this.player.getX(), x);
        Assert.assertEquals((int) this.player.getY(), y);
    }

    @Test
    public void testPlayerIsRepositionedCorrectlyWithNegativeXAndPositiveY() {
        int x = -22;
        int y = 33;
        this.player.setLocation(x, y);
        Assert.assertEquals((int) this.player.getX(), x);
        Assert.assertEquals((int) this.player.getY(), y);
    }

    @Test
    public void testPlayerIsRepositionedCorrectlyAtOrigin() {
        int x = 0;
        int y = 0;
        this.player.setLocation(x, y);
        Assert.assertEquals((int) this.player.getX(), x);
        Assert.assertEquals((int) this.player.getY(), y);
    }


    //X MOVEMENT TESTING

    @Test
    public void testPlayerCanMoveOnXAxisWithPositiveDelta() {
        int x = 0;
        int dx = 22;
        this.player.moveX(dx);
        Assert.assertEquals((int) this.player.getX(), x + dx);
    }

    @Test
    public void testPlayerCanMoveOnXAxisWithNegativeDelta() {
        int x = 0;
        int dx = -22;
        this.player.moveX(dx);
        Assert.assertEquals((int) this.player.getX(), x + dx);
    }

    @Test
    public void testPlayerCanMoveOnXAxisWithZeroDelta() {
        int x = 0;
        int dx = 0;
        this.player.moveX(dx);
        Assert.assertEquals((int) this.player.getX(), x + dx);
    }

    //Y-AXIS MOVEMENT TESTING

    @Test
    public void testPlayerCanMoveOnYAxisWithPositiveDelta() {
        int y = 0;
        int dy = 22;
        this.player.moveY(dy);
        Assert.assertEquals((int) this.player.getY(), y + dy);
    }

    @Test
    public void testPlayerCanMoveOnYAxisWithNegativeDelta() {
        int y = 0;
        int dy = -22;
        this.player.moveY(dy);
        Assert.assertEquals((int) this.player.getY(), y + dy);
    }

    @Test
    public void testPlayerCanMoveOnYAxisWithZeroDelta() {
        int y = 0;
        int dy = 0;
        this.player.moveY(dy);
        Assert.assertEquals((int) this.player.getY(), y + dy);
    }
}