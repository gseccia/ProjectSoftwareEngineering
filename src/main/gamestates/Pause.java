package main.gamestates;

import managers.MusicManager;
import managers.ResourceManager;
import missions.Mission;

import java.io.IOException;
import java.text.Normalizer.Form;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Pause extends BasicGameState {

    private final int id = GameStates.PAUSE.getState();
    private Image background;

    private static int originState;
    private static Mission mission;
    private static Pause instance;
    private UnicodeFont uniFont;
    private ResourceManager rs;
	private MusicManager mm;
	private boolean isPaused;

    public static Pause getInstance(){
        if(instance == null){
            instance = new Pause();
        }
        return instance;
    }

    private Pause() {
        this.rs = ResourceManager.getInstance();
        this.mm = MusicManager.getInstance(this.rs);
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
			this.background = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/screens/pauseBg.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
        uniFont = StatesUtils.initFont();
        isPaused = false;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    	if (stateBasedGame.getCurrentStateID() == this.id) {    		
	        background.draw(0, 0);
	        String text = mission.toString() + "Press Escape to go back to Menu";
	        StatesUtils.applyBorder(uniFont, text, 20, 20, new Color(105, 2, 2));
	        uniFont.drawString(20, 20, text, new Color(201, 2, 2));
    	}
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();
//        HANDLING PAUSE MUSIC
        if (!isPaused && stateBasedGame.getCurrentStateID() == GameStates.PAUSE.getState()) {
			isPaused = true;
			this.rs.setState(-1);
		}
        if(input.isKeyPressed(Input.KEY_P)){
        	this.rs.setState(-1);        	
            isPaused=false;
//          HANDLING PAUSE MUSIC
            stateBasedGame.enterState(originState);
        }
        if(input.isKeyDown(Input.KEY_ESCAPE)){
        	this.rs.setState(0);
            stateBasedGame.enterState(GameStates.MENU.getState());
        }
    }

    @Override
    public int getID() {
        return id;
    }

    public static void setOriginState(int state){
        originState = state;
    }

    public static void setMission(Mission mission) {
        Pause.mission = mission;
    }
    
}
