package elements;

import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.*;


public class EncapsulateMap implements TileBasedMap{
	
	private TiledMap map;
	private int wallIndex;
	
	
	public EncapsulateMap(TiledMap map) {
		this.map=map;
		wallIndex=map.getLayerIndex("Mask");
	}
	
	@Override
	public boolean blocked(PathFindingContext pf, int x, int y) {
		if(x<0 || x>=getWidthInTiles()) return false;
		if(y<0 || y>=getHeightInTiles()-1) return false;
		return map.getTileId(x, y, wallIndex) != 0 || map.getTileId(x, y+1, wallIndex) != 0;
	}

	@Override
	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		return 0; // No cost for now
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
