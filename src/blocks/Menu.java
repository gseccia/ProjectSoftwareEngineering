package blocks;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;

import main.GameStates;

public class Menu extends BasicGameState {
    private final int id = GameStates.MENU.getState();
    private static Menu ourInstance = new Menu();

    private int playersChoice = 0;
    private static final int NOCHOICES = 5;
    private static final int START = 0;
    private static final int DEMO = 1;
    private static final int SCORES = 2;
    private static final int OPTIONS = 3;
    private static final int QUIT = 4;
    private String[] playersOptions = new String[NOCHOICES];
    private boolean exit = false;
    private Font font;
    private TrueTypeFont playersOptionsTTF;
    private Color notChosen = new Color(255, 0, 0);
    private Color chosen = new Color(0,0,0);
    /** The background image to be displayed */
    private Image image;


    public static Menu getInstance() {
        return ourInstance;
    }

    private Menu() {
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        font = new Font("Jokerman", Font.BOLD, 40);
        playersOptionsTTF = new TrueTypeFont(font, true);
        font = new Font ("Jokerman", Font.PLAIN, 20);
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
                	stateBasedGame.init(gameContainer);
                    stateBasedGame.enterState(GameStates.PLAY.getState());
                    break;
                default:
                    break;

            }

        }
    }

    private void renderPlayersOptions(GameContainer gc){
    	int width = (Long.valueOf(Math.round(gc.getWidth()*0.5)).intValue())-50;
    	int height = (Long.valueOf(Math.round(gc.getHeight()*0.5)).intValue())+70;
        for (int i = 0; i < NOCHOICES; i++) {
            if (playersChoice == i) {
                applyBorder(playersOptions[i], width, i * 50 + height, new Color(0, 255, 255));
                playersOptionsTTF.drawString(width, i * 50 + height, playersOptions[i], chosen);
            } else {
                applyBorder(playersOptions[i], width, i * 50 + height, new Color(0, 255, 0));
                playersOptionsTTF.drawString(width, i * 50 + height, playersOptions[i], notChosen);
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
        playersOptionsTTF.drawString(ShiftWest(x, 1), ShiftNorth(y, 1), s, c);
        playersOptionsTTF.drawString(ShiftWest(x, 1), ShiftSouth(y, 1), s, c);
        playersOptionsTTF.drawString(ShiftEast(x, 1), ShiftNorth(y, 1), s, c);
        playersOptionsTTF.drawString(ShiftEast(x, 1), ShiftSouth(y, 1), s, c);
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
}