package Unit.src.attack.states;

import attacks.states.DrawIntroState;
import attacks.states.SpecialAttackState;
import elements.Intro;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Sound;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.doAnswer;

public class DrawIntroStateTest {

    private final float INITIAL_X = 0;
    private final float INITIAL_Y = 0;
    private final float FINAL_X = 0;
    private final float FINAl_Y = 0;

    @Mock private Sound mockSound;
    @Mock private Intro mockIntro;

    private float introX = INITIAL_X, introY = INITIAL_Y;
    private boolean playing, finished;

    @Before
    public void setUp(){
        mockSound = Mockito.mock(Sound.class);
        doAnswer((i) -> {
            new Thread((Runnable) () -> {
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

        mockIntro = Mockito.mock(Intro.class);
        doAnswer((i) -> {
            playing = (boolean)i.getArgument(0);
            return null;
        }).when(mockIntro).setPlaying(anyBoolean());
        doAnswer((i) -> {
            introX = (float)i.getArgument(0);
            introY = (float)i.getArgument(1);
            return null;
        }).when(mockIntro).setLocation(anyFloat(), anyFloat());
    }

    @Test
    public void testIntroIsNotPlayingBeforeExecute() {
        playing = false;
        DrawIntroState s = new DrawIntroState(mockIntro, mockSound, FINAL_X, FINAl_Y, null);
        assertFalse(playing);
    }

    @Test
    public void testIntroIsPlayingAfterExecute() {
        playing = false;
        DrawIntroState s = new DrawIntroState(mockIntro, mockSound, FINAL_X, FINAl_Y, null);
        s.execute();
        assertTrue(playing);
    }

    @Test
    public void testIntroIsNotPlayingAfterNext() {
        playing = false;
        DrawIntroState s = new DrawIntroState(mockIntro, mockSound, FINAL_X, FINAl_Y, null);
        s.execute();
        s.next();
        assertFalse(playing);
    }

    @Test
    public void testIntroPositionIsNotSetBeforeExecute() {
        DrawIntroState s = new DrawIntroState(mockIntro, mockSound, FINAL_X, FINAl_Y,null);
        assertEquals(INITIAL_X, introX, 1);
        assertEquals(INITIAL_Y, introY, 1);
    }

    @Test
    public void testIntroPositionIsSetAfterExecute() {
        DrawIntroState s = new DrawIntroState(mockIntro, mockSound, FINAL_X, FINAl_Y, null);
        s.execute();
        assertEquals(FINAL_X, introX, 1);
        assertEquals(FINAl_Y, introY, 1);
    }

    @Test
    public void testStateIsNotExecutedBeforeExecute() {
        DrawIntroState s = new DrawIntroState(mockIntro, mockSound, FINAL_X, FINAl_Y, null);
        assertFalse(s.executed());
    }

    @Test
    public void testStateIsExecutedAfterExecute() {
        DrawIntroState s = new DrawIntroState(mockIntro, mockSound, FINAL_X, FINAl_Y, null);
        s.execute();
        assertTrue(s.executed());
    }

    @Test
    public void testStateIsNotFinishedBeforeExecute() {
        DrawIntroState s = new DrawIntroState(mockIntro, mockSound, FINAL_X, FINAl_Y, null);
        assertFalse(s.finished());
    }

    @Test
    public void testStateIsFinishedImmediatelyAfterExecuteFinished() {
        DrawIntroState s = new DrawIntroState(mockIntro, mockSound, FINAL_X, FINAl_Y, null);
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
        DrawIntroState s = new DrawIntroState(mockIntro, mockSound, FINAL_X, FINAl_Y, null);
        assertNull(s.next());
    }

    @Test
    public void testNextReturnsTheNextStatePassedAsArgument() {
        SpecialAttackState ret = Mockito.mock(SpecialAttackState.class);
        DrawIntroState s = new DrawIntroState(mockIntro, mockSound, FINAL_X, FINAl_Y, ret);
        assertEquals(ret, s.next());
    }


}
