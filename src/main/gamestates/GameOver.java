package main.gamestates;

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
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;

import main.Game;
import main.ResourceManager;
import managers.MusicManager;

public class GameOver extends BasicGameState{

    private final int id = GameStates.GAMEOVER.getState();
    /** The background image to be displayed */
    private Image image;
    private ResourceManager rs;
	private MusicManager mm;
	private UnicodeFont uniFont;
	private boolean startMusic;
	
	public GameOver() {
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
		startMusic = false;
		uniFont = StatesUtils.initFont();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (sbg.getCurrentStateID() == GameStates.GAMEOVER.getState()) {
			g.drawImage(image, 0, 0);
			StatesUtils.applyBorder(uniFont, "Press Enter", 
					(gc.getWidth()-uniFont.getWidth("Press Enter"))/2, 
					(Long.valueOf(Math.round(gc.getHeight()*8/9)).intValue()), 
					new Color(105, 2, 2));
			uniFont.drawString(
					(gc.getWidth()-uniFont.getWidth("Press Enter"))/2, 
					(Long.valueOf(Math.round(gc.getHeight()*8/9)).intValue()), 
					"Press Enter",
					new Color(201, 2, 2));
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
//		Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0) , 5, TimeUnit.SECONDS); 
		if (arg1.getCurrentStateID() == GameStates.GAMEOVER.getState()) {
			if (!startMusic) {
				startMusic = true;
				this.rs.setState(3);
				System.out.println("starting gameover music");
			}
			if (arg0.getInput().isKeyPressed(Input.KEY_ENTER)) {
				startMusic = false;
				((Game)arg1).resetDifficulty();
				this.rs.setState(0);
				arg1.enterState(GameStates.MENU.getState());
			}
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

