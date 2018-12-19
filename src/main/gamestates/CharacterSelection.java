package main.gamestates;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import managers.ResourceManager;

/**
 * Character selection state
 */
public class CharacterSelection extends BasicGameState {
	private final int id = GameStates.CHAR_SELECTION.getState();
	private int playersChoice = 0;
	private static final int CHAR1 = 0;
    private static final int CHAR2 = 1;
    private static final int CHAR3 = 2;
    private UnicodeFont uniFont;
	private String char1Descr, char2Descr, char3Descr;
	private String charname1, charname2, charname3, selectedChar;
	private Image background, char1Img, char2Img, char3Img;
	private Rectangle char1, char2, char3, char1Border, char2Border, char3Border;
	
	private Color selectedBorder = new Color(0, 255, 255);
	private Color selectedText = new Color(0, 0, 0);
	
	public CharacterSelection(ResourceManager rs) {
		this.char1 = new Rectangle(50, 153, 200, 400);
		this.char2 = new Rectangle(300, 153, 200, 400);
		this.char3 = new Rectangle(550, 153, 200, 400);
		this.char1Border = new Rectangle(47, 150, 206, 406);
		this.char2Border = new Rectangle(297, 150, 206, 406);
		this.char3Border = new Rectangle(547, 150, 206, 406);
		this.char1Descr = "You join the hunt?";
		this.char2Descr = "So cute, so ruthless";
		this.char3Descr = "My sword is sharp and ready";
		this.charname1 = "vegeta";
		this.charname2 = "fumiko";
		this.charname3 = "rinaldo";
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		try {
			this.background = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/screens/charSelect.png");
			this.char1Img = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/characters/vegeta.png");
			this.char2Img = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/characters/fumiko.png");
			this.char3Img = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/characters/ark.png");
		} catch (IOException e) {
			e.printStackTrace();
		}		
		uniFont = StatesUtils.initFont();
		uniFont = StatesUtils.changeSizeAndStyle(uniFont, 34f, java.awt.Font.PLAIN);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		if (arg1.getCurrentStateID() == this.id) {  
			background.draw(0, 0);
	        arg2.setColor(new Color(105, 2, 2));
	        arg2.draw(char1Border);
	        arg2.fillRect(char1Border.getX(), char1Border.getY(), char1Border.getWidth(), char1Border.getHeight());
	        arg2.draw(char2Border);
	        arg2.fillRect(char2Border.getX(), char2Border.getY(), char2Border.getWidth(), char2Border.getHeight());
	        arg2.draw(char3Border);
	        arg2.fillRect(char3Border.getX(), char3Border.getY(), char3Border.getWidth(), char3Border.getHeight());
	        arg2.setColor(new Color(201, 2, 2));
	        arg2.draw(char1);
	        arg2.fillRect(char1.getX(), char1.getY(), char1.getWidth(), char1.getHeight());
	        arg2.draw(char2);
	        arg2.fillRect(char2.getX(), char2.getY(), char2.getWidth(), char2.getHeight());
	        arg2.draw(char3);
	        arg2.fillRect(char3.getX(), char3.getY(), char3.getWidth(), char3.getHeight());
	        renderCharSelected(arg2,arg0);
			char1Img.draw(60, 283);
			char2Img.draw(310, 283);
			char3Img.draw(560, 283);
    	}		
	}
	
	private void renderCharSelected(Graphics g, GameContainer gc) {
		int selectedBlock = 0;
		switch(playersChoice) {
		case CHAR1:
			selectedChar = charname1;
			g.setColor(new Color(0, 255, 255));
			g.draw(char1Border);
			g.fillRect(char1Border.getX(), char1Border.getY(), char1Border.getWidth(), char1Border.getHeight());
			g.setColor(Color.black);
			g.draw(char1);
			g.fillRect(char1.getX(), char1.getY(), char1.getWidth(), char1.getHeight());
			StatesUtils.applyBorder(uniFont, char1Descr, 20, 600, selectedBorder);
	        uniFont.drawString(20, 600, char1Descr, selectedText);
			break;
		case CHAR2:
			selectedChar = charname2;
			g.setColor(new Color(0, 255, 255));
			g.draw(char2Border);
			g.fillRect(char2Border.getX(), char2Border.getY(), char2Border.getWidth(), char2Border.getHeight());
			g.setColor(Color.black);
			g.draw(char2);
			g.fillRect(char2.getX(), char2.getY(), char2.getWidth(), char2.getHeight());
			StatesUtils.applyBorder(uniFont, char2Descr, (gc.getWidth()-uniFont.getWidth(char2Descr))/2, 600, selectedBorder);
	        uniFont.drawString((gc.getWidth()-uniFont.getWidth(char2Descr))/2, 600, char2Descr, selectedText);
			break;
		case CHAR3:
			selectedChar = charname3;
			g.setColor(new Color(0, 255, 255));
			g.draw(char3Border);
			g.fillRect(char3Border.getX(), char3Border.getY(), char3Border.getWidth(), char3Border.getHeight());
			g.setColor(Color.black);
			g.draw(char3);
			g.fillRect(char3.getX(), char3.getY(), char3.getWidth(), char3.getHeight());
			StatesUtils.applyBorder(uniFont, char3Descr, gc.getWidth()-uniFont.getWidth(char3Descr)-20, 600, selectedBorder);
	        uniFont.drawString(gc.getWidth()-uniFont.getWidth(char3Descr)-20, 600, char3Descr, selectedText);
			break;
		default:
			break;
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Input input = arg0.getInput();		
		if (input.isKeyPressed(Input.KEY_LEFT)) {
				changeSelection(false);
        }
		else if (input.isKeyPressed(Input.KEY_RIGHT)) {
				changeSelection(true);
        }
		else if(input.isKeyPressed(Input.KEY_ENTER)) {
			((main.Game)arg1).setCharacter(selectedChar);
			arg1.enterState(GameStates.SKILL_SELECTION.getState());
		}
		else if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			arg1.enterState(GameStates.MENU.getState());
		}
		
	}
	
	private void changeSelection(boolean isRight) {
		switch(playersChoice) {
			case CHAR1:
				if(isRight)
					playersChoice = CHAR2;
				else
					playersChoice = CHAR3;
				break;
			case CHAR2:
				if(isRight)
					playersChoice = CHAR3;
				else
					playersChoice = CHAR1;
				break;
			case CHAR3:
				if(isRight)
					playersChoice = CHAR1;
				else
					playersChoice = CHAR2;
				break;
			default:
				break;
		}
		
	}

	@Override
	public int getID() {
		return id;
	}
	

}
