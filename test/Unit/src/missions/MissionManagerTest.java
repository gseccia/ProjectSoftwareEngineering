package Unit.src.missions;

import configuration.EnemyConfiguration;
import configuration.ItemConfiguration;
import configuration.MobConfiguration;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class MissionManagerTest {

    private static final String item1 = "item1";
    private static final String item2 = "item2";
    private static final String mob1 = "mob1";
    private static final String mob2 = "mob2";
    private static final String mob3 = "mob3";
    private static final int numItem1 = 3;
    private static final int numItem2 = 2;
    private static final int numItem3 = 3;
    private static final int numMob1 = 3;
    private static final int numMob2 = 2;
    private static final int numMob3 = 3;
    private static final int numInt1 = 1;
    private static final int numInt2 = 2;

    @Mock private EnemyConfiguration mobconf;
    @Mock private ItemConfiguration itemconf;
    @Mock private Item mockItem1;
    @Mock private Item mockItem2;
    @Mock private Enemy mockEnemy1;
    @Mock private Enemy mockEnemy2;
    @Mock private Mission completedMission;
    @Mock private Mission uncompletedMission;
    @Mock private Mission itemMission1;
    @Mock private Mission itemMission2;
    @Mock private Mission itemMission3;
    @Mock private Mission itemMission4;
    @Mock private Mission itemMission5;
    @Mock private Mission itemMission6;
    @Mock private Mission mobMission1;
    @Mock private Mission mobMission2;
    @Mock private Mission mobMission3;
    @Mock private Mission mobMission4;
    @Mock private Mission mobMission5;
    @Mock private Mission mobMission6;

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

        itemconf = Mockito.mock(ItemConfiguration.class);

        mission1 = new CollectNItemsMission("1", 10);
        mission2 = new CollectNItemsMission("2", 10);
        equalToMission1 =  new CollectNItemsMission("1", 10);

        completedMission = Mockito.mock(Mission.class);
        Mockito.when(completedMission.completed()).thenReturn(true);
        Mockito.when(completedMission.getNumInteractions()).thenReturn(numInt1);

        uncompletedMission = Mockito.mock(Mission.class);
        Mockito.when(uncompletedMission.completed()).thenReturn(false);
        Mockito.when(uncompletedMission.getNumInteractions()).thenReturn(numInt2);

        Map<String, Integer> m1 = new HashMap<>();
        m1.put(item1, numItem1);
        itemMission1 = Mockito.mock(Mission.class);
        Mockito.when(itemMission1.getItemPopulation()).thenReturn(m1);

        Map<String, Integer> m2 = new HashMap<>();
        m2.put(item2, numItem2);
        itemMission2 = Mockito.mock(Mission.class);
        Mockito.when(itemMission2.getItemPopulation()).thenReturn(m2);

        Map<String, Integer> m3 = new HashMap<>();
        m3.put(item2, numItem3);
        itemMission3 = Mockito.mock(Mission.class);
        Mockito.when(itemMission3.getItemPopulation()).thenReturn(m3);

        mockItem1 = Mockito.mock(Item.class);
        Mockito.when(mockItem1.getID()).thenReturn(item1);

        mockItem2 = Mockito.mock(Item.class);
        Mockito.when(mockItem2.getID()).thenReturn(item2);

        Set<Item> s1 = new HashSet<>();
        s1.add(mockItem1);
        itemMission4 = Mockito.mock(Mission.class);
        Mockito.when(itemMission4.getItemSet()).thenReturn(s1);

        Set<Item> s2 = new HashSet<>();
        s2.add(mockItem2);
        itemMission5 = Mockito.mock(Mission.class);
        Mockito.when(itemMission5.getItemSet()).thenReturn(s2);

        itemMission6 = Mockito.mock(Mission.class);
        Mockito.when(itemMission6.getItemSet()).thenReturn(s1);

        Map<String, Integer> m4 = new HashMap<>();
        m4.put(mob1, numMob1);
        mobMission1 = Mockito.mock(Mission.class);
        Mockito.when(mobMission1.getEnemyPopulation()).thenReturn(m4);

        Map<String, Integer> m5 = new HashMap<>();
        m5.put(mob2, numMob2);
        mobMission2 = Mockito.mock(Mission.class);
        Mockito.when(mobMission2.getEnemyPopulation()).thenReturn(m5);

        Map<String, Integer> m6 = new HashMap<>();
        m6.put(mob2, numMob3);
        mobMission3 = Mockito.mock(Mission.class);
        Mockito.when(mobMission3.getEnemyPopulation()).thenReturn(m6);

        mockEnemy1 = Mockito.mock(Enemy.class);
        Mockito.when(mockEnemy1.getID()).thenReturn(mob1);

        mockEnemy2 = Mockito.mock(Enemy.class);
        Mockito.when(mockEnemy2.getID()).thenReturn(mob2);

        Set<Enemy> s3 = new HashSet<>();
        s3.add(mockEnemy1);
        mobMission4 = Mockito.mock(Mission.class);
        Mockito.when(mobMission4.getEnemySet()).thenReturn(s3);

        Set<Enemy> s4 = new HashSet<>();
        s4.add(mockEnemy2);
        mobMission5 = Mockito.mock(Mission.class);
        Mockito.when(mobMission5.getEnemySet()).thenReturn(s4);

        mobMission6 = Mockito.mock(Mission.class);
        Mockito.when(mobMission6.getEnemySet()).thenReturn(s3);

        manager = new MissionManager(mobconf, itemconf);
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

    @Test
    public void testGetItemPopulationReturnsNull() {
        assertEquals(new HashMap<String, Integer>(),manager.getItemPopulation());
    }

    @Test
    public void testPopulationOfMissionThatReturnsDifferentItemPopulationsIsReturnedCorrectly() {
        manager.add(itemMission1);
        manager.add(itemMission2);
        assertEquals(numItem1+numItem2, manager.getItemSet().size());
        for(Item i : manager.getItemSet()){
            assertTrue(i.getID().equals(item1) || i.getID().equals(item2));
        }
    }

    @Test
    public void testPopulationOfMissionsThatReturnsSameItemPopulationsIsReturnedCorrectly() {
        manager.add(itemMission2);
        manager.add(itemMission3);
        assertEquals(numItem3, manager.getItemSet().size());
        for(Item i : manager.getItemSet()){
            assertEquals(item2, i.getID());
        }
    }

    @Test
    public void testPopulationOfMissionsThatReturnsDifferentItemPopulationAndSetIsReturnedCorrectly() {
        manager.add(itemMission2);
        manager.add(itemMission4);
        assertEquals(numItem2+1, manager.getItemSet().size());
        for(Item i : manager.getItemSet()){
            assertTrue(i.getID().equals(item1) || i.getID().equals(item2));
        }
    }

    @Test
    public void testPopulationOfMissionsThatReturnsSameItemPopulationAndSetIsReturnedCorrectly() {
        manager.add(itemMission1);
        manager.add(itemMission4);
        assertEquals(numItem1, manager.getItemSet().size());
        for(Item i : manager.getItemSet()){
            assertEquals(item1, i.getID());
        }
    }

    @Test
    public void testPopulationOfMissionsThatReturnsDifferentItemSetIsReturnedCorrectly() {
        manager.add(itemMission5);
        manager.add(itemMission4);
        assertEquals(2, manager.getItemSet().size());
        for(Item i : manager.getItemSet()){
            assertTrue(i.getID().equals(item1) || i.getID().equals(item2));
        }
    }

    @Test
    public void testPopulationOfMissionsThatReturnsSameItemSetIsReturnedCorrectly() {
        manager.add(itemMission4);
        manager.add(itemMission6);
        assertEquals(1, manager.getItemSet().size());
        for(Item i : manager.getItemSet()){
            assertEquals(item1, i.getID());
        }
    }

    @Test
    public void testGetEnemyPopulationReturnsNull() {
        assertEquals(new HashMap<String, Integer>(), manager.getEnemyPopulation());
    }


    @Test
    public void testPopulationOfMissionThatReturnsDifferentEnemyPopulationsIsReturnedCorrectly() {
        manager.add(mobMission1);
        manager.add(mobMission2);
        assertEquals(numMob1+numMob2, manager.getEnemySet().size());
        for(Enemy e : manager.getEnemySet()){
            assertTrue(e.getID().equals(mob1) || e.getID().equals(mob2));
        }
    }

    @Test
    public void testPopulationOfMissionsThatReturnsSameEnemyPopulationsIsReturnedCorrectly() {
        manager.add(mobMission2);
        manager.add(mobMission3);
        assertEquals(numMob3, manager.getEnemySet().size());
        for(Enemy e : manager.getEnemySet()){
            assertEquals(mob2, e.getID());
        }
    }

    @Test
    public void testPopulationOfMissionsThatReturnsDifferentEnemyPopulationAndSetIsReturnedCorrectly() {
        manager.add(mobMission2);
        manager.add(mobMission4);
        assertEquals(numItem2+1, manager.getEnemySet().size());
        for(Enemy e : manager.getEnemySet()){
            assertTrue(e.getID().equals(mob1) || e.getID().equals(mob2));
        }
    }

    @Test
    public void testPopulationOfMissionsThatReturnsSameEnemyPopulationAndSetIsReturnedCorrectly() {
        manager.add(mobMission1);
        manager.add(mobMission4);
        assertEquals(numMob1, manager.getEnemySet().size());
        for(Enemy e : manager.getEnemySet()){
            assertEquals(mob1, e.getID());
        }
    }

    @Test
    public void testPopulationOfMissionsThatReturnsDifferentEnemySetIsReturnedCorrectly() {
        manager.add(mobMission5);
        manager.add(mobMission4);
        assertEquals(2, manager.getEnemySet().size());
        for(Enemy e : manager.getEnemySet()){
            assertTrue(e.getID().equals(mob1) || e.getID().equals(mob2));
        }
    }

    @Test
    public void testPopulationOfMissionsThatReturnsSameEnemySetIsReturnedCorrectly() {
        manager.add(mobMission4);
        manager.add(mobMission6);
        assertEquals(1, manager.getEnemySet().size());
        for(Enemy e : manager.getEnemySet()){
            assertEquals(mob1, e.getID());
        }
    }
}
