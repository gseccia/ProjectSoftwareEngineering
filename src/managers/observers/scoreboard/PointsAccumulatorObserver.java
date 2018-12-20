package managers.observers.scoreboard;

import managers.observers.Observer;
import managers.observers.Subject;

/**
 * Concrete Observer
 * Maintains a reference to ScorePointsManager as a concrete subject
 * Store state consistently with the one of the subject, that is item and enemy points
 * updates during the game step by step.
 * Observer fits like a glove because allows to detach objects different in behavior
 * but dependent in the logic, thus the other objects do not need to know who the 
 * notified object is so that they are not coupled.
 * 
 * @author Ilaria
 */
public class PointsAccumulatorObserver extends Observer{
	private int points;
	private static PointsAccumulatorObserver instance;
	private ScorePointsManager subject;
	
	private PointsAccumulatorObserver(Subject subject) {
      this.subject = (ScorePointsManager) subject;
      this.subject.attach(this);
      this.points = 0;
	}
	
	public static PointsAccumulatorObserver getInstance(Subject s) {
		if (instance == null) instance = new PointsAccumulatorObserver(s);
		return instance;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points += points;
	}
	
	public String getName() {
		return ((ScorePointsManager)this.subject).getNamePlayer();
	}

	@Override
	public void update() {
		if (this.subject.getState() == States.PointsAccumulator) {
			setPoints(((ScorePointsManager)this.subject).getIncreaseValue() 
					- ((ScorePointsManager)this.subject).getDecreaseValue()
					);
		}
	}

}
