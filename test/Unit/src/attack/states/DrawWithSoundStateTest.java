package Unit.src.attack.states;

import attacks.states.DrawWithSoundState;
import attacks.states.SpecialAttackState;
import attacks.ultras.SpecialAttack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Sound;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doAnswer;

public class DrawWithSoundStateTest {

    @Mock private SpecialAttack mockAttack;
    @Mock private Sound mockSound;

    private boolean drawable, finished;


    @Before
    public void setUp(){
        mockAttack = Mockito.mock(SpecialAttack.class);
        doAnswer((i) -> {
            drawable = (boolean)i.getArgument(0);
            return null;
        }).when(mockAttack).setDrawable(anyBoolean());

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
    public void testAttackIsNotDrawableBeforeExecute() {
        drawable = false;
        DrawWithSoundState s = new DrawWithSoundState(mockAttack, mockSound, null);
        assertFalse(drawable);
    }

    @Test
    public void testAttackIsDrawableAfterExecute() {
        drawable = false;
        DrawWithSoundState s = new DrawWithSoundState(mockAttack, mockSound, null);
        s.execute();
        assertTrue(drawable);
    }

    @Test
    public void testAttackIsNotDrawableAfterNext() {
        drawable = false;
        DrawWithSoundState s = new DrawWithSoundState(mockAttack, mockSound, null);
        s.execute();
        s.next();
        assertFalse(drawable);
    }

    @Test
    public void testStateIsNotExecutedBeforeExecute() {
        DrawWithSoundState s = new DrawWithSoundState(mockAttack, mockSound, null);
        assertFalse(s.executed());
    }

    @Test
    public void testStateIsExecutedAfterExecute() {
        DrawWithSoundState s = new DrawWithSoundState(mockAttack, mockSound, null);
        s.execute();
        assertTrue(s.executed());
    }

    @Test
    public void testStateIsNotFinishedBeforeExecute() {
        DrawWithSoundState s = new DrawWithSoundState(mockAttack, mockSound, null);
        assertFalse(s.finished());
    }

    @Test
    public void testStateIsNotFinishedImmediatelyAfterExecute() {
        DrawWithSoundState s = new DrawWithSoundState(mockAttack, mockSound, null);
        s.execute();
        assertFalse(s.finished());
    }

    @Test
    public void testStateIsFinishedImmediatelyAfterExecuteFinished() {
        DrawWithSoundState s = new DrawWithSoundState(mockAttack, mockSound, null);
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
        DrawWithSoundState s = new DrawWithSoundState(mockAttack, mockSound, null);
        assertNull(s.next());
    }

    @Test
    public void testNextReturnsTheNextStatePassedAsArgument() {
        SpecialAttackState ret = Mockito.mock(SpecialAttackState.class);
        DrawWithSoundState s = new DrawWithSoundState(mockAttack, mockSound, ret);
        assertEquals(ret, s.next());
    }
}
