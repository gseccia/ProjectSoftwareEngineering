package main;

import blocks.Block;
import blocks.Menu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;

public class Game extends StateBasedGame {

    private Menu menu;
    private Level level;
    private int current_difficulty;
    private String charname;

    public Game(String name, String charname) {
        super(name);
        current_difficulty = 1;
        this.charname = charname;

    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        menu = Menu.getInstance();

        level = new Level(charname,current_difficulty);

        // for menu
        this.addState(menu);
        //
        List<Block> blocks = level.getBlocks();
        for(Block block: blocks) {
            this.addState(block);
        }


        // for menu
        this.enterState(0); //always enter in menu block

    }
}
