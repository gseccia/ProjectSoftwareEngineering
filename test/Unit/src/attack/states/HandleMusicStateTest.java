package Unit.src.attack.states;

import attacks.states.HandleMusicState;
import attacks.states.SpecialAttackState;
import managers.ResourceManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;

public class HandleMusicStateTest {

    private final int INITIAL_STATE = 0;
    private final int FINAL_STATE = 1;

    @Mock private ResourceManager mockManager;
    private int state = INITIAL_STATE;

    @Before
    public void setUp(){
        mockManager = Mockito.mock(ResourceManager.class);
        doAnswer((i) -> {
            state = (int)i.getArgument(0);
            return null;
        }).when(mockManager).setState(anyInt());
        doAnswer((i) -> state).when(mockManager).getState();
    }

    @Test
    public void testStateIsSetCorrectly() {
        HandleMusicState s = new HandleMusicState(mockManager, FINAL_STATE, null);
        s.execute();
        assertEquals(FINAL_STATE, state);
    }

    @Test
    public void testStateIsNotSetBeforeExecute() {
        HandleMusicState s = new HandleMusicState(mockManager, FINAL_STATE, null);
        assertEquals(INITIAL_STATE, state);
    }

    @Test(expected = NullPointerException.class)
    public void testExecuteThrowsExceptionIfManagerIsNull() {
        HandleMusicState s = new HandleMusicState(null, FINAL_STATE, null);
        s.execute();
    }

    @Test
    public void testStateIsNotFinishedBeforeExecute() {
        HandleMusicState s = new HandleMusicState(mockManager, FINAL_STATE, null);
        assertFalse(s.finished());
    }

    @Test
    public void testStateIsFinishedAfterExecute() {
        HandleMusicState s = new HandleMusicState(mockManager, FINAL_STATE, null);
        s.execute();
        assertTrue(s.finished());
    }

    @Test
    public void testStateIsNotExecutedBeforeExecute() {
        HandleMusicState s = new HandleMusicState(mockManager, FINAL_STATE, null);
        assertFalse(s.executed());
    }

    @Test
    public void testStateIsExecutedAfterExecute() {
        HandleMusicState s = new HandleMusicState(mockManager, FINAL_STATE, null);
        s.execute();
        assertTrue(s.executed());
    }

    @Test
    public void testNextReturnsNullIfNullIsPassed() {
        HandleMusicState s = new HandleMusicState(mockManager, FINAL_STATE, null);
        assertNull(s.next());
    }

    @Test
    public void testNextReturnsTheNextStatePassedAsArgument() {
        SpecialAttackState ret = Mockito.mock(SpecialAttackState.class);
        HandleMusicState s = new HandleMusicState(mockManager, FINAL_STATE, ret);
        assertEquals(ret, s.next());
    }
}
