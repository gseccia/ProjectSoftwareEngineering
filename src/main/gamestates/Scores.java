package main.gamestates;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import main.ResourceManager;
import managers.observers.scoreboard.Score;
import managers.observers.scoreboard.ScoreFileObserver;

public class Scores extends BasicGameState {

	private final int id = 1;
	private Image background;
	private static Scores instance;
	private ScoreFileObserver scoreboard;
	private UnicodeFont uniFont;
    private ResourceManager rs;
    private Color fontColor = new Color (255, 0, 255);
	private Color borderColor = new Color(105, 2, 105);
    
    public static Scores getInstance(ScoreFileObserver scoreboard, ResourceManager rs) {
    	if(instance == null)
    		instance = new Scores(scoreboard, rs);
    	return instance;
    }
    
    private Scores(ScoreFileObserver scoreboard, ResourceManager rs) {
    	this.scoreboard = scoreboard;
    	this.rs = rs;
    }
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		try {
			this.background = StatesUtils.loadImage(System.getProperty("user.dir") + "/resource/textures/screens/scoreBg.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		uniFont = StatesUtils.initFont();
		uniFont = StatesUtils.changeSizeAndStyle(uniFont, 40f, java.awt.Font.ITALIC);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
    	int i = 0;
		if(arg1.getCurrentStateID() == id) {
			background.draw(0, 0);
			ArrayList<Score> scores = scoreboard.getScores();
			for(Score s : scores) {
//				CENTER ALIGNMENT
//				StatesUtils.applyBorder(uniFont, s.toString(), arg0.getWidth()/2 - uniFont.getWidth(s.toString())/2, 150+i*100, 
//						borderColor);
//				uniFont.drawString(arg0.getWidth()/2 - uniFont.getWidth(s.toString())/2, 150+i*100, s.toString(), fontColor);
//				LEFT ALIGNMENT
				StatesUtils.applyBorder(uniFont, s.toString(), 60, 200+i*50, 
						borderColor);
				uniFont.drawString(60, 200+i*50, s.toString(), fontColor);
				i++;
			}
			StatesUtils.applyBorder(uniFont, "Press esc to go back", 44, 600, new Color(105, 2, 2));
			uniFont.drawString(44, 600, "Press esc to go back", new Color(201, 2, 2));
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		Input input = arg0.getInput();
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
        	//this.rs.setState(0);
            arg1.enterState(GameStates.MENU.getState());
        }
	}

	@Override
	public int getID() {
		return id;
	}

}
