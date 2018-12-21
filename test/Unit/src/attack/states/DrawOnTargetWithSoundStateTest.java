package Unit.src.attack.states;

import attacks.states.DrawOnTargetWithSoundState;
import attacks.states.SpecialAttackState;
import attacks.ultras.SpecialAttack;
import elements.Enemy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Sound;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.doAnswer;

public class DrawOnTargetWithSoundStateTest {

    private final float INITIAL_X = 0;
    private final float INITIAL_Y = 0;
    private final float POS_X = 10;
    private final float POS_Y = 10;
    private final int SHIFT_X = 20;
    private final int SHIFT_Y = 20;
    private final int SIZE = 16;
    private final int BIAS = 56;

    @Mock private SpecialAttack mockAttack;
    @Mock private Sound mockSound;
    @Mock private Enemy mockEnemy;

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

        mockEnemy = Mockito.mock(Enemy.class);
        Mockito.when(mockEnemy.getX()).thenReturn(POS_X);
        Mockito.when(mockEnemy.getY()).thenReturn(POS_Y);

    }

    @Test
    public void testAttackIsNotDrawableBeforeExecute() {
        drawable = false;
        DrawOnTargetWithSoundState s = new DrawOnTargetWithSoundState(mockAttack, mockEnemy, SHIFT_X, SHIFT_Y, mockSound, null);
        assertFalse(drawable);
    }

    @Test
    public void testAttackIsDrawableAfterExecute() {
        drawable = false;
        DrawOnTargetWithSoundState s = new DrawOnTargetWithSoundState(mockAttack, mockEnemy, SHIFT_X, SHIFT_Y, mockSound, null);
        s.execute();
        assertTrue(drawable);
    }

    @Test
    public void testAttackIsNotDrawableAfterNext() {
        drawable = false;
        DrawOnTargetWithSoundState s = new DrawOnTargetWithSoundState(mockAttack, mockEnemy, SHIFT_X, SHIFT_Y, mockSound, null);
        s.execute();
        s.next();
        assertFalse(drawable);
    }

    @Test
    public void testAttackPositionIsNotSetBeforeExecute() {
        DrawOnTargetWithSoundState s = new DrawOnTargetWithSoundState(mockAttack, mockEnemy, SHIFT_X, SHIFT_Y, mockSound, null);
        assertEquals(INITIAL_X, introX, 1);
        assertEquals(INITIAL_Y, introY, 1);
    }

    @Test
    public void testAttackPositionIsSetAfterExecute() {
        DrawOnTargetWithSoundState s = new DrawOnTargetWithSoundState(mockAttack, mockEnemy, SHIFT_X, SHIFT_Y, mockSound, null);
        s.execute();
        assertEquals(POS_X - SHIFT_X*SIZE-BIAS, introX, 1);
        assertEquals(POS_Y - SHIFT_Y*SIZE, introY, 1);
    }

    @Test
    public void testStateIsNotExecutedBeforeExecute() {
        DrawOnTargetWithSoundState s = new DrawOnTargetWithSoundState(mockAttack, mockEnemy, SHIFT_X, SHIFT_Y, mockSound, null);
        assertFalse(s.executed());
    }

    @Test
    public void testStateIsExecutedAfterExecute() {
        DrawOnTargetWithSoundState s = new DrawOnTargetWithSoundState(mockAttack, mockEnemy, SHIFT_X, SHIFT_Y, mockSound, null);
        s.execute();
        assertTrue(s.executed());
    }

    @Test
    public void testStateIsNotFinishedBeforeExecute() {
        DrawOnTargetWithSoundState s = new DrawOnTargetWithSoundState(mockAttack, mockEnemy, SHIFT_X, SHIFT_Y, mockSound, null);
        assertFalse(s.finished());
    }


    @Test
    public void testStateIsFinishedImmediatelyAfterExecuteFinished() {
        DrawOnTargetWithSoundState s = new DrawOnTargetWithSoundState(mockAttack, mockEnemy, SHIFT_X, SHIFT_Y, mockSound, null);
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
        DrawOnTargetWithSoundState s = new DrawOnTargetWithSoundState(mockAttack, mockEnemy, SHIFT_X, SHIFT_Y, mockSound, null);
        assertNull(s.next());
    }

    @Test
    public void testNextReturnsTheNextStatePassedAsArgument() {
        SpecialAttackState ret = Mockito.mock(SpecialAttackState.class);
        DrawOnTargetWithSoundState s = new DrawOnTargetWithSoundState(mockAttack, mockEnemy, SHIFT_X, SHIFT_Y, mockSound, ret);
        assertEquals(ret, s.next());
    }
}
