package Unit.src.managers.observers.scoreboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import managers.observers.scoreboard.PointsAccumulatorObserver;
import managers.observers.scoreboard.Score;
import managers.observers.scoreboard.ScoreFileObserver;
import managers.observers.scoreboard.ScorePointsManager;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/*
 * PLEASE CAUTION: DELETE scoreboard.log BEFORE TESTING
 */
public class ScoreFileObserverTest {
	
	// Subjects
	private ScorePointsManager pm = ScorePointsManager.getScorePointsManagerInstance();
	// Observer
	private ScoreFileObserver sfo = new ScoreFileObserver(pm);
	private PointsAccumulatorObserver pao = new PointsAccumulatorObserver(pm);
		
	@Test
	/*
	 * When the observer is initiated with no previous saved file of scores, it keeps an empty scoreboard
	 */
	public void testA() {
		assertEquals(sfo.getScores().size(), 0);
	}
	
	@Test
	/*
	 * Checks if the getter method returns the correct variable instance
	 */
	public void testB() {
		assertEquals(sfo.getScores().getClass(), ArrayList.class);
	}
	
	
	@Test
	/*
	 * Checks if the observer is correctly notified and adds a score to the scoreboard
	 */
	public void testC() {
		pao.setPoints(99);
		pm.setNamePlayer("Armando");
		pm.setState(2);
		assertTrue(sfo.contains(99, "Armando"));
	}
	
	@Test
	/*
	 * Checks if the observer maintains only the 5 best scores, deleting the worst in case of a new better one
	 */
	public void testD() {
		pao.setPoints(89);
		pm.setNamePlayer("Armandino");
		pm.setState(2);
		pao.setPoints(-89);
		pao.setPoints(109);
		pm.setNamePlayer("Armandone");
		pm.setState(2);
		pao.setPoints(-109);
		pao.setPoints(90);
		pm.setNamePlayer("Armandello");
		pm.setState(2);
		pao.setPoints(-90);
		pao.setPoints(129);
		pm.setNamePlayer("Armanduccio");
		pm.setState(2);
		pao.setPoints(-129);
		pao.setPoints(1000);
		pm.setNamePlayer("Armando Potente");
		pm.setState(2);
		pao.setPoints(-1000);
		pao.setPoints(103);
		pm.setNamePlayer("Armandinosaur");
		pm.setState(2);
		assertTrue(!(sfo.contains(89, "Armandino")) && sfo.getScores().size() == 5);
	}
	
	@Test
	/*
	 * Checks if the observer maintains only the 5 best scores, not allowing the insertion of a lower score than the last saved
	 */
	public void testE() {
		pao.setPoints(1);
		pm.setNamePlayer("Armando Buuuu");
		pm.setState(2);
		assertTrue(!(sfo.contains(1, "Armando Buuuu")) && sfo.getScores().size() == 5);
	}
	
	@Test
	/*
	 * Checks if the observer correctly reads a previous saved file
	 */
	public void testF() {
		ScoreFileObserver sfo1 = new ScoreFileObserver(pm);
		assertTrue(sfo1.contains(99, "Armando") &&
				sfo1.contains(103, "Armandinosaur") &&
				sfo1.contains(109, "Armandone") &&
				sfo1.contains(129, "Armanduccio") &&
				sfo1.contains(1000, "Armando Potente") &&
				sfo1.getScores().size() == 5);
	}
	
	@Test
	/*
	 * Checks if the scoreboard is descendingly ordered
	 */
	public void testG() {
		int prev = Integer.MAX_VALUE;
		boolean order = true;
		for(Score s : sfo.getScores()) {
			if(!(s.getValue() <= prev)) {
				order = false;
				break;
			}
		}
		assertTrue(order);
	}

}
