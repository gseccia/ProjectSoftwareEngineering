package managers;

import org.newdawn.slick.GameContainer;
import  org.newdawn.slick.Input;
import org.newdawn.slick.tiled.TiledMap;

import elements.Mob;
import elements.NullAnimationException;

/**
 * 
 * @author JBFourierous
 * This class manages collisions of the character with map elements.
 */
public class MapCollisionManager {
	//int shiftX, shiftY;
	//private String mapName;
	private TiledMap map;
	private boolean collisionArray[][];
	//private MapConfiguration mapConf;
	
	public MapCollisionManager(TiledMap map) {
		this.map = map;
		//this.shiftX = shiftX;
		//this.shiftY = shiftY;
		collisionArray = new boolean[map.getWidth()][map.getHeight()];
		int mask = map.getLayerIndex("Mask");
		int i, j;
		int tileID;
		String value;
		for(i = 0; i < map.getWidth(); i++) {
			for(j = 0; j < map.getHeight(); j++) {
				tileID = map.getTileId(i, j, mask);
				value = map.getTileProperty(tileID, "blocked", "false");
				if(value.equals("true")) {
					collisionArray[i][j] = true;
				}
			}
		}
	}


	public boolean wallCollision(int shiftX, int shiftY, Mob player, int key) throws NullAnimationException {
		// TODO Auto-generated method stub
		//Input in = gc.getInput();
		//System.out.println(player.getX());
		//System.out.println(map);

		//System.out.println(((int)player.getX()/map.getTileWidth())+" ,"+(int)player.getY()/map.getTileHeight());
		
		// DA DOVE PRENDERE shift_x e shift_y?
		int pXPosition = (int)(player.getX()/map.getTileWidth()) + shiftX;
		int pYPosition = (int)(player.getY()/map.getTileHeight()) + shiftY;
		//System.out.println(pXPosition+","+pYPosition);
		
		if (key == Input.KEY_D) {
			//Blocked tile
			return (!collisionArray[pXPosition + 1][pYPosition]);
		}
		else if (key == Input.KEY_A) {
			return (!collisionArray[pXPosition - 1][pYPosition]);
		}
		else if (key == Input.KEY_S) {
			return (!collisionArray[pXPosition][pYPosition + 1]);
		}
		else if (key == Input.KEY_W) {
			return (!collisionArray[pXPosition][pYPosition - 1]);
		}
		else
			return false;
	}

	public boolean doorCollision(int shiftX, int shiftY, Mob player) {
		//TO BE IMPLEMENTED
		return false;
	}

}
