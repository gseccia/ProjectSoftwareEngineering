import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame{
	float x,y;
	boolean down,right;
	
	public Game(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.fillOval(x,y, 10,10);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		x = y =0;
		down = right = true;
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		if(y > 599 || y < 0) 
		{
			down = !down;
		}
		if(x < 0 || x > 799)
		{
			right = !right;
		}
		x = (right)? x+1 : x-1;
		y = (down)? y+1 : y-1;
	}

}
