package managers;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.tiled.TiledMap;

import configuration.MapConfiguration;
import elements.Mob;
import elements.NullAnimationException;

import configuration.MapConfiguration;

public class CollisionManager implements CollisionManagerInterface {
	private ArrayList<String> layers;
	// private MapConfiguration configuration;

	@Override
	public void checkCollision(String mapName, int shift_x,int shift_y,Mob player, GameContainer gc) throws NullAnimationException {
		Input in = gc.getInput();
		
//		Leggere i nomi dei layer della mappa dal .conf
		MapConfiguration mp = MapConfiguration.getInstance();
		TiledMap m = mp.getMapTiled("resource/maps/Complete"+mapName+"/"+mapName+".tmx");
//		layers = mp.getLayers(mapName);
//		System.out.println(layers);

		
		System.out.println(((int)player.getX()/m.getTileWidth())+" ,"+(int)player.getY()/m.getTileHeight());
		
		int px_position = (int)(player.getX()/m.getTileWidth()) + shift_x;
		int py_position = (int)(player.getY()/m.getTileHeight()) + shift_y;
		
		System.out.println(px_position+","+py_position);
		/*px_position = 10;
		py_position = 10;*/

		if(in.isKeyDown(Input.KEY_D))
		{
			if(px_position+1<m.getWidth() && m.getTileId(px_position+1, py_position, m.getLayerIndex("Mask"))==0)
			{
				player.moveX(1);
				//player.moveX(m.getTileWidth());
				player.faceRight();
			}
		}
		else if(in.isKeyDown(Input.KEY_A))
		{
			if(px_position-1 > 0 && m.getTileId(px_position-1, py_position, m.getLayerIndex("Mask"))==0)
			{
				player.moveX(-1);
				//player.moveX(-m.getTileWidth());
				player.faceLeft();
			}
		}
		else if(in.isKeyDown(Input.KEY_W))
		{
			if(py_position-1 > 0 && m.getTileId(px_position, py_position-1, m.getLayerIndex("Mask"))==0)
			{
				player.moveY(-1);
				//player.moveY(-m.getTileHeight());
				player.faceUp();
			}
		}
		else if(in.isKeyDown(Input.KEY_S))
		{
			if(py_position+1 < m.getHeight() && m.getTileId(px_position, py_position+1, m.getLayerIndex("Mask"))==0)
			{
				player.moveY(1);
				//player.moveY(m.getTileHeight());
				player.faceDown();
			}
		}
		else
		{
			player.faceStill();
		}
		
	}
//			else if(s.matches("Door*"))
//			{
//				// Transizione al prossimo blocco
//			}
		
	

}
