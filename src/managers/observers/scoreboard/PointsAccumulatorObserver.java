package managers.observers.scoreboard;

import managers.observers.Observer;
import managers.observers.Subject;

public class PointsAccumulatorObserver extends Observer{
	private int points;
	
	public PointsAccumulatorObserver(Subject subject) {
      this.subject = subject;
      this.subject.attach(this);
      this.points = 0;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points += points;
	}
	
	public String getName() {
		return this.subject.getNamePlayer();
	}

	@Override
	public void update() {
		// Questo metodo e' richiamato dal PointsManager per cambiamento di stato
		// Lo stato cambia quando ci sono collisioni con nemici/item/muri
		// e aggiorna il punteggio accordingly
		if (this.subject.getState() == 0) {
			setPoints(this.subject.getIncreaseValue() 
					- this.subject.getDecreaseValue()
					);
		}
	}

}
