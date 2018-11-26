package main.java;

import configuration.DoorsConfiguration;
import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Enemy;
import elements.NullAnimationException;
import elements.Player;
import map.MapGraph;

import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Level extends StateBasedGame{
	private String charname;
	private int level_difficulty;
	
	
	private List<Block> block_list;
	private MapGraph map;
	private Map<Block,Set<Enemy>> population;
	
	/**
	 * This class is the manager of a level
	*/
	public Level(String gamename,String charname,int level_difficulty) {
		super(gamename);
		
		population = new HashMap<>();
		
		map = new MapGraph(level_difficulty, new DoorsConfiguration());
		try {
			map.generateGraph();
		} catch (NoSuchElementInConfigurationException e) {
			e.printStackTrace();
		}
		block_list = map.generateBlock();
		
		this.charname = charname;
		this.level_difficulty =level_difficulty;
	}
	
	
	/**
	 * This function automatically generate Mobs and put them into blocks
	 * @param difficulty parameter to define the hardness of the level
	 * @throws SlickException slick exception
	 */
	private void generatePopulation(int difficulty,Player player) throws SlickException
	{
		for(Block block: block_list)
		{
			population.put(block, generateMob(difficulty,block,player));
		}
	}
	
	/**
	 * This function automatically generate a set of Mobs
	 * @param difficulty parameter to define the hardness of the level
	 * @return A set of Mob that are generated at random
	 */
	private Set<Enemy> generateMob(int difficulty,Block b,Player player) throws SlickException
	{
		Set<Enemy> mobs=new HashSet<>();
		Enemy mob;
		for(int i=0;i<difficulty;i++)
		{
			try {
				mob = new Enemy(MobConfiguration.getEnemyInstance(),"zombo",b,player);  //Retrieve other String id
				mobs.add(mob);
			} catch (NullAnimationException | NoSuchElementInConfigurationException e) {
				e.printStackTrace();
				System.out.println("CONFIGURATION ERROR"); //TODO: Display a message on screen
			}
		}
		return mobs;
	}



	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		try {
			Player player = new Player(MobConfiguration.getPlayerInstance(), charname);
			generatePopulation(1,player); // level_difficulty
			for(Block block: block_list)
			{
				block.initBlock(player, population, map);
				this.addState(block);
			}

			this.enterState(1); //always enter in first block
		} catch (NullAnimationException | NoSuchElementInConfigurationException e) {
			e.printStackTrace();
			System.out.println("CONFIGURATION ERROR"); //TODO: Display a message on screen
		}
	}




}
