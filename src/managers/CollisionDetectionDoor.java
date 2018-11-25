package managers;

import elements.Mob;

public class CollisionDetectionDoor extends CollisionDetectionStrategy {
	private int doorID;
	
	public int getDoor() {
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
		doorID = -1;
		player.setLocation(px, py);
		return collision;
	}

}
