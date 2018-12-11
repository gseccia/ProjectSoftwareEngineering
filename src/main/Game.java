package main;

import blocks.Block;
import blocks.Menu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;

public class Game extends StateBasedGame {

    private Level current;
    private int current_difficulty;
    private String charname;

    public Game(String name, String charname) {
        super(name);
        this.charname = charname;
        current_difficulty = 1;
    }

    @Override
    public void initStatesList(GameContainer gameContainer){
        Menu menu = Menu.getInstance();

        if(current != null) clearLevel();

        current = new Level(charname, current_difficulty);
        current_difficulty++;

        // for menu
        this.addState(menu);
        //
        List<Block> blocks = current.getBlocks();
        for(Block block: blocks) {
            this.addState(block);
        }


        // for menu
        this.enterState(0); //always enter in menu block
    }

    private void clearLevel(){
        for(Block block : current.getBlocks()){
            block.clearBlock();
        }
    }
}
