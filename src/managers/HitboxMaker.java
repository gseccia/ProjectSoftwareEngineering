package managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import elements.Mob;
import org.newdawn.slick.tiled.TiledMap;

import elements.Item;

public class HitboxMaker {
	private TiledMap map;
	private List<Wall> walls;
	private List<Wall> doors;
	private List<Item> items;
	private List<Mob> mobs;
	private boolean[][] occupiedTiles;
	
	public HitboxMaker(TiledMap map,List<Mob> mobs) {
		this.map = map;
		this.mobs = mobs;
		this.occupiedTiles = new boolean[map.getWidth()][map.getHeight()];
	}
	
	/**
	 * This method defines the hitbox of the block map for all the interactive objects it contains
	 * @param map TiledMap on which organize the collision manager
	 */
	public void initiateHitbox() {
		//Define the wall hitbox
		walls = new ArrayList<>();
		doors = new ArrayList<>();
		int mask = map.getLayerIndex("Mask");
		int i, j;
		int tileID;
		for(i = 0; i < map.getWidth(); i++) {
			for(j = 0; j < map.getHeight(); j++) {
				tileID = map.getTileId(i, j, mask);
				if(tileID != 0) {
					walls.add(new Wall(i*map.getTileWidth(), j*map.getTileHeight(),
							map.getTileWidth(), map.getTileHeight()));
					occupiedTiles[i][j] = true;
					if(i != map.getWidth()-1) 
						occupiedTiles[i+1][j] = true;
					if(i != 0)
						occupiedTiles[i-1][j] = true;
				}
				else
					occupiedTiles[i][j] = false;
			}
		}
		
		//Define the hitbox for doors
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
		
		//Here there should be the definition of the hitbox for Items
		
	}
	
	public TiledMap getMap() {
		return this.map;
	}

	public List<Wall> getWalls() {
		return walls;
	}

	public List<Wall> getDoors() {
		return doors;
	}

	public List<Item> getItems() {
		return items;
	}

	public List<Mob> getMobs(){
		return mobs;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public boolean[][] getOccupiedTiles() {
		return occupiedTiles;
	}

	public void updateMobs(List<Mob> update){
		mobs.addAll(update);
	}

}
