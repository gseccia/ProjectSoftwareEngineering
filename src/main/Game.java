package main;

import blocks.Block;
import blocks.Menu;
import managers.MusicManager;
import managers.observers.scoreboard.Observer;
import managers.observers.scoreboard.SubjectInterface;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

public class Game extends StateBasedGame{

    private Level current;
    private int current_difficulty;
    private String charname;
    private ResourceManager rs;
    private MusicManager mm;

    public Game(String name, String charname) {
        super(name);
        this.charname = charname;
        current_difficulty = 0;
        this.rs = ResourceManager.getInstance();
        mm = MusicManager.getInstance(this.rs);
    }

    @Override
    public void initStatesList(GameContainer gameContainer){
        System.out.println(current_difficulty);
        if(current != null) clearLevel();
        // Instantiate block only when the level is instantiated
        if (current_difficulty > 0) {
        	current = new Level(charname, current_difficulty);
        	List<Block> blocks = current.getBlocks();
        	for(Block block: blocks) {
        		this.addState(block);
        	}
        }
        
        current_difficulty++;
        
        // for menu
        this.addState(Menu.getInstance());
        // for splash
        this.addState(new SplashScreen());
        // for game over page
		this.addState(new GameOver());
        
//        this.rs.setState(0);
        this.enterState(GameStates.SPLASHSCREEN.getState()); //always enter in splash screen state
    }

    private void clearLevel(){
        for(Block block : current.getBlocks()){
            block.clearBlock();
        }
    }

    /**
     * Resets the difficulty to 1
     */
    public void resetDifficulty(){
        current_difficulty = 1;
    }
}
