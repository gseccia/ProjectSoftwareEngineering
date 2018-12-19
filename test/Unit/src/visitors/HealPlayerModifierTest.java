package Unit.src.visitors;

import elements.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import visitors.HealPlayerModifier;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;

public class HealPlayerModifierTest {

    private final int HEAL = 20;
    private final int LIFE = 100;

    @Mock
    private Player mockPlayer;

    private HealPlayerModifier visitor;
    private int life = LIFE;

    @Before
    public void setUp() {
        visitor = new HealPlayerModifier(HEAL);

        mockPlayer = Mockito.mock(Player.class);
        doAnswer((i) -> {
            life += (int)i.getArgument(0);
            return null;
        }).when(mockPlayer).heal(anyInt());
    }

    @Test
    public void testHealingIsCalculatedCorrectly(){
        visitor.accept(mockPlayer);
        assertEquals(LIFE+HEAL, life);
    }

    @Test(expected = NullPointerException.class)
    public void testVisitorThrowsExceptionIfPlayerIsNull(){
        visitor.accept(null);
    }
}
