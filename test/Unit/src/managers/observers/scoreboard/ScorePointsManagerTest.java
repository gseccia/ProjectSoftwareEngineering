package Unit.src.managers.observers.scoreboard;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import managers.observers.scoreboard.LifePointsAccumulatorObserver;
import managers.observers.scoreboard.PointsAccumulatorObserver;
import managers.observers.scoreboard.ScorePointsManager;
import managers.observers.scoreboard.ScoreFileObserver;

public class ScorePointsManagerTest {
	// Subjects
	private ScorePointsManager pm = ScorePointsManager.getScorePointsManagerInstance();
	// Observers
	private PointsAccumulatorObserver pao = new PointsAccumulatorObserver(pm);
	private LifePointsAccumulatorObserver lpao = new LifePointsAccumulatorObserver(pm);
	private ScoreFileObserver sfo = new ScoreFileObserver(pm);
	
	@Test
	public void creationLoggerFileTest() {
		System.out.println("Testing PointsManager");
//		pm.attach(pao);
//		pm.attach(sfo);
		
		pm.saveNamePlayer("Armando");
		pm.increase(43);
		pm.decrease(1);
		pm.setState(0);
		
		pm.increase(0);
		pm.decrease(20);
		pm.setState(1);
		
		pm.setState(2);
		System.out.println("Points total: " + pao.getPoints());
		System.out.println("Hp total: " + lpao.getHp());
		System.out.println("Mappa di prove" + sfo.getScores());
	}
}
