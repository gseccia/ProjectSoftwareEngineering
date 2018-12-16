package main;

import blocks.Block;
import blocks.BlockFactory;
import configuration.*;
import elements.*;
import main.gamestates.Pause;
import managers.observers.scoreboard.ScorePointsManager;
import map.MapGraph;
import missions.Mission;
import missions.MissionFactory;
import missions.NotEnoughMissionsException;

import java.util.*;

import org.newdawn.slick.SlickException;
import utils.RandomCollection;
import visitors.HealPlayerModifier;

public class Level{

	private int level_difficulty, itemCapacity = 0;
	private List<Block> block_list;
	private Map<Block, Integer> itemsRemainingCapacity;
	private Map<Block, Integer> mobsRemainingCapacity;
	private Map<Block,Set<Enemy>> population;
	private Map<Block,Set<Item>> items;
	private MissionFactory missions;

	/**
	 * This class is the manager of a level
	*/
	Level(String charname, int level_difficulty, MissionFactory missions, BlockFactory blockFactory) {

		itemsRemainingCapacity = new HashMap<>();
		mobsRemainingCapacity = new HashMap<>();
		population = new HashMap<>();
		items = new HashMap<>();
		this.missions = missions;

		MapGraph map = new MapGraph(level_difficulty, MapConfiguration.getInstance(), blockFactory);
		try {
			map.generateGraph();
		} catch (NoSuchElementInConfigurationException e) {
			e.printStackTrace();
		}

		block_list = map.generateBlock();
		MapConfiguration conf = MapConfiguration.getInstance();

		int mobCapacity = 0, tmpItem, tmpMobs;
		for(Block b : block_list) {
			try {
				tmpItem = conf.getItemCapacity(b.getMapName());
				tmpMobs = conf.getMobCapacity(b.getMapName());

				itemCapacity += tmpItem;
				itemsRemainingCapacity.put(b, tmpItem);

				mobCapacity += tmpMobs;
				mobsRemainingCapacity.put(b, tmpMobs);

			} catch (NoSuchElementInConfigurationException e) {
				e.printStackTrace();
			}
		}

		this.level_difficulty = level_difficulty;

		try {
			ScorePointsManager spm = ScorePointsManager.getScorePointsManagerInstance();

			Player player = new Player(PlayerConfiguration.getInstance(), charname);

			Mission mission_generated = missions.generateMissions(itemCapacity/2, mobCapacity/2, level_difficulty);

			distributeItems();
			distributeMobs(player);

			for(Block block: block_list) {
				block.initBlock(player, population, items, map, mission_generated, spm);
			}

			Pause.setMission(mission_generated);

		} catch (NoSuchElementInConfigurationException | NullAnimationException | SlickException | NotEnoughMissionsException e) {
			e.printStackTrace();
			System.out.println("CONFIGURATION ERROR");

		}
		

	}

	private void distributeMobs(Player player) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
		RandomCollection<Block> blocks = new RandomCollection<>(block_list);
		Block b;
		for(Enemy e : missions.targetEnemies()){
			b = blocks.getRandom();
			e.setMap(b);
			e.setPlayer(player);

			addEnemyToBlock(b, e);
			updateCapacity(b, blocks, mobsRemainingCapacity);
		}

		RandomCollection<String> mobNames = new RandomCollection<>(EnemyConfiguration.getInstance().getMobNames());
		Enemy e;
		while(blocks.size() > 0){
			b = blocks.getRandom();
			e = new Enemy(EnemyConfiguration.getInstance(), mobNames.getRandom(), b, player);
			addEnemyToBlock(b, e);
			updateCapacity(b, blocks, mobsRemainingCapacity);
		}
	}

	private void distributeItems() throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
		ItemConfiguration conf = ItemConfiguration.getInstance();

		RandomCollection<Block> blocks = new RandomCollection<>(block_list);
		Block b;
		for(Item i : missions.targetItems()){
			b = blocks.getRandom();
			addItemToBlock(b, i);
			updateCapacity(b, blocks, itemsRemainingCapacity);

		}

		int numHearts = (itemCapacity-missions.targetItems().size())/level_difficulty;
		for(int i=0; i<numHearts; i++){
			b = blocks.getRandom();
			addItemToBlock(b, new Item(conf, "heart", new HealPlayerModifier(conf.getItemPoints("heart")), false));
			updateCapacity(b, blocks, itemsRemainingCapacity);
		}

		RandomCollection<String> itemNames = new RandomCollection<>(conf.getMissionItemNames());
		Item i;
		while(blocks.size() > 0){
			b = blocks.getRandom();
			i = new Item(conf, itemNames.getRandom());

			addItemToBlock(b, i);
			updateCapacity(b, blocks, itemsRemainingCapacity);
		}
	}

	private void updateCapacity(Block b, List<Block> blocks, Map<Block, Integer> remainingCapacity){
		int remaining = remainingCapacity.get(b);
		if(remaining <= 1){
			blocks.remove(b);
		}
		else{
			remaining--;
			remainingCapacity.put(b, remaining);
		}
	}

	private void addItemToBlock(Block b, Item i){
		if(items.get(b) == null){
			Set<Item> tmp = new HashSet<>();
			tmp.add(i);
			items.put(b, tmp);
		}

		else{
			items.get(b).add(i);
		}

	}

	private void addEnemyToBlock(Block b, Enemy e){
		if(population.get(b) == null){
			Set<Enemy> tmp = new HashSet<>();
			tmp.add(e);
			population.put(b, tmp);
		}

		else{
			population.get(b).add(e);
		}
	}

	public List<Block> getBlocks(){
		return this.block_list;
	}

}
