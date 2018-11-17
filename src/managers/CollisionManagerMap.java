package managers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.tiled.TiledMap;

import configuration.MapConfiguration;
import elements.Mob;
import elements.NullAnimationException;

/**
 * 
 * @author JBFourierous
 * This class manages collisions of the character with map elements.
 */
public class CollisionManagerMap implements CollisionManagerMKII {
	int shiftX, shiftY;
	private Mob player;
	private String mapName;
	private TiledMap map;
	private GameContainer gc;
	private MapConfiguration mapConf;
	
	public CollisionManagerMap(Mob player, String mapName, GameContainer gc) {
		super();
		this.player = player;
		this.mapName = mapName;
		this.gc = gc;
		this.mapConf = MapConfiguration.getInstance();
		TiledMap map = mapConf.getMapTiled("resource/maps/Complete"+mapName+"/"+mapName+".tmx");
		this.shiftX = Integer.parseInt(map.getMapProperty("charXDoor1","0"));
		this.shiftY = Integer.parseInt(map.getMapProperty("charYDoor1","0"));
	}

	/**
	 * Detects a collision of the player character with 
	 * @throws NullAnimationException 
	 */
	@Override
	public void detectCollision() throws NullAnimationException {
		// TODO Auto-generated method stub
		Input in = gc.getInput();
		
		System.out.println(((int)player.getX()/map.getTileWidth())+" ,"+(int)player.getY()/map.getTileHeight());
		
		// DA DOVE PRENDERE shift_x e shift_y?
		int pXPosition = (int)(player.getX()/map.getTileWidth()) + shiftX;
		int pYPosition = (int)(player.getY()/map.getTileHeight()) + shiftY;
		
		System.out.println(pXPosition+","+pYPosition);
		
		if(in.isKeyDown(Input.KEY_D))
		{
			if(pXPosition+1<map.getWidth() && map.getTileId(pXPosition+1, pYPosition, map.getLayerIndex("Mask"))==0)
			{
				player.moveX(1);
				//player.moveX(m.getTileWidth());
				player.faceRight();
			}
		}
		else if(in.isKeyDown(Input.KEY_A))
		{
			if(pXPosition-1 > 0 && map.getTileId(pXPosition-1, pYPosition, map.getLayerIndex("Mask"))==0)
			{
				player.moveX(-1);
				//player.moveX(-m.getTileWidth());
				player.faceLeft();
			}
		}
		else if(in.isKeyDown(Input.KEY_W))
		{
			if(pYPosition-1 > 0 && map.getTileId(pXPosition, pYPosition-1, map.getLayerIndex("Mask"))==0)
			{
				player.moveY(-1);
				//player.moveY(-m.getTileHeight());
				player.faceUp();
			}
		}
		else if(in.isKeyDown(Input.KEY_S))
		{
			if(pYPosition+(int)(player.getHeight()/16) < map.getHeight() && map.getTileId(pXPosition, pYPosition+(int)(player.getHeight()/16), map.getLayerIndex("Mask"))==0)
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
		
		//DOOR COLLISION TO BE ADDED
	}

}
