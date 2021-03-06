package managers.strategy;

import elements.Item;
import elements.Mob;


/**
 * Collsion detector for player with the items
 */
public class CollisionDetectionItem extends CollisionDetectionStrategy {
	private String itemID = "";
	private Item itemFound;
	
	public CollisionDetectionItem(HitboxMaker hitbox) {
		this.items = hitbox.getItems();
		this.map = hitbox.getMap();
	}
	
	public String getItemID() {
		return itemID;
	}
	
	public Item getCollidedItem() {
		return itemFound;
	}
	
	@Override
	public boolean detectCollision(int shiftX, int shiftY, Mob player) {
		boolean collision = false;
		//aligner(shiftX, shiftY, player, false);
		for(Item i : items) {
			if(i.intersects(player)) {
				collision = true;
				itemID = i.getID();
				itemFound = i;
				items.remove(i);
				break;
			}
		}
		return collision;
	}

}
