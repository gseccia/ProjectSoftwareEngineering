package main.gamestates;

import java.io.IOException;

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
import managers.MusicManager;
import managers.ResourceManager;
import managers.observers.scoreboard.PointsAccumulatorObserver;
import managers.observers.scoreboard.ScorePointsManager;

public class GameOver extends BasicGameState{

    private final int id = GameStates.GAMEOVER.getState();
    /** The background image to be displayed */
    private Image image;
    private ResourceManager rs;
    private ScorePointsManager pointsManager;
	private MusicManager mm;
	private UnicodeFont uniFont;
	private PointsAccumulatorObserver points;
	private boolean startMusic;
	
	private static final int SIZE = 3;
	private Image[] downRedTriangles = new Image[SIZE];
	private Image[] upRedTriangles = new Image[SIZE];
	private Image[] downBlackTriangles = new Image[SIZE];
	private Image[] upBlackTriangles = new Image[SIZE];
	private char[] playerName = new char[SIZE];
	private int choice = 0;
	private Color notChosen = new Color(201, 2, 2);
    private Color chosen = new Color(0,0,0);
    private String player = "";
    private boolean demo;
    private int wait;
	
	public GameOver(boolean demo) {
		this.pointsManager = ScorePointsManager.getScorePointsManagerInstance();
        this.rs = ResourceManager.getInstance();
        this.mm = MusicManager.getInstance(this.rs);
        points = pointsManager.getPointsAccumulatorObserver();
        this.demo=demo;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		arg0.getInput().clearKeyPressedRecord();
		try {
			this.image = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/screens/gameOver.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i=0; i<3; i++)
			downRedTriangles[i] = new Image(System.getProperty("user.dir") + "/resource/textures/arrows/downArrowRed.png");
		for (int i=0; i<3; i++)
			upRedTriangles[i] = new Image(System.getProperty("user.dir") + "/resource/textures/arrows/upArrowRed.png");
		for (int i=0; i<3; i++)
			downBlackTriangles[i] = new Image(System.getProperty("user.dir") + "/resource/textures/arrows/downArrowBlack.png");
		for (int i=0; i<3; i++)
			upBlackTriangles[i] = new Image(System.getProperty("user.dir") + "/resource/textures/arrows/upArrowBlack.png");
		
		playerName[0] = 'A';
		playerName[1] = 'A';
		playerName[2] = 'A';
		
		startMusic = false;
		wait=0;
		uniFont = StatesUtils.initFont();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
//		if (sbg.getCurrentStateID() == GameStates.GAMEOVER.getState()) {
			g.drawImage(image, 0, 0);
			
			if(!demo) {
			this.renderTriangles(g, gc);
			this.renderPlayerName(g, gc);
			this.renderPoints(g, gc);
			
			
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
			else {
			}
//		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
//		Executors.newSingleThreadScheduledExecutor().schedule(() -> System.exit(0) , 5, TimeUnit.SECONDS); 
//		if (arg1.getCurrentStateID() == GameStates.GAMEOVER.getState()) {
		
			if (!startMusic) {
				startMusic = true;
				player = "";
				this.rs.setState(3);
				arg0.getInput().clearKeyPressedRecord();
				System.out.println("starting gameover music");
			}
			if(demo) {
				((Game)arg1).resetDifficulty();
				this.rs.setState(0);
				wait++;
				if (wait > 50) arg1.enterState(GameStates.MENU.getState());
			}
			else {
			if (arg0.getInput().isKeyPressed(Input.KEY_ENTER)) {
				startMusic = false;
				player = new String(playerName);
				pointsManager.saveNamePlayer(player);
				this.pointsManager.setState(2);
				((Game)arg1).resetDifficulty();
				this.rs.setState(0);
				System.out.println("enter pressed");
				uniFont.destroy();
				
//				reset points
				points.setPoints(-points.getPoints());
				pointsManager.setState(0);
//				
				arg1.enterState(GameStates.MENU.getState());
			}
			if(arg0.getInput().isKeyPressed(Input.KEY_RIGHT)) {
				if (choice == SIZE - 1) {
					choice = 0;
				}else {
					choice++;
				}
			}
			
			if(arg0.getInput().isKeyPressed(Input.KEY_LEFT)) {
				if (choice == 0) {
					choice = SIZE - 1;
				}else {
					choice--;
				}
			}
			if(arg0.getInput().isKeyPressed(Input.KEY_DOWN)) {
				this.changeValue(true, this.choice);
			}
			
			if(arg0.getInput().isKeyPressed(Input.KEY_UP)) {
				this.changeValue(false, this.choice);
			}
			else {
//				System.out.println("nothing");
			}
			}
//		}
	}

	@Override
	public int getID() {
		return id;
	}
	
	private void renderPlayerName(Graphics g, GameContainer gc) {
    	for (int i = 0; i < SIZE; i++) {
    		if(choice == i) {
    			StatesUtils.applyBorder(uniFont, String.valueOf(playerName[i]), (gc.getWidth()/2 - uniFont.getWidth("A")/2 -100) + 100*i, 530, new Color(0,255,255));
    			uniFont.drawString((gc.getWidth()/2 - uniFont.getWidth("A")/2 -100) + 100*i, 530, String.valueOf(playerName[i]), chosen);
    		}else {
    			StatesUtils.applyBorder(uniFont, String.valueOf(playerName[i]), (gc.getWidth()/2 - uniFont.getWidth("A")/2 -100) + 100*i, 530, new Color(105, 2, 2));
    			uniFont.drawString((gc.getWidth()/2 - uniFont.getWidth("A")/2 -100) + 100*i, 530, String.valueOf(playerName[i]), notChosen);
    		}
    		
        }
    }
	
	private void renderTriangles(Graphics g, GameContainer gc) {
    	for (int i = 0; i <SIZE; i++) {
    		if(choice == i) {
    			g.drawImage(upBlackTriangles[i], (gc.getWidth()/2 - upBlackTriangles[i].getWidth()/2 -100) + 100*i, 480);
        		g.drawImage(downBlackTriangles[i], (gc.getWidth()/2 - downBlackTriangles[i].getWidth()/2 -100) + 100*i, 580);
    		}
    		else {
    			g.drawImage(upRedTriangles[i], (gc.getWidth()/2 - upRedTriangles[i].getWidth()/2 -100) + 100*i, 480);
        		g.drawImage(downRedTriangles[i], (gc.getWidth()/2 - downRedTriangles[i].getWidth()/2 -100) + 100*i, 580);    			
    		}
    		
    	}
    }
	
	private void renderPoints(Graphics g, GameContainer gc) {
		StatesUtils.applyBorder(uniFont, "Score: "+String.valueOf(points.getPoints()), 
				(gc.getWidth()/2 - uniFont.getWidth("Score: "+String.valueOf(points.getPoints()))/2), 120, new Color(105, 2, 2));
		uniFont.drawString((gc.getWidth()/2 - uniFont.getWidth("Score: "+String.valueOf(points.getPoints()))/2), 120, 
				String.valueOf("Score: "+points.getPoints()), notChosen);
	}
	
	private void changeValue(boolean upKey, int choice) {
		if (playerName[choice] ==  90 && upKey)
			playerName[choice] = 48;
		else if (playerName[choice] ==  57 && upKey)
			playerName[choice] = 65;
		else if (playerName[choice] ==  65 && !upKey)
			playerName[choice] = 57;
		else if (playerName[choice] ==  48 && !upKey)
			playerName[choice] = 90;
		else {
			if (upKey)
				playerName[choice] = (char)(playerName[choice] + 1);
			else
				playerName[choice] = (char)(playerName[choice] - 1);
		}
    }
}

