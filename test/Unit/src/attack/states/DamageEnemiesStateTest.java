package Unit.src.attack.states;

import attacks.states.DamageEnemiesState;
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

public class DamageEnemiesStateTest {

    private final int LIFE = 100;
    private final int DAMAGE = 20;

    private Set<Enemy> enemySet, emptySet;
    private int mockLife;

    @Before
    public void setUp(){
        Enemy tmp = Mockito.mock(Enemy.class);
        doAnswer((i) -> {
            mockLife -= (int)i.getArgument(0);
            return null;
        }).when(tmp).damage(anyInt());

        enemySet = new HashSet<>();
        emptySet = new HashSet<>();
        enemySet.add(tmp);
    }

    @Test
    public void testDamageIsCalculatedCorrectly() {
        mockLife = LIFE;
        DamageEnemiesState s = new DamageEnemiesState(enemySet, DAMAGE);
        s.execute();
        assertEquals(LIFE-DAMAGE, mockLife);
    }

    @Test
    public void testExecuteWorksWithEmptySet() {
        DamageEnemiesState s = new DamageEnemiesState(emptySet, DAMAGE);
    }

    @Test(expected = NullPointerException.class)
    public void testExecuteThrowsExceptionIfSetIsNull() {
        DamageEnemiesState s = new DamageEnemiesState(null, DAMAGE);
        s.execute();
    }

    @Test
    public void testStateIsNotFinishedBeforeExecute() {
        DamageEnemiesState s = new DamageEnemiesState(emptySet, DAMAGE);
        assertFalse(s.finished());
    }

    @Test
    public void testStateIsNotFinishedAfterExecute() {
        DamageEnemiesState s = new DamageEnemiesState(emptySet, DAMAGE);
        s.execute();
        assertTrue(s.finished());
    }

    @Test
    public void testStateIsNotExecutedBeforeExecute() {
        DamageEnemiesState s = new DamageEnemiesState(emptySet, DAMAGE);
        assertFalse(s.executed());
    }

    @Test
    public void testStateIsExecutedAfterExecute() {
        DamageEnemiesState s = new DamageEnemiesState(emptySet, DAMAGE);
        s.execute();
        assertTrue(s.executed());
    }

    @Test
    public void testNextReturnsNullIfNoNextStateIsPassed() {
        DamageEnemiesState s = new DamageEnemiesState(emptySet, DAMAGE);
        assertNull(s.next());
    }

    @Test
    public void testNextReturnsNullIfNullIsPassed() {
        DamageEnemiesState s = new DamageEnemiesState(emptySet, DAMAGE, null);
        assertNull(s.next());
    }

    @Test
    public void testNextReturnsTheNextStatePassedAsArgument() {
        SpecialAttackState ret = Mockito.mock(SpecialAttackState.class);
        DamageEnemiesState s = new DamageEnemiesState(emptySet, DAMAGE, ret);
        assertEquals(ret, s.next());
    }


}
