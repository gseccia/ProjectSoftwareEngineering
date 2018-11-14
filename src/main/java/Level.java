package main.java;

import elements.Mob;
import map.Block;
import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Level extends StateBasedGame{
	private Mob player;
	private Block current_block; //Only to TEST
	
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
	public Level(String gamename,String charname,int difficulty) throws SlickException {
		super(gamename);
		
		population = new HashMap<Block,Set<Mob>>();
		///ONLY TO TEST
		map = new HashSet<Block>();
		current_block = new Block(1,"resource/maps/CompleteLab/Lab.tmx");
		map.add(current_block);
		////
		player = Mob.generate(charname);
		//player.setLocation(0,0);
		generatePopulation(difficulty);
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
			mob = Mob.generate("guntan");  //Retrive other String id
			mobs.add(mob);
		}
		return mobs;
	}



	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		for(Block block: map)
		{
			this.addState(block);
		}
		
		this.enterState(1); //always enter in first block
	}




}
