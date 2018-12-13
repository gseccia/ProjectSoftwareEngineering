package blocks;

import main.GameStates;
import main.ResourceManager;
import managers.MusicManager;
import missions.Mission;
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

    private ResourceManager rs;
	private MusicManager mm;
	private boolean isPaused;
	
	//Fonts
	java.awt.Font UIFont1;
	org.newdawn.slick.UnicodeFont uniFont;

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
        this.background = new Image(System.getProperty("user.dir") + "/resource/textures/screens/pauseBg.png");
        initFont();
//        isPaused = false;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    	if (stateBasedGame.getCurrentStateID() == this.id) {
//    		TODO fermare la musica durante la pausa
//    		if (!isPaused) {
//    			isPaused = true;
//    			this.rs.setState(4);
//    		}
	        background.draw(0, 0);
	        uniFont.drawString(20, 20, mission.toString() + "\nPress Escape to go back to Menu", new Color(201, 2, 2));
    	}
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();
        if(input.isKeyPressed(Input.KEY_P)){
//        	this.rs.setState(5);
            stateBasedGame.enterState(originState);        	
//            isPaused=false;
        }
        if(input.isKeyPressed(Input.KEY_ESCAPE)){
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
    @SuppressWarnings("unchecked")
    public void initFont() {
    	try{
    		UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
    				org.newdawn.slick.util.ResourceLoader.getResourceAsStream(
    						System.getProperty("user.dir") + "/resource/font/joystix_monospace.ttf"
    						));
    		UIFont1 = UIFont1.deriveFont(java.awt.Font.BOLD, 25.f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 30.f is the size of your font

    		uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
    		uniFont.addAsciiGlyphs();
    		uniFont.getEffects().add(new ColorEffect(java.awt.Color.red)); //You can change your color here, but you can also change it in the render{ ... }
    		uniFont.addAsciiGlyphs();
    		uniFont.loadGlyphs();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
