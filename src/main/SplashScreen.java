package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import managers.MusicManager;

public class SplashScreen extends BasicGameState{

    private final int id = GameStates.SPLASHSCREEN.getState();
    /** The background image to be displayed */
    private Image image;
    private long initTime;
    private ResourceManager rs;
	private MusicManager mm;
	
	public SplashScreen() {
        this.rs = ResourceManager.getInstance();
        this.mm = MusicManager.getInstance(this.rs);
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
//		rs.execute();
		this.image = new Image(System.getProperty("user.dir") + "/resource/textures/screens/LoadingScreen.png");
		this.initTime = System.currentTimeMillis();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(image, 0, 0);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
//		System.out.println("splash screen");
		long delta = System.currentTimeMillis() - initTime;
		if (delta > 3000) {
			this.rs.setState(0);
			arg1.enterState(GameStates.MENU.getState());
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
