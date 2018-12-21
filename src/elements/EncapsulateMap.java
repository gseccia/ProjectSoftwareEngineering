package elements;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.*;

/**
 * This class allows to represent the tile format of the map
 */
public class EncapsulateMap implements TileBasedMap{
	
	private TiledMap map;
	private int wallIndex;
	private List<Wall> doors;
	
	/**
	 * Construct an element that encapsulate all
	 * 
	 * @param map TiledMap associated to this object
	 * @param doors List of doors contained in this map
	 */
	public EncapsulateMap(TiledMap map, List<Wall> doors) {
		this.map=map;
		wallIndex=map.getLayerIndex("Mask");
		this.doors = doors;
	}
	
	@Override
	public boolean blocked(PathFindingContext pf, int x, int y) {
		boolean result = false;
		for(Wall door:doors) {
			result = (int)(door.getX()/map.getTileWidth()) == x && (int)(door.getY()/map.getTileHeight()) == y;
			if(result) return false;
		}
		
		if(x<0 || x>getWidthInTiles()-1) return false;
		if(y<0 || y>=getHeightInTiles()-1) return false;
		
		return map.getTileId(x, y, wallIndex) != 0 || map.getTileId(x, y+1, wallIndex) != 0 ;
	}

	@Override
	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		return 0;
	}

	@Override
	public int getHeightInTiles() {
		return map.getHeight();
	}

	@Override
	public int getWidthInTiles() {
		return map.getWidth();
	}

	@Override
	public void pathFinderVisited(int x, int y) {
	}

}
