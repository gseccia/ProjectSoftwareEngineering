package main.gamestates;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import configuration.PlayerCommands;
import main.ResourceManager;
import managers.MusicManager;
import managers.command.Broker;
import managers.command.DecreaseVolumeCommand;
import managers.command.IncreaseVolumeCommand;
import managers.command.ResetVolumeCommand;

public class Settings extends BasicGameState  {
	private final int id = 2;
	private static Settings ourInstance = new Settings();
	private UnicodeFont uniFont;
	
	private int playerChoice = 0;
	// final list di stringhe e immagini 
	private String[] Settings = new String[NOCHOICES];
	private String[] descriptions = new String[NOCHOICES];
	private Image[] redRightTriangles = new Image[NOCHOICES];
	private Image[] blackRightTriangles = new Image[NOCHOICES];
	private Image[] redLeftTriangles = new Image[NOCHOICES];
	private Image[] blackLeftTriangles = new Image[NOCHOICES];
	
	private PlayerCommands pc = PlayerCommands.getPlayerCommandsInstance();
	
	private static final int NOCHOICES = 8;
	private static final int W = 0;
	private static final int A = 1;
	private static final int S = 2;
	private static final int D = 3;
	private static final int ATTACK = 4;
	private static final int SPECIAL = 5;
	private static final int VOLUME = 6;
	private static final int BACK = 7;
	
	// choices for right
	private static final String right = "KEY_RIGHT";
	// choices for left
	private static final String left = "KEY_LEFT";
	// choices for up
	private static final String up = "KEY_UP";
	// choices for right
	private static final String down = "KEY_DOWN";
	// choices for exit
	private static final String reset = "Reset";
	
    private Color notChosen = new Color(201, 2, 2);
    private Color chosen = new Color(0,0,0);
    private boolean exit = false;
    private Image background;
    
//    Command design pattern usage
    private final Broker broker = new Broker();
    private final IncreaseVolumeCommand increaseVolume = IncreaseVolumeCommand.getInstance();
    private final DecreaseVolumeCommand decreaseVolume = DecreaseVolumeCommand.getInstance();
    private final ResetVolumeCommand resetVolume = ResetVolumeCommand.getInstance();
    
    private boolean rst = false;

	public static Settings getInstance() {
        return ourInstance;
    }

    private Settings() {
    }
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
			this.background = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/screens/settings.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		uniFont = StatesUtils.initFont();
		uniFont = StatesUtils.changeSizeAndStyle(uniFont, 40f, java.awt.Font.ITALIC);
		
		for (int i=0; i<NOCHOICES; i++)
			redRightTriangles[i] = new Image(System.getProperty("user.dir") + "/resource/textures/arrows/rightArrowRed.png");
		for (int i=0; i<NOCHOICES; i++)
			blackRightTriangles[i] = new Image(System.getProperty("user.dir") + "/resource/textures/arrows/rightArrowBlack.png");
		for (int i=0; i<NOCHOICES; i++)
			redLeftTriangles[i] = new Image(System.getProperty("user.dir") + "/resource/textures/arrows/leftArrowRed.png");
		for (int i=0; i<NOCHOICES; i++)
			blackLeftTriangles[i] = new Image(System.getProperty("user.dir") + "/resource/textures/arrows/leftArrowBlack.png");
		
		descriptions[0]="Up";
		descriptions[1]="Left";
		descriptions[2]="Down";
		descriptions[3]="Right";
		descriptions[4]="Attack1";
		descriptions[5]="Attack2";
		descriptions[6]="Volume";
		descriptions[7]="";
		
		if(!rst) {
			this.defaultSetting();
			rst = true;
		}
		
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		if (stateBasedGame.getCurrentStateID() == this.id) {
			graphics.drawImage(background, 0, 0);
			this.renderPlayersOptions();
			this.renderDescriptions();
			this.renderTriangles(graphics);
			if (exit) {
				gameContainer.exit();
			}
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
        if (input.isKeyPressed(Input.KEY_LEFT)) {
        	changeValue(true);
        }
        if (input.isKeyPressed(Input.KEY_RIGHT)) {

        	changeValue(false);
        }
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            switch (playerChoice) {
                case BACK:
                	if (Settings[BACK] == "Back")
                		stateBasedGame.enterState(0);
                	else if (Settings[BACK] == reset) {
                		this.defaultSetting();
                	}
                    break;
                default:
                    break;
            }
        }
	}

	// reset settings
	
	private void defaultSetting() {
		//reset music
		broker.takeCommand(resetVolume);
		broker.executeCommand();
		//reset commands
		pc.setUp(true);
		pc.setDown(true);
		pc.setRight(true);
		pc.setLeft(true);
		pc.setAttack1(true);
		pc.setAttack2(true);
		//reset settings menu
		Settings[0] = "W";
	    Settings[1] = "A";
	    Settings[2] = "S";
	    Settings[3] = "D";
	    Settings[4] = "M";
	    Settings[5] = "SPACE";
	    Settings[6] = "30";
	    Settings[7] = "Back";
	}
	
	// Handle logic of selection
	
	private void changeValue(boolean leftKey) {
		switch(playerChoice) {
			case 0:
				if (Settings[playerChoice]=="W") {
					Settings[playerChoice] = up;
					pc.setUp(false);
					
				}
				else {
					Settings[playerChoice] = "W";
					pc.setUp(true);
				}
				break;
			case 1:
				if (Settings[playerChoice]=="A") {
					Settings[playerChoice] = left;
					pc.setLeft(false);
				}
				else {
					Settings[playerChoice] = "A";
					pc.setLeft(false);
				}
				break;
			case 2:
				if (Settings[playerChoice]=="S") {
					Settings[playerChoice] = down;
					pc.setDown(false);
				
				}
				else {
					Settings[playerChoice] = "S";
					pc.setDown(true);
				}
				break;
			case 3:
				if (Settings[playerChoice]=="D") {
					Settings[playerChoice] = right;
					pc.setRight(false);
				}
				else {
					Settings[playerChoice] = "D";
					pc.setRight(true);
				}
				break;
			case 4:
				if (Settings[playerChoice]=="M") {
					Settings[playerChoice] = "Z";
					pc.setAttack1(false);
				}
				else {
					Settings[playerChoice] = "M";
					pc.setAttack1(true);
				}
				break;
			case 5:
				if (Settings[playerChoice]=="SPACE") {
					Settings[playerChoice] = "X";
					pc.setAttack2(false);
				}	
				else {
					 Settings[playerChoice] = "SPACE";
					 pc.setAttack2(true);
				}
				break;
			case 6:
				if (leftKey) {
					broker.takeCommand(decreaseVolume);
				}
				else {
					broker.takeCommand(increaseVolume);
				}
				broker.executeCommand();
				String t = String.valueOf( Math.round(MusicManager.getInstance(ResourceManager.getInstance()).getVolume()*100));
				Settings[playerChoice] = t;
				break;
			case 7:
				if (Settings[playerChoice]=="Back")
					Settings[playerChoice] = reset;
				else Settings[playerChoice] = "Back";
				break;
			default:
				break;
		}
	}
	
	// Handle logic of update
	
	@Override
	public int getID() {
		return id;
	}
	
	private void renderTriangles(Graphics g){
		for (int i = 0; i < NOCHOICES; i++) {
			if (playerChoice == i) {
				g.drawImage(blackLeftTriangles[i], 300, i * 50 + 200);
				g.drawImage(blackRightTriangles[i], 700, i * 50 + 200);
			} else {
				g.drawImage(redLeftTriangles[i], 300, i * 50 + 200);
				g.drawImage(redRightTriangles[i], 700, i * 50 + 200);
			}
		}
    }
	
	private void renderDescriptions(){
		for (int i = 0; i < NOCHOICES; i++) {
			if (playerChoice == i) {
				StatesUtils.applyBorder(uniFont, descriptions[i], 60, i * 50 + 200, new Color(0, 255, 255));
				uniFont.drawString(60, i * 50 + 200, descriptions[i], chosen);
			} else {
				StatesUtils.applyBorder(uniFont, descriptions[i], 60, i * 50 + 200, new Color(105, 2, 2));
				uniFont.drawString(60, i * 50 + 200, descriptions[i], notChosen);
			}
		}
    }
	
	private void renderPlayersOptions(){
        for (int i = 0; i < NOCHOICES; i++) {
    		int center = ((700-300)/2)+300-uniFont.getWidth(Settings[i])/2 +redLeftTriangles[i].getWidth()/2;
            if (playerChoice == i) {
            	 StatesUtils.applyBorder(uniFont, Settings[i], center, i * 50 + 200, new Color(0, 255, 255));
            	 uniFont.drawString(center, i * 50 + 200, Settings[i], chosen);
            } else {
            	StatesUtils.applyBorder(uniFont, Settings[i], center, i * 50 + 200, new Color(105, 2, 2));
            	uniFont.drawString(center, i * 50 + 200, Settings[i], notChosen);
            }
        }
    }
	
}
