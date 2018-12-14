package main;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Settings extends BasicGameState  {
	private final int id = 2;
	private static Settings ourInstance = new Settings();
	
	private int playerChoice = 0;
	private static final int NOCHOICES = 4;
	private static final int COMAND = 0;
	private static final int SOUND = 1;
	private static final int MUSIC = 2;
	private static final int BACK = 3;
	private String[] Settings = new String[NOCHOICES];
	private Font font;
    private TrueTypeFont playersOptionsTTF;
    private Color notChosen = new Color(153, 204, 255);
    private boolean exit = false;
    
	public static Settings getInstance() {
        return ourInstance;
    }

    private Settings() {
    }
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		font = new Font("TimesNewRoman", Font.BOLD, 40);
		playersOptionsTTF = new TrueTypeFont(font, true);
        font = new Font ("TimesNewRoman", Font.PLAIN, 20);
        Settings[0] = "Comand";
        Settings[1] = "Sound";
        Settings[2] = "Music";
        Settings[3] = "Back";
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		this.renderPlayersOptions();
        if (exit) {
            gameContainer.exit();
        }
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		Input input = gameContainer.getInput();
        if (input.isKeyPressed(Input.KEY_DOWN)) {
            if (playerChoice == (NOCHOICES - 1)) {
            	playerChoice = 0;
            } else {
            	playerChoice++;
            }
        }
        if (input.isKeyPressed(Input.KEY_UP)) {
            if (playerChoice == 0) {
            	playerChoice = NOCHOICES - 1;
            } else {
            	playerChoice--;
            }
        }
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            switch (playerChoice) {
                case BACK:
                    stateBasedGame.enterState(0);
                    break;
                default:
                    break;

            }

        }
		
	}

	@Override
	public int getID() {
		return id;
	}
	
	private void renderPlayersOptions(){
        for (int i = 0; i < NOCHOICES; i++) {
            if (playerChoice == i) {
                playersOptionsTTF.drawString(100, i * 50 + 200, Settings[i]);
            } else {
                playersOptionsTTF.drawString(100, i * 50 + 200, Settings[i], notChosen);
            }
        }
    }
	
}
