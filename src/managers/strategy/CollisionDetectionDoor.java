package managers.strategy;

import elements.Mob;
import elements.Wall;

/**
 * Collision detector for the doors
 */
public class CollisionDetectionDoor extends CollisionDetectionStrategy {
	private int doorID;
	
	public CollisionDetectionDoor(HitboxMaker hitbox) {
		this.doors = hitbox.getDoors();
		this.map = hitbox.getMap();
	}
	
	public int getCollidedDoor() {
		return doorID;
	}

	@Override
	public boolean detectCollision(int shiftX, int shiftY, Mob player) {
		boolean collision = false;
		int i = 1;
		aligner(shiftX, shiftY, player, false);
		for(Wall door: doors) {
			if(door.intersects(player)) {
				collision = true;
				player.setLocation(px, py);
				doorID = i;
				break;
			}
			i++;
		}
		if(!collision)
			doorID = -1;
		player.setLocation(px, py);
		return collision;
	}

}
