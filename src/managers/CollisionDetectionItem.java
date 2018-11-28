package managers;

import elements.Item;
import elements.Mob;

public class CollisionDetectionItem extends CollisionDetectionStrategy {
	private String itemFound;
	
	public CollisionDetectionItem(HitboxMaker hitbox) {
		this.items = hitbox.getItems();
		this.map = hitbox.getMap();
	}
	
	public String getItem() {
		return itemFound;
	}
	
	@Override
	public boolean detectCollision(int shiftX, int shiftY, Mob player) {
		boolean collision = false;
		aligner(shiftX, shiftY, player, false);
		for(Item i : items) {
			if(i.intersects(player)) {
				collision = true;
				itemFound = i.getID();
				break;
			}
		}
		return collision;
	}

}
