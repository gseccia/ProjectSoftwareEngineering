package Unit.src.attack.states;

import attacks.states.KillEnemiesState;
import attacks.states.SpecialAttackState;
import elements.Enemy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;

public class KillEnemiesStateTest {

    private final int LIFE = 100;

    private Set<Enemy> enemySet, emptySet;
    private int mockLife;

    @Before
    public void setUp(){
        Enemy tmp = Mockito.mock(Enemy.class);
        doAnswer((i) -> {
            mockLife -= (int)i.getArgument(0);
            return null;
        }).when(tmp).damage(anyInt());
        doAnswer((i) -> LIFE).when(tmp).getMaxHp();

        enemySet = new HashSet<>();
        emptySet = new HashSet<>();
        enemySet.add(tmp);
    }

    @Test
    public void testDamageIsCalculatedCorrectly() {
        mockLife = LIFE;
        KillEnemiesState s = new KillEnemiesState(enemySet);
        s.execute();
        assertEquals(0, mockLife);
    }

    @Test
    public void testExecuteWorksWithEmptySet() {
        KillEnemiesState s = new KillEnemiesState(enemySet);
        s.execute();
    }

    @Test(expected = NullPointerException.class)
    public void testExecuteThrowsExceptionIfSetIsNull() {
        KillEnemiesState s = new KillEnemiesState(null);
        s.execute();
    }

    @Test
    public void testStateIsNotFinishedBeforeExecute() {
        KillEnemiesState s = new KillEnemiesState(enemySet);
        assertFalse(s.finished());
    }

    @Test
    public void testStateIsFinishedAfterExecute() {
        KillEnemiesState s = new KillEnemiesState(enemySet);
        s.execute();
        assertTrue(s.finished());
    }

    @Test
    public void testStateIsNotExecutedBeforeExecute() {
        KillEnemiesState s = new KillEnemiesState(enemySet);
        assertFalse(s.executed());
    }

    @Test
    public void testStateIsExecutedAfterExecute() {
        KillEnemiesState s = new KillEnemiesState(enemySet);
        s.execute();
        assertTrue(s.executed());
    }

    @Test
    public void testNextReturnsNullIfNoNextStateIsPassed() {
        KillEnemiesState s = new KillEnemiesState(enemySet);
        assertNull(s.next());
    }

    @Test
    public void testNextReturnsNullIfNullIsPassed() {
        KillEnemiesState s = new KillEnemiesState(enemySet, null);
        assertNull(s.next());
    }

    @Test
    public void testNextReturnsTheNextStatePassedAsArgument() {
        SpecialAttackState ret = Mockito.mock(SpecialAttackState.class);
        KillEnemiesState s = new KillEnemiesState(enemySet, ret);
        assertEquals(ret, s.next());
    }

}
