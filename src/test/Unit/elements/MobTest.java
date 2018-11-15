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

}
