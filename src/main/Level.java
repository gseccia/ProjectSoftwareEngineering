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

	private int level_difficulty;
	private List<Block> block_list;
	private Map<Block,Set<Enemy>> population;
	private Map<Block,Set<Item>> items;
	private Mission mission_generated;

	/**
	 * This class is the manager of a level
	*/
	Level(String charname, int level_difficulty) {

		population = new HashMap<>();
		items = new HashMap<>();

		MapGraph map = new MapGraph(level_difficulty, MapConfiguration.getInstance(), new ConcreteBlockFactory());
		try {
			map.generateGraph();
		} catch (NoSuchElementInConfigurationException e) {
			e.printStackTrace();
		}

		block_list = map.generateBlock();
		MapConfiguration conf = MapConfiguration.getInstance();

		int mobCapacity = 0, itemCapacity = 0;
		for(Block b : block_list) {
			try {
				itemCapacity += conf.getItemCapacity(b.getMapName());
				mobCapacity += conf.getMobCapacity(b.getMapName());
			} catch (NoSuchElementInConfigurationException e) {
				e.printStackTrace();
			}
		}

		int capacity = mobCapacity + itemCapacity;

		MissionsFactory missions = new MissionsFactory(capacity * 3 / 4, level_difficulty, EnemyConfiguration.getInstance(), ItemConfiguration.getInstance());

		this.level_difficulty = level_difficulty;

		try {
			Player player = new Player(PlayerConfiguration.getInstance(), charname);

			mission_generated = missions.generateMissions();
			int extra_mobs = mobCapacity - mission_generated.getEnemySet().size();
			int extra_items = itemCapacity - mission_generated.getItemSet().size();

			System.out.println(mission_generated.getEnemySet().size() + " " + mobCapacity);

			generatePopulation(extra_mobs, player); // level_difficulty
			generateItems(extra_items);
			ScorePointsManager spm = ScorePointsManager.getScorePointsManagerInstance();

			distribute(player);

			for(Block block: block_list) {
				block.initBlock(player, population, items, map, mission_generated, spm);
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
				mob = new Enemy(EnemyConfiguration.getInstance(),"zombo", b, player);  //Retrieve other String id
				mobs.add(mob);
			} catch (NullAnimationException | NoSuchElementInConfigurationException e) {
				e.printStackTrace();
				System.out.println("CONFIGURATION ERROR"); //TODO: Display a message on screen
			}
		}
		return mobs;
	}
	
	private Set<Item> generateItem(int difficulty) throws NullAnimationException, SlickException, NoSuchElementInConfigurationException{
		HashSet<Item> item = new HashSet<>();
		RandomCollection<String> itemNames = new RandomCollection<>(ItemConfiguration.getInstance().getItemNames());

		for(int i=0;i<difficulty;i++) {
			item.add(new Item(ItemConfiguration.getInstance(), itemNames.getRandom()));
		}
		return item;
	}
	
	private void generateItems(int difficulty) throws NullAnimationException, SlickException, NoSuchElementInConfigurationException {
		for(Block block: block_list)
		{
			items.put(block, generateItem(difficulty/block_list.size()));
		}
	}

	public List<Block> getBlocks(){
		return this.block_list;
	}

}
