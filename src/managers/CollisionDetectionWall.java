package managers;

import elements.Mob;


/**
 * Collision detector for walls
 */
public class CollisionDetectionWall extends CollisionDetectionStrategy {
	
	public CollisionDetectionWall(HitboxMaker hitbox) {
		this.walls = hitbox.getWalls();
		this.map = hitbox.getMap();
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	@Override
	public boolean detectCollision(int shiftX, int shiftY, Mob player) {
		//CAUTION! AT THIS POINT KEY MUST BE PROPERLY SET
		boolean collision = false;
		aligner(shiftX, shiftY, player, true);
		for(Wall wall: walls) {
			if(wall.intersects(player)) {
				collision = true;
				break;
			}
		}
		player.setLocation(px, py);
		return !collision;
	}

}
