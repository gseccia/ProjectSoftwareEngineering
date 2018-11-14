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
import managers.CollisionManager;

public class Block extends BasicGameState
{
	private TiledMap map;
	private Mob player;
	private Set<Mob> enemy;
	private int state;
	private CollisionManager collision;
	private String mapName;
	
	public Block(int state,String mapName)
	{
		this.state=state;
		collision = new CollisionManager();
		this.mapName = "resource/maps/CompleteLab/Lab.tmx"; //MapName
	}
	
	public void initBlock(Mob player,Map<Block,Set<Mob>> population) throws SlickException
	{
		map = new TiledMap(mapName);
		enemy = population.get(this);
		// player.setLocation(x, y); --> Need to set Location near a door
	}

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		// Calculate initial position of map
		// enemy...
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {
		map.render(0, 0, 3, 10,80,60);
		for(Mob e : enemy)
		{
			e.draw();
		}
		player.draw();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2) throws SlickException {
		collision.checkCollision(map, mapName, 0, 0, gc); // substitute 0,0 with player
		
		/*
		player_input = container.getInput();
		
		if(player_input.isKeyDown(Input.KEY_DOWN) && player.getY()<container.getHeight()-player.getWidth())
		{
			if (current_block.getTileId(((int)(player.getX())/16), ((int)(player.getY()+1)/16), floor_index) == 0) player.moveY(1);
		}
		else if(player_input.isKeyDown(Input.KEY_UP)  && player.getY()>0)
		{
			if (current_block.getTileId(((int)(player.getX())/16), ((int)(player.getY()-1)/16), floor_index) == 0) player.moveY(-1);
		}
		else if(player_input.isKeyDown(Input.KEY_LEFT) && player.getX()>0)
		{
			if (current_block.getTileId(((int)(player.getX()-1)/16), ((int)(player.getY())/16), floor_index) == 0) player.moveX(-1);
		}
		else if(player_input.isKeyDown(Input.KEY_RIGHT) && player.getX()<container.getWidth()-player.getHeight())
		{
			if (current_block.getTileId(((int)(player.getX()+1)/16), ((int)(player.getY())/16), floor_index) == 0) player.moveX(1);
		}
		
		for(Mob e : enemy)
		{
			int random_x = new Random().nextInt(2);
			int random_y = new Random().nextInt(2);
			e.moveX(random_x);
			e.moveY(random_y);
			// manage the collision
		}*/
	}

	@Override
	public int getID() {
		return state;
	}
	
	
	
}
