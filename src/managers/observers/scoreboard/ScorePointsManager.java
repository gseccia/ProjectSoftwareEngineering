package managers.observers.scoreboard;

import managers.observers.Subject;
import managers.observers.Observer;

/**
 * Concrete Subject
 * Singleton because one and only one instance of a manager of the score must exist in the game.
 * Stores state of interest to ConcreteObserver objects and sends a notification to its observers 
 * when its state changes.
 * The subject fulfills the need of a manager for the logic of points accumulation.
 * Provides utility method to exchange data with the observer supplied by external clients.
 * 
 * @author Ilaria
 */
public class ScorePointsManager extends Subject{
	private static ScorePointsManager instance = null;
	private int increaseValue, decreaseValue;
	private String namePlayer;
	
	private ScorePointsManager() {
	}
	
	public static ScorePointsManager getScorePointsManagerInstance() {
		if (instance == null) {
			instance = new ScorePointsManager();
		}
		return instance;
	}

	/*
	 * States are
	 *	0 points
	 *	1 hp
	 *	2 rip update scoreboard
	 */

	public void increase(int v) {
		setIncreaseValue(v);
	}
	
	public void decrease(int v) {
		setDecreaseValue(v);
	}
	
	public void saveNamePlayer(String name) {
		setNamePlayer(name);
	}
	
	public LifePointsAccumulatorObserver getLifePointsAccumulatorObserver() {
	   for (Observer e : observers) {
		   if (e.getClass() == LifePointsAccumulatorObserver.class) {
			   return (LifePointsAccumulatorObserver) e;
		   }
	   }
	return null;
	}
	
	public PointsAccumulatorObserver getPointsAccumulatorObserver() {
	   for (Observer e : observers) {
		   if (e.getClass() == PointsAccumulatorObserver.class) {
			   return (PointsAccumulatorObserver) e;
		   }
	   }
	return null;
	}
	

	private void setIncreaseValue(int increaseValue) {
	this.increaseValue = increaseValue;
}

	private void setDecreaseValue(int decreaseValue) {
		this.decreaseValue = decreaseValue;
	}

	public int getIncreaseValue() {
		return increaseValue;
	}

	public int getDecreaseValue() {
		return decreaseValue;
	}
	
	public String getNamePlayer() {
		return namePlayer;
	}

	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}
}
