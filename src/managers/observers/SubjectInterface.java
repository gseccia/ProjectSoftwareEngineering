package managers.observers;

public interface SubjectInterface {
	
	public void attach(Observer observer);
	public void detach(Observer observer);
	public void notifyAllObservers();
	public int getState();
	public void setState(int state);
}
