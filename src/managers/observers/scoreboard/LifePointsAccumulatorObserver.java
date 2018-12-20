package managers.observers.scoreboard;

import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Item;
import elements.NullAnimationException;
import managers.observers.Observer;
import managers.observers.Subject;

/**
 * Concrete Observer
 * Maintains a reference to ScorePointsManager as a concrete subject
 * Store state consistently with the one of the subject, that is life points
 * updates during the game step by step.
 * Observer fits like a glove because allows to detach objects different in behavior
 * but dependent in the logic, thus the other objects do not need to know who the 
 * notified object is so that they are not coupled.
 * 
 * @author Ilaria
 */
public class LifePointsAccumulatorObserver extends Observer{
	private int hp = 100;
	ItemConfiguration lifeConf = ItemConfiguration.getInstance();
	private ScorePointsManager subject;
	Item heart;
	
	public LifePointsAccumulatorObserver(Subject s) {
		this.subject = (ScorePointsManager)s;
		this.subject.attach(this);
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp += hp;
		if (this.hp > 100) this.hp = 100;
		else if(this.hp < 0) this.hp = 0;
	}
	
	public void resetHp() {
		this.hp = 100;
	}
	
	private int getNumberOfHearts() {
		if (this.hp > 80) {
			return 5;
		}
		else if (this.hp > 60) {
			return 4;
		}
		else if (this.hp > 40) {
			return 3;
		}
		else if (this.hp > 20) {
			return 2;
		}
		else if (this.hp > 0) {
			return 1;
		}
		else if (this.hp <= 0) {
			return 0;
		}
		else {
			return -1;
		}
	}
	
	public void renderHearts(Graphics g, int width, int height) {

		int initialWidthHearts = width - 90;
		try {
			heart = new Item(lifeConf, "heart");
		} catch (NullAnimationException | SlickException | NoSuchElementInConfigurationException e) {
			e.printStackTrace();
		}
		for (int i=0; i < this.getNumberOfHearts(); i++) {
			heart.draw(initialWidthHearts+18*i, height);
		}
		
	}

	@Override
	public void update() {
		if (this.subject.getState() == States.LifePointsAccumulator) {
			setHp(((ScorePointsManager)this.subject).getIncreaseValue() - ((ScorePointsManager)this.subject).getDecreaseValue());
		}
	}

}
