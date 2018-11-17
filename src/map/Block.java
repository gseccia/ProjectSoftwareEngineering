package map;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import elements.Mob;
import elements.NullAnimationException;
import managers.CollisionManager;

public class Block extends BasicGameState
{
	private TiledMap map;
	private Mob player;
	private Set<Mob> enemy;
	private int state;
	private int map_x,map_y;
	private CollisionManager collision;
	private String mapName;
	
	public Block(int state,String mapName)
	{
		this.state=state;
		collision = new CollisionManager();
		this.mapName = mapName;
	}
	
	/**
	 *  This function initializes the block
	 * @param player is the user charachter
	 * @param population Map that contains information about enemies in the blocks
	 * @throws SlickException
	 */
	public void initBlock(Mob player,Map<Block,Set<Mob>> population) throws SlickException
	{
		map = new TiledMap("resource/maps/Complete"+mapName+"/"+mapName+".tmx");
		enemy = population.get(this);
		this.player = player; 
	}
	

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) {
		int x,y;
		Random rand = new Random();
		x = Integer.parseInt(map.getMapProperty("charXDoor1","0"));
		y = Integer.parseInt(map.getMapProperty("charYDoor1","0"));
		
		map_x = x;
		map_y = y;
		
		// Player spawns in front of Door1
		player.setPosition(x*map.getTileWidth() -map_x*map.getTileWidth()/2, y*map.getTileHeight()-map_y*map.getTileWidth()/2); //WARNING --> subtract shifting
		
		// Enemies spawn from a set of a random spawn points
		for(Mob e : enemy)
		{
			if (rand.nextInt(3) > 1) {
				x = Integer.parseInt(map.getMapProperty("spawnX1","0"));
				y = Integer.parseInt(map.getMapProperty("spawnY1","0"));
			}
			else {
				x = Integer.parseInt(map.getMapProperty("spawnX3","0"));
				y = Integer.parseInt(map.getMapProperty("spawnY3","0"));
			}
			e.setPosition(x*map.getTileWidth() -map_x*map.getTileWidth()/2, y*map.getTileHeight()-map_y*map.getTileWidth()/2);
		}
		
		
		// Shift of the map
		map_x = (int)player.getX()/map.getTileWidth();
		map_y = (int)player.getY()/map.getTileHeight();
	 }

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) {
		g.scale(1.5f, 1.5f);
		map.render(0,0,map_x,map_y,map_x+50,map_y+50);
		for(Mob e : enemy)
		{
			e.draw();
		}
		player.draw();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta) {
		try {
			collision.checkCollision(mapName,map_x,map_y, player, gc);
			map_x = (int)player.getX()/map.getTileWidth();
			map_y = (int)player.getY()/map.getTileHeight();
		} catch (NullAnimationException e1) {
			e1.printStackTrace();
		}
		for(Mob e : enemy)
		{
			int random_x = new Random().nextInt(2);
			int random_y = new Random().nextInt(2);
			e.moveX(random_x);
			e.moveY(random_y);
			// manage the collision
		}
	}
	
	@Override
	public int getID() {
		return state;
	}
	
	
	
}
