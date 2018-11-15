package map;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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
	private int x,y,map_x,map_y;
	private CollisionManager collision;
	private String mapName;
	
	public Block(int state,String mapName)
	{
		this.state=state;
		collision = new CollisionManager();
		this.mapName = mapName;//"resource/maps/CompleteHall/Hall.tmx"; //MapName
	}
	
	public void initBlock(Mob player,Map<Block,Set<Mob>> population) throws SlickException
	{
		map = new TiledMap(mapName);
		enemy = population.get(this);
		this.player = player; // --> Need to set Location near a door
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		//calculate init postion of enemies
		Random rand = new Random();
		player.setPosition(10*16, 10*16); //Set Position on "Base"
		for(Mob e : enemy)
		{
			e.setPosition(rand.nextInt(5)*16, rand.nextInt(5)*16);
		}
		map_x = (int)player.getX()/map.getTileWidth();
		map_y = (int)player.getY()/map.getTileHeight();
		x = (int)player.getX() - map.getWidth()/2;
		y = (int)player.getY() - map.getHeight()/2;
		x = (x < 0)? 0:x;
		y = (y < 0)? 0:y;
	 }

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {
		g.scale(2, 2);
		map.render(0,0,map_x,map_y,map_x+50,map_y+50);
		for(Mob e : enemy)
		{
			e.draw();
		}
		player.draw();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2) throws SlickException {
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
		
		x = (int)player.getX() - map.getWidth();
		y = (int)player.getY() - map.getHeight();
		x = (x < 0)? 0:x;
		y = (y < 0)? 0:y;
		
		
	}
	
	public TiledMap getMap()
	{
		return map;
	}
	
	
	@Override
	public int getID() {
		return state;
	}
	
	
	
}
