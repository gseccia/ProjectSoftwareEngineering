package managers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.tiled.TiledMap;

import elements.Mob;
import elements.NullAnimationException;

/**
 * 
 * @author JBFourierous
 * This class manages collisions of the character with map elements.
 */
public class CollisionManagerMap implements CollisionManagerMKII {
	//int shiftX, shiftY;
	private Mob player;
	//private String mapName;
	private TiledMap map;
	private GameContainer gc;
	private boolean collisionArray[][];
	//private MapConfiguration mapConf;
	
	public CollisionManagerMap(Mob player, TiledMap map, GameContainer gc) {
		this.player = player;
		this.map = map;
		this.gc = gc;
		//this.shiftX = shiftX;
		//this.shiftY = shiftY;
		collisionArray = new boolean[map.getWidth()][map.getHeight()];
		int mask = map.getLayerIndex("Mask");
		int i = 0, j = 0;
		int tileID = 0;
		String value = "";
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
	 * Detects a collision of the player character with 
	 * @throws NullAnimationException 
	 */
	@Override
	public void detectCollision(int shiftX, int shiftY) throws NullAnimationException {
		// TODO Auto-generated method stub
		Input in = gc.getInput();
		//System.out.println(player.getX());
		//System.out.println(map);

		//System.out.println(((int)player.getX()/map.getTileWidth())+" ,"+(int)player.getY()/map.getTileHeight());
		
		// DA DOVE PRENDERE shift_x e shift_y?
		int pXPosition = (int)(player.getX()/map.getTileWidth()) + shiftX;
		int pYPosition = (int)(player.getY()/map.getTileHeight()) + shiftY;
		//System.out.println(pXPosition+","+pYPosition);
		
		if ((in.isKeyDown(Input.KEY_D))){
			//Blocked tile
			if(!collisionArray[pXPosition + 1][pYPosition]) 
				player.moveX(1);
			player.faceRight();
		}
		else if ((in.isKeyDown(Input.KEY_A))) {
			if(!collisionArray[pXPosition - 1][pYPosition]) 
				player.moveX(-1);
			player.faceLeft();
		}
		else if ((in.isKeyDown(Input.KEY_S))) {
			if(!collisionArray[pXPosition][pYPosition + 1]) 
				player.moveY(1);
			player.faceDown();
		}
		else if ((in.isKeyDown(Input.KEY_W))) {
			if(!collisionArray[pXPosition][pYPosition - 1]) 
				player.moveY(-1);
			player.faceUp();
		}
		else 
			player.faceStill();
		//DOOR COLLISION TO BE ADDED
	}

}
