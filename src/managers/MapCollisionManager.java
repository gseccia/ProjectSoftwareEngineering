package managers;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import configuration.DoorsConfiguration;
import elements.Item;
import elements.Mob;

/**
 * 
 * @author JBFourierous
 * This class manages collisions of the character with map elements.
 */
public class MapCollisionManager implements MapCollisionInterface {
	private TiledMap map;
	private List<Wall> collidingBlocks;
	private List<Wall> doors;
	private List<Item> items;
	
	public MapCollisionManager(TiledMap map) {
		this.map = map;
		collidingBlocks = new ArrayList<>();
		doors = new ArrayList<>();
		int mask = map.getLayerIndex("Mask");
		int i, j;
		int tileID;
		for(i = 0; i < map.getWidth(); i++) {
			for(j = 0; j < map.getHeight(); j++) {
				tileID = map.getTileId(i, j, mask);
				if(tileID != 0) {
					collidingBlocks.add(new Wall(i*map.getTileWidth(), j*map.getTileHeight(),
							map.getTileWidth(), map.getTileHeight()));
				}
			}
		}
		
		int n=1,w,h;
		i=0;
		while(i!=-1) {
			i = Integer.parseInt(map.getMapProperty("charXDoor"+n, "-1"));
			j = Integer.parseInt(map.getMapProperty("charYDoor"+n, "-1"));
			w = Integer.parseInt(map.getMapProperty("charWidthDoor"+n, "1"));
			h = Integer.parseInt(map.getMapProperty("charHeightDoor"+n, "1"));
			n++;
			if(i!=-1) {
				doors.add(new Wall(i*map.getTileWidth(), j*map.getTileHeight(),w*map.getTileWidth(), h*map.getTileHeight()));
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

		float px =player.getX();
		float py =player.getY();
		
		int pXPosition = (int)(player.getX());
		int pYPosition = (int)(player.getY());
		
		boolean collides = false;

		// Position updating to check
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
		else
			return false;
		
		// Alignment to the map
		pXPosition += shiftX*map.getTileWidth();
		pYPosition += shiftY*map.getTileHeight();
		
		// check collision
		player.setLocation(pXPosition, pYPosition);
		for(Wall block : collidingBlocks) {
			if(block.intersects(player)) {
				collides = true;
//				System.out.println("COLLIDES! ");
//				System.out.println("PLAYER: "+pXPosition +";"+ pYPosition+"; "+(pXPosition+player.getWidth())+";"+ (pYPosition+player.getWidth()));
//				System.out.println("BLOCK: "+block.getX() +";"+ block.getY()+"; "+(block.getX()+block.getWidth())+";"+ (block.getY()+block.getWidth()));
				break;
			}	
		}
		
		// Set player Position to his place 
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
	public int doorCollision(int shiftX, int shiftY, Mob player) {
		float px =player.getX();
		float py =player.getY();
		
		int pXPosition = (int)(player.getX());
		int pYPosition = (int)(player.getY());
		
		pXPosition += shiftX*map.getTileWidth();
		pYPosition += shiftY*map.getTileHeight();
		
		player.setLocation(pXPosition, pYPosition);
		for(int i=0;i<doors.size();i++) {
			if(doors.get(i).intersects(player)) {
				player.setLocation(px, py);
				return i+1;
			}
		}
		player.setLocation(px, py);
		
		return -1;
	}
	
	/**
	 * Checks if the player collides with an item of the map
	 * 
	 * @param shiftX the x shift of the map
	 * @param shiftY the y shift of the map
	 * @param player the player object that collides
	 * @return type of the item hit by the palyer, empty String in none
	 */
	public String itemCollision(int shiftX, int shiftY, Mob player) {
		float px =player.getX();
		float py =player.getY();
		
		int pXPosition = (int)(player.getX());
		int pYPosition = (int)(player.getY());
		
		pXPosition += shiftX*map.getTileWidth();
		pYPosition += shiftY*map.getTileHeight();
		
		player.setLocation(pXPosition, pYPosition);
		String collidingItem = "";
		for(Item i : items) {
			if(i.intersects(player)) {
				collidingItem = i.getID();
				break;
			}
		}
		return collidingItem;
	}
	
	public List<Wall> getCollidingBlocks()
	{
		return collidingBlocks;
	}
	
	public List<Wall> getDoors()
	{
		return doors;
	}
}
