package main.gamestates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import main.ResourceManager;
import managers.MusicManager;
import managers.command.Broker;
import managers.command.DecreaseVolumeCommand;
import managers.command.IncreaseVolumeCommand;

public class Settings extends BasicGameState  {
	private final int id = 2;
	private static Settings ourInstance = new Settings();
	
	private int playerChoice = 0;
	// final list di stringhe e immagini 
	private String[] Settings = new String[NOCHOICES];
	private String[] descriptions = new String[NOCHOICES];
	private Image[] redRightTriangles = new Image[NOCHOICES];
	private Image[] blackRightTriangles = new Image[NOCHOICES];
	private Image[] redLeftTriangles = new Image[NOCHOICES];
	private Image[] blackLeftTriangles = new Image[NOCHOICES];
	
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

    //Fonts
    java.awt.Font UIFont1;
    org.newdawn.slick.UnicodeFont uniFont;

	public static Settings getInstance() {
        return ourInstance;
    }

    private Settings() {
    }
	
	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.background = new Image(System.getProperty("user.dir") + "/resource/textures/screens/settings.png");
		initFont();
		
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
		
        Settings[0] = "W";
        Settings[1] = "A";
        Settings[2] = "S";
        Settings[3] = "D";
        Settings[4] = "M";
        Settings[5] = "SPACE";
        Settings[6] = String.valueOf(Math.round(MusicManager.getInstance(ResourceManager.getInstance()).getVolume()*100));
        Settings[7] = "Back";
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
                		init(gameContainer, stateBasedGame);
                	}
                    break;
                default:
                    break;
            }
        }
	}

	// Handle logic of selection
	
	private void changeValue(boolean leftKey) {
		switch(playerChoice) {
			case 0:
				if (Settings[playerChoice]=="W")
					Settings[playerChoice] = up;
				else Settings[playerChoice] = "W";
				break;
			case 1:
				if (Settings[playerChoice]=="A")
					Settings[playerChoice] = left;
				else Settings[playerChoice] = "A";
				break;
			case 2:
				if (Settings[playerChoice]=="S")
					Settings[playerChoice] = down;
				else Settings[playerChoice] = "S";
				break;
			case 3:
				if (Settings[playerChoice]=="D")
					Settings[playerChoice] = right;
				else Settings[playerChoice] = "D";
				break;
			case 4:
				if (leftKey) Settings[playerChoice] = String.valueOf( (char) (Settings[playerChoice].charAt(0) - 1));
				else Settings[playerChoice] = String.valueOf( (char) (Settings[playerChoice].charAt(0) + 1));
				break;
			case 5:
				if (leftKey) Settings[playerChoice] = String.valueOf( (char) (Settings[playerChoice].charAt(0) - 1));
				else Settings[playerChoice] = String.valueOf( (char) (Settings[playerChoice].charAt(0) + 1));
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
				applyBorder(descriptions[i], 60, i * 50 + 200, new Color(0, 255, 255));
				uniFont.drawString(60, i * 50 + 200, descriptions[i], chosen);
			} else {
				applyBorder(descriptions[i], 60, i * 50 + 200, new Color(105, 2, 2));
				uniFont.drawString(60, i * 50 + 200, descriptions[i], notChosen);
			}
		}
    }
	
	private void renderPlayersOptions(){
        for (int i = 0; i < NOCHOICES; i++) {
    		int center = ((700-300)/2)+300-uniFont.getWidth(Settings[i])/2 +redLeftTriangles[i].getWidth()/2;
            if (playerChoice == i) {
            	 applyBorder(Settings[i], center, i * 50 + 200, new Color(0, 255, 255));
            	 uniFont.drawString(center, i * 50 + 200, Settings[i], chosen);
            } else {
            	applyBorder(Settings[i], center, i * 50 + 200, new Color(105, 2, 2));
            	uniFont.drawString(center, i * 50 + 200, Settings[i], notChosen);
            }
        }
    }
	@SuppressWarnings("unchecked")
    public void initFont() {
    	try{
    		UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
    				org.newdawn.slick.util.ResourceLoader.getResourceAsStream(
    						System.getProperty("user.dir") + "/resource/font/joystix_monospace.ttf"
    						));
    		UIFont1 = UIFont1.deriveFont(java.awt.Font.ITALIC, 42.f); 

    		uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
    		uniFont.addAsciiGlyphs();
    		uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
    		uniFont.addAsciiGlyphs();
    		uniFont.loadGlyphs();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private void applyBorder(String s, int x, int y, Color c) {    	
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
	
}
