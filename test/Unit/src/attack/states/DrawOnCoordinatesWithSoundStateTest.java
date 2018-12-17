package Unit.src.attack.states;

import attacks.states.DrawOnCoordinatesWithSoundState;
import attacks.states.SpecialAttackState;
import attacks.ultras.SpecialAttack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Sound;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.doAnswer;

public class DrawOnCoordinatesWithSoundStateTest {

    private final float INITIAL_X = 0;
    private final float INITIAL_Y = 0;
    private final float FINAL_X = 10;
    private final float FINAl_Y = 10;

    @Mock private SpecialAttack mockAttack;
    @Mock private Sound mockSound;

    private boolean drawable, finished;
    private float introX = INITIAL_X, introY = INITIAL_Y;

    @Before
    public void setUp(){
        mockAttack = Mockito.mock(SpecialAttack.class);
        doAnswer((i) -> {
            drawable = (boolean)i.getArgument(0);
            return null;
        }).when(mockAttack).setDrawable(anyBoolean());
        doAnswer((i) -> {
            introX = (float)i.getArgument(0);
            introY = (float)i.getArgument(1);
            return null;
        }).when(mockAttack).setLocation(anyFloat(), anyFloat());

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
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, INITIAL_X, INITIAL_Y, null);
        assertFalse(drawable);
    }

    @Test
    public void testAttackIsDrawableAfterExecute() {
        drawable = false;
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, INITIAL_X, INITIAL_Y, null);
        s.execute();
        assertTrue(drawable);
    }

    @Test
    public void testAttackIsNotDrawableAfterNext() {
        drawable = false;
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, INITIAL_X, INITIAL_Y, null);
        s.execute();
        s.next();
        assertFalse(drawable);
    }

    @Test
    public void testAttackPositionIsNotSetBeforeExecute() {
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, FINAL_X, FINAl_Y, null);
        assertEquals(INITIAL_X, introX, 1);
        assertEquals(INITIAL_Y, introY, 1);
    }

    @Test
    public void testAttackPositionIsSetAfterExecute() {
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, FINAL_X, FINAl_Y, null);
        s.execute();
        assertEquals(FINAL_X, introX, 1);
        assertEquals(FINAl_Y, introY, 1);
    }

    @Test
    public void testStateIsNotExecutedBeforeExecute() {
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, INITIAL_X, INITIAL_Y, null);
        assertFalse(s.executed());
    }

    @Test
    public void testStateIsExecutedAfterExecute() {
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, INITIAL_X, INITIAL_Y, null);
        s.execute();
        assertTrue(s.executed());
    }

    @Test
    public void testStateIsNotFinishedBeforeExecute() {
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, INITIAL_X, INITIAL_Y, null);
        assertFalse(s.finished());
    }

    @Test
    public void testStateIsNotFinishedImmediatelyAfterExecute() {
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, INITIAL_X, INITIAL_Y, null);
        s.execute();
        assertFalse(s.finished());
    }

    @Test
    public void testStateIsFinishedImmediatelyAfterExecuteFinished() {
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, INITIAL_X, INITIAL_Y, null);
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
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, INITIAL_X, INITIAL_Y, null);
        assertNull(s.next());
    }

    @Test
    public void testNextReturnsTheNextStatePassedAsArgument() {
        SpecialAttackState ret = Mockito.mock(SpecialAttackState.class);
        DrawOnCoordinatesWithSoundState s = new DrawOnCoordinatesWithSoundState(mockAttack, mockSound, INITIAL_X, INITIAL_Y, ret);
        assertEquals(ret, s.next());
    }

}
