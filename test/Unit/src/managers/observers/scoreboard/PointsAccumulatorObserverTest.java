package Unit.src.managers.observers.scoreboard;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;

//import org.junit.jupiter.api.Test;

import managers.observers.scoreboard.PointsAccumulatorObserver;
import managers.observers.scoreboard.ScorePointsManager;
import org.junit.Test;

public class PointsAccumulatorObserverTest {
	// Subjects
	private ScorePointsManager pm;
	// Observer
	private PointsAccumulatorObserver pao;
	
	@Before
	public void setUP() {
		pm = ScorePointsManager.getScorePointsManagerInstance();
		pao = PointsAccumulatorObserver.getInstance(pm);
	}
	
	@Test
	public void createPointsAccumulatorObserverTestPointsInit() {
		pao.setPoints(-pao.getPoints());		
//		If the PointsAccumulatorObserver is correctly initialized, sets points to zero
		assertEquals(pao.getPoints(), 0);
	}
	
	@Test
	public void testSetPoints() {
		pao.setPoints(-pao.getPoints());
		pao.setPoints(42);
		assertEquals(pao.getPoints(), 42);
	}
	
	@Test
	public void testGetNameFromCurrentSubject() {
		pm.setNamePlayer("Armando");
		assertEquals("Armando", pao.getName());
	}
	@Test
	public void updateTest() {
		pao.setPoints(-pao.getPoints());
//		State associated is 0
		pm.increase(42);
		pm.decrease(0);
		pm.setState(0);
		assertEquals(42, pm.getPointsAccumulatorObserver().getPoints());
	}
}
