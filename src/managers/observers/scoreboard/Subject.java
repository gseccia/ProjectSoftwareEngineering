package managers.observers.scoreboard;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
	
   protected List<Observer> observers = new ArrayList<Observer>();
   protected int state;
   private int increaseValue, decreaseValue;
   private String namePlayer;

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

	public void setIncreaseValue(int increaseValue) {
	this.increaseValue = increaseValue;
}

	public void setDecreaseValue(int decreaseValue) {
		this.decreaseValue = decreaseValue;
	}

	public int getIncreaseValue() {
		return increaseValue;
	}

	public int getDecreaseValue() {
		return decreaseValue;
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public String getNamePlayer() {
		return namePlayer;
	}

	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}
	
}