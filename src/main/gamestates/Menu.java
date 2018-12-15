package main.gamestates;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;

import main.ResourceManager;
import managers.MusicManager;

public class Menu extends BasicGameState {
    private final int id = GameStates.MENU.getState();
    private static Menu ourInstance = new Menu();

    private int playersChoice = 0;
    private static final int NOCHOICES = 5;
    private static final int START = 0;
    private static final int DEMO = 1;
    private static final int SCORES = 2;
    private static final int SETTINGS = 3;
    private static final int QUIT = 4;
    private String[] playersOptions = new String[NOCHOICES];
    private boolean exit = false;
    private Font font;
    private TrueTypeFont playersOptionsTTF;
    private Color notChosen = new Color(201, 2, 2);
    private Color chosen = new Color(0,0,0);
    /** The background image to be displayed */
    private Image image;
    private ResourceManager rs;
	private MusicManager mm;


    public static Menu getInstance() {
        return ourInstance;
    }

    private Menu() {
        this.rs = ResourceManager.getInstance();
        this.mm = MusicManager.getInstance(this.rs);
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
//        font = new Font("Jokerman", Font.BOLD, 40);
//        playersOptionsTTF = new TrueTypeFont(font, true);
//        font = new Font ("Jokerman", Font.PLAIN, 20);
    	initFont();
        playersOptions[0] = "Start";
        playersOptions[1] = "Demo";
        playersOptions[2] = "Scores";
        playersOptions[3] = "Options";
        playersOptions[4] = "Quit";
        
        try {
			this.image = loadImage(System.getProperty("user.dir") + "/resource/textures/screens/mainMenu.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    	if (stateBasedGame.getCurrentStateID() == this.id) {
    		graphics.drawImage(image, 0, 0);
        this.renderPlayersOptions(gameContainer);
        if (exit) {
            gameContainer.exit();
        }
    	}
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();
        if (input.isKeyPressed(Input.KEY_DOWN)) {
            if (playersChoice == (NOCHOICES - 1)) {
                playersChoice = 0;
            } else {
                playersChoice++;
            }
        }
        if (input.isKeyPressed(Input.KEY_UP)) {
            if (playersChoice == 0) {
                playersChoice = NOCHOICES - 1;
            } else {
                playersChoice--;
            }
        }
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            switch (playersChoice) {
                case QUIT:
                    exit = true;
                    break;
                case START:
                	this.rs.setState(1);
                	stateBasedGame.init(gameContainer);
                    stateBasedGame.enterState(GameStates.STARTING_POINT.getState());
                    break;
                case DEMO:
                	this.rs.setState(1);
                	((main.Game)stateBasedGame).setDemo();
                	stateBasedGame.init(gameContainer);
                    stateBasedGame.enterState(GameStates.STARTING_POINT.getState());
                    break;
                case SETTINGS:
                	stateBasedGame.enterState(GameStates.SETTINGS.getState());
                	break;
                default:
                    break;

            }

        }
    }

    private void renderPlayersOptions(GameContainer gc){
    	int width = 60;
    	int height = (Long.valueOf(Math.round(gc.getHeight()*0.5)).intValue())+70;
        for (int i = 0; i < NOCHOICES; i++) {
            if (playersChoice == i) {
                applyBorder(playersOptions[i], width, i * 50 + height, new Color(0, 255, 255));
//                playersOptionsTTF.drawString(width, i * 50 + height, playersOptions[i], chosen);
                uniFont.drawString(width, i * 50 + height, playersOptions[i], chosen);
            } else {
                applyBorder(playersOptions[i], width, i * 50 + height, new Color(105, 2, 2));
//                playersOptionsTTF.drawString(width, i * 50 + height, playersOptions[i], notChosen);
                uniFont.drawString(width, i * 50 + height, playersOptions[i], notChosen);
            }
        }
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
    private void applyBorder(String s, int x, int y, Color c) {    	
//    	uniFont.drawString(x, y, text, col);
        uniFont.drawString(ShiftWest(x, 2), ShiftNorth(y, 2), s, c);
        uniFont.drawString(ShiftWest(x, 2), ShiftSouth(y, 2), s, c);
        uniFont.drawString(ShiftEast(x, 2), ShiftNorth(y, 2), s, c);
        uniFont.drawString(ShiftEast(x, 2), ShiftSouth(y, 2), s, c);
    }
    
    private int ShiftNorth(int p, int distance) {
    	return (p - distance);
    }
    private int ShiftSouth(int p, int distance) {
    	return (p + distance);
    }
    private int ShiftEast(int p, int distance) {
    	return (p + distance);
    }
    private int ShiftWest(int p, int distance) {
    	return (p - distance);
    }
    

//	Fonts
	java.awt.Font UIFont1;
	org.newdawn.slick.UnicodeFont uniFont;
    @SuppressWarnings("unchecked")
    public void initFont() {
    	try{
    		UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
    				org.newdawn.slick.util.ResourceLoader.getResourceAsStream(
    						System.getProperty("user.dir") + "/resource/font/joystix_monospace.ttf"
    						));
    		UIFont1 = UIFont1.deriveFont(java.awt.Font.ITALIC, 42.f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 30.f is the size of your font

    		uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
    		uniFont.addAsciiGlyphs();
    		uniFont.getEffects().add(new ColorEffect(java.awt.Color.white)); //You can change your color here, but you can also change it in the render{ ... }
    		uniFont.addAsciiGlyphs();
    		uniFont.loadGlyphs();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
