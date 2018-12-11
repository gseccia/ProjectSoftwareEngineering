package main;

import blocks.Block;
import blocks.ConcreteBlockFactory;
import configuration.*;
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

import org.newdawn.slick.SlickException;
import utils.RandomCollection;

public class Level{
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
	public Level(String charname,int level_difficulty) {

		population = new HashMap<>();
		items = new HashMap<>();
		
		map = new MapGraph(level_difficulty, new DoorsConfiguration(), new ConcreteBlockFactory());
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
		
		missions = new MissionsFactory(capacity,level_difficulty, EnemyConfiguration.getInstance() ,ItemConfiguration.getInstance());

		this.charname = charname;
		this.level_difficulty = level_difficulty;


		try {
			Player player = new Player(PlayerConfiguration.getInstance(), charname);
			generatePopulation(level_difficulty,player); // level_difficulty
			generateItems();
			spm = ScorePointsManager.getScorePointsManagerInstance();

			mission_generated = missions.generateMissions();
			RandomCollection<String> itemNames = new RandomCollection<>(ItemConfiguration.getInstance().getItemNames());
			distribute(player);
			for(Block block: block_list) {
				for(int i=0;i<population.get(block).size()/2;i++) {
					items.get(block).add(new Item(ItemConfiguration.getInstance(),itemNames.getRandom()));
				}
				block.initBlock(player, population, items,map,mission_generated, spm);
			}
		} catch (NoSuchElementInConfigurationException | NullAnimationException | SlickException | NotEnoughMissionsException e) {
			e.printStackTrace();
			System.out.println("CONFIGURATION ERROR"); //TODO: Display a message on screen

		}

	}


	
	
	private void distribute(Player player) {

		int b;
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

		Iterator<Item> iter_item = mission_generated.getItemSet().iterator();
		b = 0;
		while(iter_item.hasNext()){
			items.get(block_list.get(b)).add(iter_item.next());
			b = (b+1)%block_list.size();
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
				mob = new Enemy(EnemyConfiguration.getInstance(),"zombo",b,player);  //Retrieve other String id
				mobs.add(mob);
			} catch (NullAnimationException | NoSuchElementInConfigurationException e) {
				e.printStackTrace();
				System.out.println("CONFIGURATION ERROR"); //TODO: Display a message on screen
			}
		}
		return mobs;
	}
	
	private Set<Item> generateItem() throws NullAnimationException, SlickException, NoSuchElementInConfigurationException{
		HashSet<Item> item = new HashSet<>();
		
		for(int i=0;i<level_difficulty/3;i++) {
			item.add(new Item(ItemConfiguration.getInstance(),"pizza"));
		}
		return item;
	}
	
	private void generateItems() throws NullAnimationException, SlickException, NoSuchElementInConfigurationException {
		for(Block block: block_list)
		{
			items.put(block, generateItem());
		}
	}

	public List<Block> getBlocks(){
		return this.block_list;
	}

}
