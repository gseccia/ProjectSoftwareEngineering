package managers;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;
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
	private List<Rectangle> collidingBlocks;
	//private MapConfiguration mapConf;
	
	public MapCollisionManager(TiledMap map) {
		this.map = map;
		//this.shiftX = shiftX;
		//this.shiftY = shiftY;
		collisionArray = new boolean[map.getWidth()][map.getHeight()];
		collidingBlocks = new ArrayList<Rectangle>();
		int mask = map.getLayerIndex("Mask");
		int i, j;
		int tileID;
		String value;
		for(i = 0; i < map.getWidth(); i++) {
			for(j = 0; j < map.getHeight(); j++) {
				tileID = map.getTileId(i, j, mask);
				//value = map.getTileProperty(tileID, "blocked", "false");
				if(tileID != 0/*value.equals("true")*/) {
					collisionArray[i][j] = true;
					collidingBlocks.add(new Rectangle(i*map.getTileWidth(), j*map.getTileHeight(),
							map.getTileWidth(), map.getTileHeight()));
				}
				else collisionArray[i][j] = false;
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
		float px =player.getX();
		float py =player.getY();
		
		int pXPosition = (int)(player.getX()); //+ shiftX*map.getTileWidth();
		int pYPosition = (int)(player.getY()); //+ shiftY*map.getTileHeight();
		//System.out.println(pXPosition+","+pYPosition);
		boolean collides = false;

		if (key == RIGHT) {
			//Blocked tile
			pXPosition+=2;
			//return (!collisionArray[pXPosition + 1][pYPosition]);
		}
		else if (key ==  LEFT) {
			pXPosition-=2;
			//return (!collisionArray[pXPosition - 1][pYPosition]);
		}
		else if (key == DOWN) {
			pYPosition+=2;
			//return (!collisionArray[pXPosition][pYPosition + 1]);
		}
		else if (key == UP) {
			pYPosition-=2;
			//return (!collisionArray[pXPosition][pYPosition - 1]);
		}
		else
			return false;
		
		player.setLocation(pXPosition, pYPosition);
		for(Rectangle block : collidingBlocks) {
			Rectangle shifted_block = new Rectangle(block.getX()-shiftX*map.getTileWidth(),block.getY()-shiftY*map.getTileHeight(),block.getWidth(),block.getHeight());
			if(shifted_block.intersects(player)) {
				collides = true;
				System.out.println("COLLIDES! ");
				System.out.println("PLAYER: "+pXPosition +";"+ pYPosition+"; "+(pXPosition+player.getWidth())+";"+ (pYPosition+player.getWidth()));
				System.out.println("BLOCK: "+shifted_block.getX() +";"+ shifted_block.getY()+"; "+(shifted_block.getX()+shifted_block.getWidth())+";"+ (shifted_block.getY()+shifted_block.getWidth()));
				break;
			}	
		}
		player.setLocation(px, py);
		return !collides;
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
	
	public List<Rectangle> getCollidingBlocks()
	{
		return collidingBlocks;
	}
}
