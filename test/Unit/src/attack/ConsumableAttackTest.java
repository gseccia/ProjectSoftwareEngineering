package Unit.src.attack;

import attacks.Attack;
import attacks.ConsumableAttack;
import elements.NotPositiveValueException;
import elements.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;

public class ConsumableAttackTest {

    private final int USES = 3;
    private final int MULTIPLIER = 2;
    private final int BASE_ATTACK = 1;

    @Mock private Player mockCaster;
    @Mock private Attack mockStandard;
    @Mock private Attack mockSpecial;

    private int attack = BASE_ATTACK;

    @Before
    public void setUp() throws NotPositiveValueException {
        mockCaster = Mockito.mock(Player.class);
        doAnswer((i) -> {
            attack = (int)i.getArgument(0);
            return null;
        }).when(mockCaster).setAttackDamage(anyInt());
        doAnswer((i) -> attack).when(mockCaster).getAttackDamage();

        mockStandard = Mockito.mock(Attack.class);

        mockSpecial = Mockito.mock(Attack.class);
    }

    @Test
    public void testAttackIsMultipliedCorrectlyAfterUse() {
        ConsumableAttack a = new ConsumableAttack(mockCaster, mockSpecial, mockStandard, USES, MULTIPLIER);
        a.attack();
        assertEquals(BASE_ATTACK*MULTIPLIER, attack);
    }

    @Test
    public void testAttackReturnsToBaseAfterUse() {
        ConsumableAttack a = new ConsumableAttack(mockCaster, mockSpecial, mockStandard, USES, MULTIPLIER);
        a.attack();
        a.attack();
        a.attack();
        a.attack();
        assertEquals(BASE_ATTACK, attack);
    }

    @Test
    public void testAttackIsMultipliedBeforeLastUse() {
        ConsumableAttack a = new ConsumableAttack(mockCaster, mockSpecial, mockStandard, USES, MULTIPLIER);
        a.attack();
        a.attack();
        a.attack();
        assertEquals(BASE_ATTACK*MULTIPLIER, attack);
    }

    @Test
    public void testAttackIsBaseBeforeUse() {
        ConsumableAttack a = new ConsumableAttack(mockCaster, mockSpecial, mockStandard, USES, MULTIPLIER);
        assertEquals(BASE_ATTACK, attack);
    }

    @Test(expected = NullPointerException.class)
    public void raiseExceptionIfCasterIsNull() {
        ConsumableAttack a = new ConsumableAttack(null, mockSpecial, mockStandard, USES, MULTIPLIER);
        a.attack();
    }

    @Test(expected = NullPointerException.class)
    public void raiseExceptionIfSpecialIsNull() {
        ConsumableAttack a = new ConsumableAttack(mockCaster, null, mockStandard, USES, MULTIPLIER);
        a.attack();
    }

    @Test(expected = NullPointerException.class)
    public void raiseExceptionIfStandardIsNull() {
        ConsumableAttack a = new ConsumableAttack(mockCaster, null, mockStandard, USES, MULTIPLIER);
        a.attack();
    }

}
