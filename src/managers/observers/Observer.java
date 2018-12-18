package managers.observers;

public abstract class Observer implements ObserverInterface{
   protected Subject subject;
   public abstract void update();
}
