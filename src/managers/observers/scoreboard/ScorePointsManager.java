package managers.observers.scoreboard;

import managers.observers.Subject;
import managers.observers.Observer;

public class ScorePointsManager extends Subject{
	private static ScorePointsManager instance = null;
	
	private ScorePointsManager() {
	}
	
	public static ScorePointsManager getScorePointsManagerInstance() {
		if (instance == null) {
			instance = new ScorePointsManager();
		}
		return instance;
	}
		
	public void setState(int state) {
		/* Salva lo stato corrente e notifica il cambiamento agli observer
		* Lo stato corrisponde a:
		*	0 punti
		*	1 vita
		*	2 rip update scoreboard
		*/
		
		this.state = state;
		notifyAllObservers();
	}

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
}
