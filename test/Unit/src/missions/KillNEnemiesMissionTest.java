package Unit.src.missions;

import missions.KillNEnemiesMission;
import missions.Mission;
import missions.MissionItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class KillNEnemiesMissionTest {

    @Mock private MissionItem correctmob;
    @Mock private MissionItem wrongmob;
    @Mock private Mission samplemission;

    private static final String correctMobID = "correct";
    private static final String wrongMobID = "wrong";
    private static final int numInteractions = 3;
    private KillNEnemiesMission mission;

    @Before
    public void setUp(){
        mission = new KillNEnemiesMission(correctMobID, numInteractions);

        samplemission = Mockito.mock(Mission.class);

        correctmob = Mockito.mock(MissionItem.class);
        wrongmob = Mockito.mock(MissionItem.class);
        Mockito.when(correctmob.getID()).thenReturn(correctMobID);
        Mockito.when(wrongmob.getID()).thenReturn(wrongMobID);
    }

    @Test
    public void testAddReturnsFalse(){
        assertFalse(mission.add(samplemission));
    }

    @Test
    public void testNumMissionsReturnsZero() {
        assertEquals(0, mission.numMissions());
    }

    @Test
    public void testUncompletedMissionReturnsFalse() {
        assertFalse(mission.completed());
    }

    @Test
    public void testMissionIsCompletedPassingTheCorrectNumberOfCorrectItems() {
        for(int i=0; i<numInteractions; i++){
            mission.check(correctmob);
        }
        assertTrue(mission.completed());
    }

    @Test
    public void testMissionIsNotCompletedPassingTheCorrectNumberOfIncorrectItems() {
        for(int i=0; i<numInteractions; i++){
            mission.check(wrongmob);
        }
        assertFalse(mission.completed());
    }

    @Test
    public void testMissionIsNotCompletedPassingTheIncorrectNumberOfIncorrectItems() {
        for(int i=0; i<numInteractions-1; i++){
            mission.check(wrongmob);
        }
        assertFalse(mission.completed());
    }

    @Test
    public void testGetNumInteractionsReturnTheCorrectNumber() {
        assertEquals(mission.getNumInteractions(), numInteractions);
    }

    @Test
    public void testGetNumInteractionsIsNotUpdatedWithIncorrectItems() {
        mission.check(wrongmob);
        assertEquals(mission.getNumInteractions(), numInteractions);
    }

    @Test
    public void testGetNumInteractionsIsUpdatedWithCorrectItems() {
        mission.check(correctmob);
        assertEquals(mission.getNumInteractions(), numInteractions-1);
    }

    //END OF SUPERCLASS TESTS

    @Test
    public void testGetEnemyPopulationReturnsTheIDAndTheNumberOfItems() {
        Map<String, Integer> ret = mission.getEnemyPopulation();
        Set<String> keys = ret.keySet();
        for(String key : keys){
            assertEquals(key, correctMobID);
            assertEquals((int)ret.get(key), numInteractions);
        }
    }

    @Test
    public void testGetItemPopulationReturnsNull() {
        assertNull(mission.getItemPopulation());
    }

    @Test
    public void testGetEnemySetReturnsNull() {
        assertNull(mission.getEnemySet());
    }

    @Test
    public void testGetItemSetReturnsNull() {
        assertNull(mission.getItemSet());
    }
}
