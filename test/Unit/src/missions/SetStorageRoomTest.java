package Unit.src.missions;

import elements.Enemy;
import elements.Item;
import missions.SetStorageRoom;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SetStorageRoomTest {

    @Mock private Item mockItem;
    @Mock private Enemy mockEnemy;

    private Set<Item> mockItemSet;
    private Set<Enemy> mockEnemySet;
    private SetStorageRoom room;

    @Before
    public void setUp() {
        room = new SetStorageRoom();

        mockItem = Mockito.mock(Item.class);
        mockEnemy = Mockito.mock(Enemy.class);

        mockItemSet = new HashSet<>();
        mockItemSet.add(mockItem);

        mockEnemySet = new HashSet<>();
        mockEnemySet.add(mockEnemy);
    }

    @Test
    public void testCollectItemDoesNotThrowException() {
        room.collectItem(mockItem);
    }

    @Test
    public void testCollectItemDoesNotThrowExceptionWithNull() {
        room.collectItem(null);
    }

    @Test
    public void testCollectEnemyDoesNotThrowException() {
        room.collectEnemy(mockEnemy);
    }

    @Test
    public void testCollectEnemyDoesNotThrowExceptioNWithNull() {
        room.collectEnemy(null);
    }

    @Test
    public void testGetItemSetReturnsTheItemPassed() {
        room.collectItem(mockItem);
        assertEquals(mockItemSet, room.getItemSet());
    }

    @Test
    public void testGetEnemySetReturnsTheEnemyPassed() {
        room.collectEnemy(mockEnemy);
        assertEquals(mockEnemySet, room.getEnemySet());
    }
}
