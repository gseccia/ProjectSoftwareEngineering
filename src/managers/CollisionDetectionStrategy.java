package managers;

import java.util.List;

import org.newdawn.slick.tiled.TiledMap;

import elements.Item;
import elements.Mob;

/**
 * Collision detector strategy
 */
public abstract class CollisionDetectionStrategy implements Directions {
	protected TiledMap map;
	protected float px, py;
	protected int key;
	protected List<Wall> walls;
	protected List<Wall> doors;
	protected List<Item> items;
	protected List<Mob> mobs;
	
	public List<Wall> getWalls() {
		return walls;
	}

	public List<Mob> getMobs(){
		return mobs;
	}

	public List<Wall> getDoors() {
		return doors;
	}

	public List<Item> getItems() {
		return items;
	}

	public abstract boolean detectCollision(int shiftX, int shiftY, Mob player);
	
	protected void aligner(int shiftX, int shiftY, Mob player, boolean patch) {
		px = player.getX();
		py = player.getY();
		
		int pXPosition = (int)(px);
		int pYPosition = (int)(py);
		
		if(patch) {
			if (key == RIGHT) {
				pXPosition+=16;
			}
			else if (key ==  LEFT) {
				pXPosition-=16;
			}
			else if (key == DOWN) {
				pYPosition+=16;
			}
			else if (key == UP) {
				pYPosition-=16;
			}
		}
		
		pXPosition += shiftX*map.getTileWidth();
		pYPosition += shiftY*map.getTileHeight();
		
		player.setLocation(pXPosition, pYPosition);
	}

}
