package Unit.src.managers.observers.scoreboard;

import static org.junit.Assert.assertEquals;

//import org.junit.jupiter.api.Test;

import managers.observers.scoreboard.LifePointsAccumulatorObserver;
import managers.observers.scoreboard.ScorePointsManager;
import org.junit.Test;

public class LifePointsAccumulatorObserverTest {

	// Subjects
	private ScorePointsManager pm = ScorePointsManager.getScorePointsManagerInstance();
	// Observer
	private LifePointsAccumulatorObserver lpao = new LifePointsAccumulatorObserver(pm);
	
	@Test
	public void LifePointsAccumulatorObserverTestPointsInit() {
//			If the PointsAccumulatorObserver is correctly initialized, sets points to zero
		assertEquals(lpao.getHp(), 100);
	}
	
	@Test
	public void testSetHp() {
		lpao.setHp(42);
		assertEquals(lpao.getHp(), 42);
	}
	
//	TODO manca da testare il metodo renderHearts()
	
	@Test
	public void updateTest() {
//			State associated is 1
		pm.increase(0);
		pm.decrease(42);
		pm.setState(1);
		assertEquals(84, pm.getLifePointsAccumulatorObserver().getHp());
	}
}
