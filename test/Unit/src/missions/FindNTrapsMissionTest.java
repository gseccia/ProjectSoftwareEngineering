package Unit.src.missions;

import elements.Item;
import missions.FindNTrapsMission;
import missions.Mission;
import missions.MissionTarget;
import missions.StorageRoom;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

public class FindNTrapsMissionTest {

    @Mock private MissionTarget wrongitem;
    @Mock private Mission samplemission;
    @Mock private StorageRoom mockStorage;

    private static final String CORRECT_ITEM_ID = "pizza";
    private static final int NUM_INTERACTIONS = 3;
    private static final int DIFFICULTY = 2;

    private FindNTrapsMission mission;
    private Set<Item> mockSet;

    @Before
    public void setUp(){
        mission = new FindNTrapsMission(CORRECT_ITEM_ID, NUM_INTERACTIONS, DIFFICULTY);

        wrongitem = Mockito.mock(Item.class);

        samplemission = Mockito.mock(Mission.class);

        mockSet = new HashSet<>();

        mockStorage = Mockito.mock(StorageRoom.class);
        doAnswer((i) -> {
           mockSet.add((Item)i.getArgument(0));
           return null;
        }).when(mockStorage).collectItem(any());

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
    public void testGetNumInteractionsReturnTheCorrectNumber() {
        assertEquals(mission.getNumInteractions(), NUM_INTERACTIONS);
    }

    @Test
    public void testGetNumInteractionsIsNotUpdatedWithIncorrectItems() {
        mission.check(wrongitem);
        assertEquals(mission.getNumInteractions(), NUM_INTERACTIONS);
    }


}
