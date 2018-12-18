package Unit.src.managers.observers.scoreboard;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import managers.observers.scoreboard.LifePointsAccumulatorObserver;
import managers.observers.scoreboard.PointsAccumulatorObserver;
import managers.observers.scoreboard.ScorePointsManager;
//import managers.observers.scoreboard.ScoreFileObserver;

public class ScorePointsManagerTest {
	// Subjects
	private ScorePointsManager pm = ScorePointsManager.getScorePointsManagerInstance();
	// Observers
	private PointsAccumulatorObserver pao = PointsAccumulatorObserver.getInstance(pm);
	private LifePointsAccumulatorObserver lpao = new LifePointsAccumulatorObserver(pm);
//	private ScoreFileObserver sfo = new ScoreFileObserver(pm);
	
	@Test
	public void createScorePointManagerSingletonTest() {
		ScorePointsManager anotherInstanceOfScorePointsManager = ScorePointsManager.getScorePointsManagerInstance();
		assertEquals(pm, anotherInstanceOfScorePointsManager);
	}
	
	@Test
	public void setStateTest() {
		pm.setState(0);
		assertEquals(pm.getState(), 0);
	}
	
	@Test
	public void saveNamePlayerTest() {
		pm.saveNamePlayer("Armando");
		assertEquals(pm.getNamePlayer(), "Armando");
	}
	
	@Test
	public void increasePointsTest() {
		pm.increase(42);
		assertEquals(42, pm.getIncreaseValue());
	}
	
	@Test
	public void decreasePointsTest() {
		pm.decrease(42);
		assertEquals(42, pm.getDecreaseValue());
	}
	
	@Test
	public void getLifePointsAccumulatorObserverTest() {
		assertEquals(lpao.getClass(), pm.getLifePointsAccumulatorObserver().getClass());
	}
	
	@Test
	public void getPointsAccumulatorObserverTest() {
		assertEquals(pao.getClass(), pm.getPointsAccumulatorObserver().getClass());
	}
}
