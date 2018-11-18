package managers;

import org.newdawn.slick.tiled.TiledMap;

import elements.Mob;

/**
 * 
 * @author JBFourierous
 * This class manages collisions of the character with map elements.
 */
public class MapCollisionManager implements MapCollisionInterface {
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

	/**
	 * Checks if a mob collides with a wall
	 *
	 * @param shiftX the x shift of the map
	 * @param shiftY the y shift of the map
	 * @param player the mob that collides
	 * @param key    the direction of the movement
	 * @return true if collide, false otherwise
	 */
	@Override
	public boolean wallCollision(int shiftX, int shiftY, Mob player, int key) {
		// TODO Auto-generated method stub
		//Input in = gc.getInput();
		//System.out.println(player.getX());
		//System.out.println(map);

		//System.out.println(((int)player.getX()/map.getTileWidth())+" ,"+(int)player.getY()/map.getTileHeight());

		// DA DOVE PRENDERE shift_x e shift_y?
		int pXPosition = (int)(player.getX()/map.getTileWidth()) + shiftX;
		int pYPosition = (int)(player.getY()/map.getTileHeight()) + shiftY;
		//System.out.println(pXPosition+","+pYPosition);

		if (key == Directions.RIGHT) {
			//Blocked tile
			return (!collisionArray[pXPosition + 1][pYPosition]);
		}
		else if (key == Directions.LEFT) {
			return (!collisionArray[pXPosition - 1][pYPosition]);
		}
		else if (key == Directions.DOWN) {
			return (!collisionArray[pXPosition][pYPosition + 1]);
		}
		else if (key == Directions.UP) {
			return (!collisionArray[pXPosition][pYPosition - 1]);
		}
		else
			return false;
	}

	/**
	 * Checks if a player collides with a door
	 *
	 * @param shiftX the x shift of the map
	 * @param shiftY the y shift of the map
	 * @param player the player object that collides
	 * @return true if collide, false otherwise
	 */
	@Override
	public boolean doorCollision(int shiftX, int shiftY, Mob player) {
		//TO BE IMPLEMENTED
		return false;
	}
}
