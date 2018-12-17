package main;

import blocks.*;
import configuration.EnemyConfiguration;
import configuration.ItemConfiguration;
import main.gamestates.CharacterSelection;
import main.gamestates.GameOver;
import main.gamestates.GameStates;
import main.gamestates.Menu;
import main.gamestates.Pause;
import main.gamestates.Scores;
import main.gamestates.Settings;
import main.gamestates.SplashScreen;
import managers.MusicManager;
import managers.observers.scoreboard.ScoreFileObserver;
import managers.observers.scoreboard.ScorePointsManager;
import missions.ConcreteMissionFactory;
import missions.DemoMissionFactory;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;

public class Game extends StateBasedGame{

    private boolean demo = false;
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
    
    public void setCharacter(String charname) {
    	this.charname = charname;
    }

    @Override
    public void initStatesList(GameContainer gameContainer){
        // Instantiate block only when the level is instantiated
        if (current_difficulty > 0) {
            if(demo) {
                current = new Level(charname, current_difficulty, new DemoMissionFactory(ItemConfiguration.getInstance(), EnemyConfiguration.getInstance()), new DemoBlockFactory());
            }
            else{
                current = new Level(charname, current_difficulty, new ConcreteMissionFactory(ItemConfiguration.getInstance(), EnemyConfiguration.getInstance()), new ConcreteBlockFactory());
            }
        	List<Block> blocks = current.getBlocks();
        	for(Block block: blocks) {
        		this.addState(block);
        	}

            // for pause
            this.addState(Pause.getInstance());
        }
        
        current_difficulty++;

        // for menu
        this.addState(Menu.getInstance());
        // for scores
        this.addState(Scores.getInstance(ScoreFileObserver.getInstance(ScorePointsManager.getScorePointsManagerInstance()), rs));
        // for splash
        this.addState(new SplashScreen());
        // for game over page
		this.addState(new GameOver());
		// for options page
		this.addState(Settings.getInstance());
		// for character selection page
		this.addState(new CharacterSelection(rs));
//        this.rs.setState(0);
        this.enterState(GameStates.SPLASHSCREEN.getState()); //always enter in splash screen state
    }

    /**
     * Resets the difficulty to 1
     */
    public void resetDifficulty(){
        current_difficulty = 1;
    }

    public void setDemo(boolean demo) {
        this.demo = demo;
    }
}
