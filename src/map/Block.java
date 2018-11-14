package map;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Block extends TiledMap
{
	private Map<Integer,Integer> forbidden_layer;
	
	
	public Block(String ref) throws SlickException {
		super(ref);
		//Better a configuration file as Mob
		//Spawn Zone ecc..
		forbidden_layer = new HashMap<Integer,Integer>();
		int tmp = this.getLayerIndex("Wall");
		forbidden_layer.put(tmp, this.getTileId(0, 0, tmp));
		tmp = this.getLayerIndex("Obstacle1");
		forbidden_layer.put(tmp, this.getTileId(0, 0, tmp));
		tmp = this.getLayerIndex("Obstacle1");
		forbidden_layer.put(tmp, this.getTileId(0, 0, tmp));
	}

	private int convert(int i)
	{
		return i/16;
	}
	
	public boolean available_zone(int x,int y){
		boolean available=true;
		x = convert(x);
		y = convert(y);
		for(Map.Entry<Integer, Integer> entry:forbidden_layer.entrySet()) 
		{
			available = available && this.getTileId(x, y, entry.getKey()) == entry.getValue();
		}
		return available;
	}
	
	
	
}
