package Unit.src.missions;

import missions.CollectNItemsMission;
import missions.Mission;
import missions.MissionTarget;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class CollectNItemsMissionTest {

    @Mock private MissionTarget correctitem;
    @Mock private MissionTarget wrongitem;
    @Mock private Mission samplemission;

    private static final String correctItemID = "correct";
    private static final String wrongItemID = "wrong";
    private static final int numInteractions = 3;
    private CollectNItemsMission mission;

    @Before
    public void setUp(){
        mission = new CollectNItemsMission(correctItemID, numInteractions);

        samplemission = Mockito.mock(Mission.class);

        correctitem = Mockito.mock(MissionTarget.class);
        wrongitem = Mockito.mock(MissionTarget.class);
        Mockito.when(correctitem.getID()).thenReturn(correctItemID);
        Mockito.when(wrongitem.getID()).thenReturn(wrongItemID);
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


}
