package Unit.src.managers.observers.scoreboard;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

//import org.junit.jupiter.api.Test;

import managers.observers.scoreboard.LifePointsAccumulatorObserver;
import managers.observers.scoreboard.ScorePointsManager;
import org.junit.Test;

public class LifePointsAccumulatorObserverTest {
	private ScorePointsManager pm;
	private LifePointsAccumulatorObserver lpao;
	@Before
	public void setUp() {
		// Subjects
		pm = ScorePointsManager.getScorePointsManagerInstance();
		pm.detach(pm.getLifePointsAccumulatorObserver());
		// Observer
		lpao = new LifePointsAccumulatorObserver(pm);
	}
	
	@Test
	public void LifePointsAccumulatorObserverTestPointsInit() {
//			If the LifePointsAccumulatorObserverTestPointsInit is correctly initialized, sets life to 100
		assertEquals(100, lpao.getHp());
	}
	
	@Test
	public void testSetHp() {
		int prevValue = lpao.getHp();
		lpao.setHp(-2);
		assertEquals(prevValue-2, lpao.getHp());
	}
	
	@Test
	public void testResetHp() {
		lpao.resetHp();
		assertEquals(100, lpao.getHp());
	}
	
//	TODO manca da testare il metodo renderHearts()
	
	@Test
	public void updateTest() {
		lpao.resetHp();
//			State associated is 1
		int prevValue = pm.getLifePointsAccumulatorObserver().getHp();
		pm.increase(0);
		pm.decrease(1);
		pm.setState(1);
		assertEquals(prevValue-1, pm.getLifePointsAccumulatorObserver().getHp());
	}
}
