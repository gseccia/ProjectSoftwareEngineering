import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameMain extends StateBasedGame{
	
	public final static int MENU = 0;
	public final static int PLAY = 1;
	
	
	public GameMain(String gamename)
	{
		super(gamename);
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new Level(PLAY));
		this.enterState(PLAY); //Enter in MENU in real version
	}
	
    public static void main(String args[]){

        System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir")+"/native/"+LWJGLUtil.getPlatformName());
        System.setProperty("net.java.games.input.librarypath",System.getProperty("user.dir")+"/native/"+LWJGLUtil.getPlatformName());
       
		try {
			 AppGameContainer app = new AppGameContainer(new GameMain("UNI:ZA"));
			 app.setDisplayMode(800, 600, false);
		     app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
    }


}
