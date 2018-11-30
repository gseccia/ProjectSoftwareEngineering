package managers.observers.scoreboard;


public class LifePointsAccumulatorObserver extends Observer{
	private int hp = 100;
	
	public LifePointsAccumulatorObserver(Subject s) {
		this.subject = s;
		this.subject.attach(this);
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp += hp;
	}

	@Override
	public void update() {
		// Questo metodo è richiamato dal PointsManager per cambiamento di stato
		// Lo stato cambia quando ci sono collisioni con nemici/item/muri
		// e aggiorna il livello di hp
		if (this.subject.getState() == 1) {
			setHp(this.subject.getIncreaseValue() - this.subject.getDecreaseValue());
		}
	}

}
