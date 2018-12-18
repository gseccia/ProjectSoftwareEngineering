package Unit.src.attack;

import attacks.PointBlankRangeAttack;
import elements.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;

public class PointBlankRangeAttackTest {

    private final static float POS_X = 0;
    private final static float POS_Y = 0;
    private final static float HEIGHT = 10;
    private final static float WIDTH = 10;

    @Mock
    private Player mockCaster;

    @Before
    public void setUp(){
        mockCaster = Mockito.mock(Player.class);
        doAnswer((i) -> POS_X).when(mockCaster).getX();
        doAnswer((i) -> POS_Y).when(mockCaster).getY();
        doAnswer((i) -> WIDTH).when(mockCaster).getWidth();
        doAnswer((i) -> HEIGHT).when(mockCaster).getHeight();
    }

    @Test
    public void testAttackSetPosXCorrectly() {
        PointBlankRangeAttack a = new PointBlankRangeAttack(mockCaster);
        a.setHitbox();
        assertEquals(POS_X, a.getX(), 1);
    }

    @Test
    public void testAttackSetPosYCorrectly() {
        PointBlankRangeAttack a = new PointBlankRangeAttack(mockCaster);
        a.setHitbox();
        assertEquals(POS_Y, a.getY(), 1);
    }

    @Test
    public void testAttackSetHeightCorrectly() {
        PointBlankRangeAttack a = new PointBlankRangeAttack(mockCaster);
        a.setHitbox();
        assertEquals(HEIGHT, a.getHeight(), 1);
    }

    @Test
    public void testAttackSetWidthCorrectly() {
        PointBlankRangeAttack a = new PointBlankRangeAttack(mockCaster);
        a.setHitbox();
        assertEquals(WIDTH, a.getWidth(), 1);
    }

    @Test(expected = NullPointerException.class)
    public void testAttackThrowsExceptionIfCasterIsNull() {
        PointBlankRangeAttack a = new PointBlankRangeAttack(null);
        a.setHitbox();
    }


}
