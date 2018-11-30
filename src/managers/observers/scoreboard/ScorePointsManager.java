package managers.observers.scoreboard;

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
		*	
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
}
