package managers.observers.scoreboard;

import java.util.ArrayList;
import java.util.List;

import elements.Item;

public class ItemAccumulatorObserver extends Observer{
	private List<Item> itemsList;
	
	
	public ItemAccumulatorObserver(Subject s) {
		this.subject = s;
		s.attach(this);
		this.itemsList = new ArrayList<>();
	}

	public List<Item> getItemsList() {
		return itemsList;
	}

	@Override
	public void update() {
		// Questo metodo e' richiamato dal PointsManager per cambiamento di stato
		// Lo stato cambia quando ci sono collisioni con nemici/item/muri
		// e aggiorna la lista di item
		
	}

}
