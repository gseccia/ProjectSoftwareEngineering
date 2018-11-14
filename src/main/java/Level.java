package main.java;

import elements.Mob;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Level extends BasicGameState{
	private int state;
	private Mob player;
	private TiledMap back;
	private int floor_index,floor_id;
	private Input player_input;
	/*
	 * private GraphTransitionManager map;
	 * private Set<InteractiveElement> element_set;
	 * 
	 * */
	
	public Level(int state) {
		this.state = state;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		player = Mob.generate("guntan");
		back = new TiledMap("resource/maps/CompleteLab/Lab.tmx");
		floor_index = back.getLayerIndex("Base");
		floor_id = back.getTileId(0, 0, floor_index);
		player.setLocation(back.getWidth()/2,back.getHeight()/2);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		back.render(0, 0);
		player.draw();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		player_input = container.getInput();
		
		if(player_input.isKeyDown(Input.KEY_DOWN) && player.getY()<container.getHeight())
		{
			if (back.getTileId((int)player.getX() , (int)player.getY() + 1, floor_index) == 0) player.moveY(1);
		}
		else if(player_input.isKeyDown(Input.KEY_UP)  && player.getY()>0)
		{
			if (back.getTileId((int)player.getX() , (int)player.getY() - 1, floor_index) == floor_id) player.moveY(-1);
		}
		else if(player_input.isKeyDown(Input.KEY_LEFT) && player.getX()>0)
		{
			if (back.getTileId((int)player.getX() - 1, (int)player.getY(), floor_index) == floor_id) player.moveX(-1);
		}
		else if(player_input.isKeyDown(Input.KEY_RIGHT) && player.getX()<container.getWidth())
		{
			if (back.getTileId((int)player.getX() + 1, (int)player.getY(), floor_index) == floor_id) player.moveX(1);
		}
	}

	@Override
	public int getID() {
		return state;
	}




}
