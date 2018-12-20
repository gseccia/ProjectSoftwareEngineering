package managers.observers;

/**
 *  Provides an interface for attaching and detaching Observer objects.
 *  
 *  @author Ilaria
 */
public interface SubjectInterface {
	
	public void attach(Observer observer);
	public void detach(Observer observer);
	public void notifyAllObservers();
	public int getState();
	public void setState(int state);
}
