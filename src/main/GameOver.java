package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;

import managers.MusicManager;

public class GameOver extends BasicGameState{

    private final int id = 4;
    /** The background image to be displayed */
    private Image image;
    private long initTime;
    private ResourceManager rs;
	private MusicManager mm;
	private boolean startCoolDown;
	
	public GameOver() {
//		TODO Chiamare il music manager da questa classe anzichè dal blocco
        this.rs = ResourceManager.getInstance();
        this.mm = MusicManager.getInstance(this.rs);
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		try {
			this.image = loadImage(System.getProperty("user.dir") + "/resource/textures/screens/gameOver.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.initTime = System.currentTimeMillis();
//		if (arg1.getCurrentStateID()==-2) {
//			rs.setState(3);
//			System.out.println("starting game over music");
//		}
		startCoolDown = true;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (sbg.getCurrentStateID() == GameStates.GAMEOVER.getState()) {
			g.drawImage(image, 0, 0);
			g.drawString("Dumb ass press ENTER to continue.", 
					(Long.valueOf(Math.round(gc.getWidth()/2)).intValue()), 
					(Long.valueOf(Math.round(gc.getHeight()/2)).intValue()));
			
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		long delta = System.currentTimeMillis() - this.initTime;
//		if (delta >= 5000) {
//			System.exit(0);
//		}
//		Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0) , 5, TimeUnit.SECONDS); 
		if (startCoolDown && arg1.getCurrentStateID() == GameStates.GAMEOVER.getState()) {
			startCoolDown = false;
			rs.setState(3);
			System.out.println("starting gameover music");
		}
		if (arg0.getInput().isKeyPressed(Input.KEY_ENTER)) {
			this.rs.setState(0);
			arg1.enterState(GameStates.MENU.getState());
		}
	}

	@Override
	public int getID() {
		return id;
	}
	
	private static Image loadImage(String path) throws IOException
	{
	    BufferedImage bufferedImage = ImageIO.read(new File(path));
	    Texture texture = BufferedImageUtil.getTexture("", bufferedImage);
	    Image image = null;
		try {
			image = new Image(texture.getImageWidth(), texture.getImageHeight());
		    image.setTexture(texture);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return image; 
	}
}

