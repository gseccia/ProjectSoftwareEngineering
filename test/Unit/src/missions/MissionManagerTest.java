package Unit.src.missions;

import configuration.EnemyConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Enemy;
import elements.Item;
import missions.CollectNItemsMission;
import missions.Mission;
import missions.MissionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import static org.junit.Assert.*;

public class MissionManagerTest {

    private static final String item1 = "item1";
    private static final String item2 = "item2";
    private static final String mob1 = "mob1";
    private static final String mob2 = "mob2";
    private static final String mob3 = "mob3";
    private static final int numInt1 = 1;
    private static final int numInt2 = 2;

    @Mock private EnemyConfiguration mobconf;
    @Mock private Item mockItem1;
    @Mock private Item mockItem2;
    @Mock private Enemy mockEnemy1;
    @Mock private Enemy mockEnemy2;
    @Mock private Mission completedMission;
    @Mock private Mission uncompletedMission;

    private Mission mission1;
    private Mission mission2;
    private Mission equalToMission1;
    private MissionManager manager;

    @Before
    public void setUp() {
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
            Mockito.when(mobconf.getAttack(mob2)).thenReturn(100);
            Mockito.when(mobconf.getHeight(mob2)).thenReturn(42);
            Mockito.when(mobconf.getWidth(mob2)).thenReturn(17);
            Mockito.when(mobconf.getHp(mob2)).thenReturn(100);
            Mockito.when(mobconf.getFaceDown(mob2)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceStill(mob2)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceUp(mob2)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceLeft(mob2)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceRight(mob2)).thenReturn(new Animation());
            Mockito.when(mobconf.getAttack(mob3)).thenReturn(100);
            Mockito.when(mobconf.getHeight(mob3)).thenReturn(42);
            Mockito.when(mobconf.getWidth(mob3)).thenReturn(17);
            Mockito.when(mobconf.getHp(mob3)).thenReturn(100);
            Mockito.when(mobconf.getFaceDown(mob3)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceStill(mob3)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceUp(mob3)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceLeft(mob3)).thenReturn(new Animation());
            Mockito.when(mobconf.getFaceRight(mob3)).thenReturn(new Animation());
        } catch (NoSuchElementInConfigurationException | SlickException e) {
            e.printStackTrace();
        }

        mission1 = new CollectNItemsMission("1", 10);
        mission2 = new CollectNItemsMission("2", 10);
        equalToMission1 =  new CollectNItemsMission("1", 10);

        completedMission = Mockito.mock(Mission.class);
        Mockito.when(completedMission.completed()).thenReturn(true);
        Mockito.when(completedMission.getNumInteractions()).thenReturn(numInt1);

        uncompletedMission = Mockito.mock(Mission.class);
        Mockito.when(uncompletedMission.completed()).thenReturn(false);
        Mockito.when(uncompletedMission.getNumInteractions()).thenReturn(numInt2);

        mockItem1 = Mockito.mock(Item.class);
        Mockito.when(mockItem1.getID()).thenReturn(item1);

        mockItem2 = Mockito.mock(Item.class);
        Mockito.when(mockItem2.getID()).thenReturn(item2);

        mockEnemy1 = Mockito.mock(Enemy.class);
        Mockito.when(mockEnemy1.getID()).thenReturn(mob1);

        mockEnemy2 = Mockito.mock(Enemy.class);
        Mockito.when(mockEnemy2.getID()).thenReturn(mob2);

        manager = new MissionManager();
    }

    @Test
    public void test2DifferentMissionsCanBeAdded() {
        assertTrue(manager.add(mission1));
        assertTrue(manager.add(mission2));
    }

    @Test
    public void testNumInteractionReturnsTheTotalNumberOfInteractions() {
        manager.add(completedMission);
        manager.add(uncompletedMission);
        assertEquals(numInt1+numInt2, manager.getNumInteractions());
    }

    @Test
    public void testNumMissionsReturnsCorrectNumberOfMissionsAdded() {
        manager.add(mission1);
        manager.add(mission2);
        assertEquals(2, manager.numMissions());
    }

    @Test
    public void test2EqualMissionsCannotBeAdded() {
        assertTrue(manager.add(mission1));
        assertFalse(manager.add(equalToMission1));
    }

    @Test
    public void testIfAllMissionsAreCompletedThenCompletedReturnsTrue() {
        manager.add(completedMission);
        assertTrue(manager.completed());
    }

    @Test
    public void testIfAMissionIsNotCompletedThenCompletedReturnsFalse() {
        manager.add(completedMission);
        manager.add(uncompletedMission);
        assertFalse(manager.completed());
    }

    @Test
    public void testIfThereAreNoMissionsThenCompletedReturnsTrue() {
        assertTrue(manager.completed());
    }

}
