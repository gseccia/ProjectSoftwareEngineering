
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
		x = y =0;
		down = right = true;
		//back = new TiledMap("resource/mapblock/Lab.tmx");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.fillOval(x,y, 10,10);
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
