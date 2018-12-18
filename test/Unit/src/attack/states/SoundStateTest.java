package Unit.src.attack.states;

import attacks.states.SoundState;
import attacks.states.SpecialAttackState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Sound;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;

public class SoundStateTest {

    @Mock private Sound mockSound;

    private boolean drawable, finished;


    @Before
    public void setUp(){
        mockSound = Mockito.mock(Sound.class);
        doAnswer((i) -> {
            new Thread(() -> {
                try {
                    finished = true;
                    Thread.sleep(1000);
                    finished = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            return null;
        }).when(mockSound).play();
        doAnswer((i) -> finished).when(mockSound).playing();

    }

    @Test
    public void testStateIsNotExecutedBeforeExecute() {
        SoundState s = new SoundState(mockSound, null);
        assertFalse(s.executed());
    }

    @Test
    public void testStateIsExecutedAfterExecute() {
        SoundState s = new SoundState(mockSound, null);
        s.execute();
        assertTrue(s.executed());
    }

    @Test
    public void testStateIsNotFinishedBeforeExecute() {
        SoundState s = new SoundState(mockSound, null);
        assertFalse(s.finished());
    }

    @Test
    public void testStateIsNotFinishedImmediatelyAfterExecute() {
        SoundState s = new SoundState(mockSound, null);
        s.execute();
        assertFalse(s.finished());
    }

    @Test
    public void testStateIsFinishedImmediatelyAfterExecuteFinished() {
        SoundState s = new SoundState(mockSound, null);
        s.execute();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(s.finished());
    }

    @Test
    public void testNextReturnsNullIfNullIsPassed() {
        SoundState s = new SoundState(mockSound, null);
        assertNull(s.next());
    }

    @Test
    public void testNextReturnsTheNextStatePassedAsArgument() {
        SpecialAttackState ret = Mockito.mock(SpecialAttackState.class);
        SoundState s = new SoundState(mockSound, ret);
        assertEquals(ret, s.next());
    }
}
