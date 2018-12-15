package managers.observers.scoreboard;

public abstract class Observer implements ObserverInterface{
   protected Subject subject;
   public abstract void update();
}
