package test.Unit.configuration;

import configuration.MobConfiguration;
import elements.Mob;
import org.junit.Test;

import static junit.framework.Assert.assertSame;
import static junit.framework.TestCase.assertEquals;

public class MobConfigurationTest {

    @Test
    public void testMobConfigurationIsASingleton(){
        MobConfiguration conf1 = MobConfiguration.getInstance();
        MobConfiguration conf2 = MobConfiguration.getInstance();
        assertSame(conf1, conf2);
    }

    @Test
    public void testHpAreReadCorrectly(){
        MobConfiguration conf = MobConfiguration.getInstance();
        int hp = 100;
        assertEquals(conf.getHp("test"), hp);
    }

}
