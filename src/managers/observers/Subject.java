package managers.observers;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject implements SubjectInterface{
	
   protected List<Observer> observers = new ArrayList<Observer>();
   protected int state;

   public void attach(Observer observer) {
      this.observers.add(observer);		
   }
   
   public void detach(Observer observer) {
	   this.observers.remove(observer);
   }

   public void notifyAllObservers(){
      for (Observer observer : this.observers) {
         observer.update();
      }
   }
   
   // getters and setters   
   
   public int getState() {
	   return state;
   }

   public void setState(int state) {
	   this.state = state;
	   this.notifyAllObservers();
   }

	public List<Observer> getObservers() {
		return observers;
	}

}