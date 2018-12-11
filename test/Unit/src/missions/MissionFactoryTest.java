package Unit.src.missions;

import configuration.EnemyConfiguration;
import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import missions.Mission;
import missions.MissionsFactory;
import missions.NotEnoughMissionsException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import java.util.HashSet;

import static org.junit.Assert.*;

public class MissionFactoryTest {

    private static final String mob1 = "mob1";
    private static final String item1 = "item1";
    private static final String item2 = "item2";
    private static final int defaultCapacity = 20;
    private static final int defaultDifficulty = 2;

    @Mock private EnemyConfiguration mobconf;
    @Mock private ItemConfiguration itemconf;

    @Before
    public void setUp() {

        HashSet<String> mobNames = new HashSet<>();
        mobNames.add(mob1);

        mobconf = Mockito.mock(EnemyConfiguration.class);
        try {
            Mockito.when(mobconf.getAttack(mob1)).thenReturn(100);
            Mockito.when(mobconf.getHeight(mob1)).thenReturn(42);
            Mockito.when(mobconf.getWidth(mob1)).thenReturn(17);
            Mockito.when(mobconf.getHp(mob1)).thenReturn(100);
            Mockito.when(mobconf.getFaceDown(mob1)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceStill(mob1)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceUp(mob1)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceLeft(mob1)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceRight(mob1)).thenReturn(new Animation());
            Mockito.when(mobconf.getMobNames()).thenReturn(mobNames);
        } catch (NoSuchElementInConfigurationException | SlickException e) {
            e.printStackTrace();
        }

        HashSet<String> itemNames = new HashSet<>();
        itemNames.add(item1);
        itemNames.add(item2);

        itemconf = Mockito.mock(ItemConfiguration.class);
        Mockito.when(itemconf.getItemNames()).thenReturn(itemNames);
    }

    @Test
    public void testMissionFactoryCanCreateMissions() throws NotEnoughMissionsException {
        MissionsFactory factory = new MissionsFactory(defaultCapacity, defaultCapacity, defaultDifficulty, mobconf, itemconf);
        Mission manager = factory.generateMissions();
        assertEquals(defaultDifficulty, manager.numMissions());
    }

    @Test
    public void testCapacityCanBeAReallyBigNumber() throws NotEnoughMissionsException {
        MissionsFactory factory = new MissionsFactory(100000000, 100000000, defaultDifficulty, mobconf, itemconf);
        factory.generateMissions();
    }

    @Test(expected = NotEnoughMissionsException.class)
    public void testIfDifficultyIsTooHighThenExceptionIsRaised() throws NotEnoughMissionsException {
        MissionsFactory factory = new MissionsFactory(defaultCapacity, defaultCapacity, 10, mobconf, itemconf);
        factory.generateMissions();
    }

    @Test
    public void testNumberOfInteractionsIsEqualToCapacity() throws NotEnoughMissionsException {
        MissionsFactory factory = new MissionsFactory(defaultCapacity, defaultCapacity, defaultDifficulty, mobconf, itemconf);
        Mission manager = factory.generateMissions();
        assertEquals(defaultDifficulty, manager.numMissions());
    }

}
