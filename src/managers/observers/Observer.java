package managers.observers;

/**
 * Defines an updating abstract class for objects that should be notified of changes in a subject.
 * 
 * @author Ilaria
 */
public abstract class Observer implements ObserverInterface{
   protected Subject subject;
   public abstract void update();
}
