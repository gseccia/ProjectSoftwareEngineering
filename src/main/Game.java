package main;

import attacks.ultras.HoraHora;
import attacks.ultras.IUF;
import attacks.ultras.Sparagmos;
import blocks.*;
import configuration.EnemyConfiguration;
import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import configuration.PlayerConfiguration;
import elements.NullAnimationException;
import elements.Player;
import main.gamestates.*;
import managers.MusicManager;
import managers.ResourceManager;
import managers.observers.scoreboard.ScoreFileObserver;
import managers.observers.scoreboard.ScorePointsManager;
import missions.ConcreteMissionFactory;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;

/**
 * This class is the state manager of all game
 */
public class Game extends StateBasedGame{

    private boolean demo = false;
    private Level current;
    private int current_difficulty;
    private String charname, ultra;
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

    public void setUltra(String ultra) {
        this.ultra = ultra;
    }

    private void attachUltra(Player player){
        if(ultra.equals("horahora")) player.setUltra(new HoraHora(player));
        else if(ultra.equals("sparagmos")) player.setUltra(new Sparagmos(player));
        else if(ultra.equals("iuf")) player.setUltra(new IUF(player));
        else player.setUltra(new IUF(player));
    }

    @Override
    public void initStatesList(GameContainer gameContainer){
        // Instantiate block only when the level is instantiated
        if (current_difficulty > 0) {
            try {
                Player player = new Player(PlayerConfiguration.getInstance(), charname);
                attachUltra(player);

                if(demo) {
                    current = new Level(player, charname, current_difficulty, new ConcreteMissionFactory(ItemConfiguration.getInstance(), EnemyConfiguration.getInstance()), new DemoBlockFactory());
                }
                else{
                    current = new Level(player, charname, current_difficulty, new ConcreteMissionFactory(ItemConfiguration.getInstance(), EnemyConfiguration.getInstance()), new ConcreteBlockFactory());
                }
                List<Block> blocks = current.getBlocks();
                for(Block block: blocks) {
                    this.addState(block);
                }

            } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException e) {
                e.printStackTrace();
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
		this.addState(new GameOver(demo));
		// for options page
		this.addState(Settings.getInstance());
		// for character selection page
		this.addState(new CharacterSelection(rs));
        // for skill selection page
        this.addState(new SkillSelection(rs));
//        this.rs.setState(0);
        this.enterState(GameStates.SPLASHSCREEN.getState()); //always enter in splash screen state
    }

    /**
     * Resets the difficulty to 1
     */
    public void resetDifficulty(){
        current_difficulty = 1;
    }

    /**
     * Sets the modality
     * @param demo if asserted indicates demo mode
     */
    public void setDemo(boolean demo) {
        this.demo = demo;
    }
}
