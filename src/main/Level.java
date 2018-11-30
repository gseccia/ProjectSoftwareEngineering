package main;

import configuration.DoorsConfiguration;
import configuration.ItemConfiguration;
import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Enemy;
import elements.Item;
import elements.NullAnimationException;
import elements.Player;
import managers.observers.scoreboard.ScorePointsManager;
import map.MapGraph;
import missions.Mission;
import missions.MissionsFactory;
import missions.NotEnoughMissionsException;

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
	private Map<Block,Set<Item>> items;
	private MissionsFactory missions;
	private Mission mission_generated;
	private ScorePointsManager spm;
	
	/**
	 * This class is the manager of a level
	*/
	public Level(String gamename,String charname,int level_difficulty) {
		super(gamename);
		
		population = new HashMap<>();
		items = new HashMap<>();
		
		map = new MapGraph(level_difficulty, new DoorsConfiguration());
		try {
			map.generateGraph();
		} catch (NoSuchElementInConfigurationException e) {
			e.printStackTrace();
		}
		block_list = map.generateBlock();
		
		int capacity = 10;
		/*for(Block e: block_list) {
			capacity += Integer.parseInt(e.getMap().getMapProperty("capacity","1"));
		}*/
		
		missions = new MissionsFactory(capacity,level_difficulty,MobConfiguration.getEnemyInstance(),ItemConfiguration.getInstance());
		
		try {
			mission_generated = missions.generateMissions();
			
			System.out.println(mission_generated);
		} catch (NotEnoughMissionsException e1) {
			e1.printStackTrace();
		}
		
		this.charname = charname;
		this.level_difficulty = level_difficulty;
	}
	
	
	private void distribute(Player player) {
		int b;
		if(mission_generated.getEnemySet() != null){
			Iterator<Enemy> i = mission_generated.getEnemySet().iterator();
			b = 0;
			Enemy e;
			while(i.hasNext()){
				e = i.next();
				e.setMap(block_list.get(b));
				e.setPlayer(player);
				population.get(block_list.get(b)).add(e);
				b = (b+1)%block_list.size();
			}
		}
		
		if(mission_generated.getItemSet() != null) {
			Iterator<Item> iter_item = mission_generated.getItemSet().iterator();
			b = 0;
			while(iter_item.hasNext()){
				items.get(block_list.get(b)).add(iter_item.next());
				b = (b+1)%block_list.size();
			}
		}
		
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
	
	
	private void generateItems() {
		for(Block block: block_list)
		{
			items.put(block, new HashSet<>());
		}
	}



	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		try {
			Player player = new Player(MobConfiguration.getPlayerInstance(), charname);
			generatePopulation(1,player); // level_difficulty
			generateItems();
			spm = ScorePointsManager.getScorePointsManagerInstance();
			
			distribute(player);
			for(Block block: block_list)
			{
				block.initBlock(player, population, items,map,mission_generated, spm);
				this.addState(block);
			}

			this.enterState(1); //always enter in first block
		} catch (NullAnimationException | NoSuchElementInConfigurationException e) {
			e.printStackTrace();
			System.out.println("CONFIGURATION ERROR"); //TODO: Display a message on screen
		}
	}




}
