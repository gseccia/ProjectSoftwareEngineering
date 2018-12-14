package main.gamestates;

import main.ResourceManager;
import managers.MusicManager;
import missions.Mission;

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
        isPaused = false;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    	if (stateBasedGame.getCurrentStateID() == this.id) {    		
	        background.draw(0, 0);
	        String text = mission.toString() + "Press Escape to go back to Menu";

//	        formatStringIntoCanvas(text, gameContainer);   non funziona rip
	        applyBorder(text, 20, 20, new Color(105, 2, 2));
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
    		UIFont1 = UIFont1.deriveFont(java.awt.Font.PLAIN, 25.f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 30.f is the size of your font

    		uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
    		uniFont.addAsciiGlyphs();
    		uniFont.getEffects().add(new ColorEffect(java.awt.Color.red)); //You can change your color here, but you can also change it in the render{ ... }
    		uniFont.addAsciiGlyphs();
    		uniFont.loadGlyphs();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
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
    
    private void formatStringIntoCanvas(String s, GameContainer gameContainer) {
    	System.out.println(s);
        int width = (Long.valueOf(Math.round(gameContainer.getWidth())).intValue());
    	String formattedString = "";
        if (uniFont.getWidth(s) > width) {
        	for (String subString : s.split(" ")) {
        		String tempString = formattedString+subString;
        		if (uniFont.getWidth(tempString) < width) {
        			formattedString += subString;
        		}
        		else {
        			uniFont.drawString(0, 0, formattedString, new Color(201, 2, 2));
        			formattedString = "";
        		}
        	}
        }
//    	System.out.println(formattedString);
    }
}
