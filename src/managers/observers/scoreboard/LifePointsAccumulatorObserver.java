package managers.observers.scoreboard;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Item;
import elements.NullAnimationException;
import managers.observers.Observer;
import managers.observers.Subject;

public class LifePointsAccumulatorObserver extends Observer{
	private int hp = 100;
	ItemConfiguration lifeConf = ItemConfiguration.getInstance();
	Item heart;
	
	public LifePointsAccumulatorObserver(Subject s) {
		this.subject = s;
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
		// Questo metodo e' richiamato dal PointsManager per cambiamento di stato
		// Lo stato cambia quando ci sono collisioni con nemici/item/muri
		// e aggiorna il livello di hp
		if (this.subject.getState() == 1) {
			setHp(this.subject.getIncreaseValue() - this.subject.getDecreaseValue());
		}
	}

}
