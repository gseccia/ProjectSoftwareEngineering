package main.java;

import elements.Mob;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Level extends BasicGameState{
	private int state;
	private float x,y;
	private boolean down,right;
	private Mob tmp;
	//private TiledMap back;
	
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
		tmp = Mob.generate("guntan");
		//back = new TiledMap("resource/mapblock/Lab.tmx");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		tmp.draw(400,300);
		//back.render(0, 0);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if(y > container.getHeight() || y < 0) 
		{
			down = !down;
		}
		if(x < 0 || x > container.getWidth())
		{
			right = !right;
		}
		x = (right)? x+1 : x-1;
		y = (down)? y+1 : y-1;
	}

	@Override
	public int getID() {
		return state;
	}




}
