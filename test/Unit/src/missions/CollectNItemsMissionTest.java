package Unit.src.missions;

import missions.CollectNItemsMission;
import missions.MissionItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class CollectNItemsMissionTest {

    @Mock private MissionItem correctitem;
    @Mock private MissionItem wrongitem;

    private static final String correctItemID = "correct";
    private static final String wrongItemID = "wrong";
    private static final int numInteractions = 3;
    private CollectNItemsMission mission;

    @Before
    public void setUp(){
        mission = new CollectNItemsMission(correctItemID, numInteractions);

        correctitem = Mockito.mock(MissionItem.class);
        wrongitem = Mockito.mock(MissionItem.class);
        Mockito.when(correctitem.getID()).thenReturn(correctItemID);
        Mockito.when(wrongitem.getID()).thenReturn(wrongItemID);
    }

    @Test
    public void testAddReturnsFalse(){
        assertFalse(mission.add(new CollectNItemsMission(wrongItemID, 1)));
    }

    @Test
    public void testUncompletedMissionReturnsFalse() {
        assertFalse(mission.completed());
    }

    @Test
    public void testMissionIsCompletedPassingTheCorrectNumberOfCorrectItems() {
        for(int i=0; i<numInteractions; i++){
            mission.check(correctitem);
        }
        assertTrue(mission.completed());
    }

    @Test
    public void testMissionIsNotCompletedPassingTheCorrectNumberOfIncorrectItems() {
        for(int i=0; i<numInteractions; i++){
            mission.check(wrongitem);
        }
        assertFalse(mission.completed());
    }

    @Test
    public void testMissionIsNotCompletedPassingTheIncorrectNumberOfIncorrectItems() {
        for(int i=0; i<numInteractions-1; i++){
            mission.check(wrongitem);
        }
        assertFalse(mission.completed());
    }

    @Test
    public void testGetNumInteractionsReturnTheCorrectNumber() {
        assertEquals(mission.getNumInteractions(), numInteractions);
    }

    @Test
    public void testGetNumInteractionsIsNotUpdatedWithIncorrectItems() {
        mission.check(wrongitem);
        assertEquals(mission.getNumInteractions(), numInteractions);
    }

    @Test
    public void testGetNumInteractionsIsUpdatedWithCorrectItems() {
        mission.check(correctitem);
        assertEquals(mission.getNumInteractions(), numInteractions-1);
    }

    //END OF SUPERCLASS TESTS

    @Test
    public void testGetItemPopulationReturnsTheIDAndTheNumberOfItems() {
        Map<String, Integer> ret = mission.getItemPopulation();
        Set<String> keys = ret.keySet();
        for(String key : keys){
            assertEquals(key, correctItemID);
            assertEquals((int)ret.get(key), numInteractions);
        }
    }

    @Test
    public void testGetEnemyPopulationReturnsNull() {
        assertNull(mission.getEnemyPopulation());
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