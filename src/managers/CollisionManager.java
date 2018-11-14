package managers;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.tiled.TiledMap;

public class CollisionManager implements CollisionManagerInterface {
	private ArrayList<String> layers;
	// private MapConfiguration configuration;

	@Override
	public void checkCollision(TiledMap m, String mapName, int charX, int charY, GameContainer gc) {
		Input in = gc.getInput();
		
//		Leggere i nomi dei layer della mappa dal .tmx
//		layers = getLayers(new MapConfiguration(getJSONIndex(mapName)));
		layers = new ArrayList<String>();
		layers.add("Wall");
		layers.add("Base");
		layers.add("Obstacle1");
		layers.add("Obstacle2");
		
		for (String s: layers) { 
			if (s.matches("Wall*|Obstacle*"))
			{
				if(in.isKeyDown(Input.KEY_D))
				{
					if(m.getTileId(charX+1, charY, m.getLayerIndex(s))==0)
					{
						charX += 1;
					}
				}
				if(in.isKeyDown(Input.KEY_A))
				{
					if(m.getTileId(charX-1, charY, m.getLayerIndex(s))==0)
					{
						charX -= 1;
					}
				}
				if(in.isKeyDown(Input.KEY_W))
				{
					if(m.getTileId(charX, charY-1, m.getLayerIndex(s))==0)
					{
						charY -= 1;
					}
				}
				if(in.isKeyDown(Input.KEY_S))
				{
					if(m.getTileId(charX, charY+1, m.getLayerIndex(s))==0)
					{
						charY += 1;
					}
				}
				
			}
			else if(s.matches("Door*"))
			{
				// Transizione al prossimo blocco
			}
		}
		
	}

}
