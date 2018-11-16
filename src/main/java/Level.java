package main.java;

import elements.Mob;
import elements.NullAnimationException;
import map.Block;
import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Level extends StateBasedGame{
	private Mob player;
	private Block current_block; //Only to TEST
	private String charname;
	private int level_difficulty;
	
	
	private Set<Block> map; //DO substitution with graph
	private Map<Block,Set<Mob>> population;
	/*
	private GraphTransitionManager map;
	private Set<Mob> element_set;
	*/
	
	/**
	 * This class is the manager of a level
	 * @throws SlickException 
	*/
	public Level(String gamename,String charname,int level_difficulty) throws SlickException {
		super(gamename);
		
		population = new HashMap<Block,Set<Mob>>();
		
		///ONLY TO TEST
		map = new HashSet<Block>();
		current_block = new Block(1,"Library2");
		map.add(current_block);
		//ONLY TEST END
		
		this.charname = charname;
		this.level_difficulty =level_difficulty;
		//player.setLocation(0,0);
	}
	
	
	/**
	 * This function automatically generate Mobs and put them into blocks
	 * @param difficulty parameter to define the hardness of the level
	 * @throws SlickException 
	 */
	private void generatePopulation(int difficulty) throws SlickException
	{
		for(Block block: map)
		{
			population.put(block, generateMob(difficulty));
		}
	}
	
	/**
	 * This function automatically generate a set of Mobs
	 * @param difficulty parameter to define the hardness of the level
	 * @return A set of Mob that are randomically generated
	 */
	private Set<Mob> generateMob(int difficulty) throws SlickException
	{
		Set<Mob> mobs=new HashSet<Mob>();
		Mob mob;
		for(int i=0;i<difficulty;i++)
		{
			try {
				mob = Mob.generate("zombo");  //Retrive other String id
				mobs.add(mob);
			} catch (NullAnimationException e) {
				e.printStackTrace();
				System.out.println("CONFIGURATION ERROR"); //TODO: Display a message on screen
			}
		}
		return mobs;
	}



	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		try {
			player = Mob.generate(charname);
			generatePopulation(level_difficulty);
			for(Block block: map)
			{
				block.initBlock(player, population);
				this.addState(block);
			}

			this.enterState(1); //always enter in first block
		} catch (NullAnimationException e) {
			e.printStackTrace();
			System.out.println("CONFIGURATION ERROR"); //TODO: Display a message on screen
		}
	}




}
