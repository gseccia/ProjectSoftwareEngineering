package main;

import attacks.FireSpearAttack;
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
import visitors.PlayerModifier;
import visitors.SetAttackModifier;

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
	Level(Player player, String charname, int level_difficulty, MissionFactory missions, BlockFactory blockFactory) {

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

			Mission mission_generated = missions.generateMissions(itemCapacity/2, mobCapacity/2, level_difficulty);

			distributeItems(player);
			distributeMobs(player);

			for(Block block: block_list) {
				block.initBlock(player, population, items, map, mission_generated, spm);
				block.setLevelNumber(level_difficulty);
				block.setCharName(charname);
			}

			Pause.setMission(mission_generated);

		} catch (NoSuchElementInConfigurationException | NullAnimationException | SlickException | NotEnoughMissionsException e) {
			e.printStackTrace();
			System.out.println("CONFIGURATION ERROR");

		}
		

	}
	
	/**
	 * Distribute mobs into blocks
	 * @param player Player object
	 */
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
	
	/**
	 * Distribute items into the blocks
	 * @param player Player object
	 */
	private void distributeItems(Player player) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
		ItemConfiguration conf = ItemConfiguration.getInstance();

		RandomCollection<Block> blocks = new RandomCollection<>(block_list);
		Block b;
		for(Item i : missions.targetItems()){
			b = blocks.getRandom();
			addItemToBlock(b, i);
			updateCapacity(b, blocks, itemsRemainingCapacity);

		}

		int numNPC = level_difficulty/3 + 1;
		int i = 0;
		RandomCollection<String> NPCNames = new RandomCollection<>(conf.getNPCNames());
		SoundConfiguration soundConfiguration = SoundConfiguration.getInstance();
		while(numNPC <= itemCapacity && i < numNPC){
			PlayerModifier visitor;
			String NPCId = NPCNames.getRandom();
			if(new Random().nextInt()%2 == 0){
				visitor = new SetAttackModifier(new FireSpearAttack(player), soundConfiguration.getPowerUpSound(NPCId), level_difficulty*10, level_difficulty+1);
			}
			else {
				visitor = new HealPlayerModifier(player.getMaxHp()*3/4, soundConfiguration.getHealSound(NPCId));
			}
			Item npc = new Item(conf, NPCId, visitor, false);
			b = blocks.getRandom();
			addItemToBlock(b, npc);
			updateCapacity(b, blocks, itemsRemainingCapacity);
			i++;
		}

		int numHearts = (itemCapacity - numNPC - missions.targetItems().size())/level_difficulty;
		for(i=0; i<numHearts; i++){
			b = blocks.getRandom();
			addItemToBlock(b, new Item(conf, "heart", new HealPlayerModifier(conf.getItemPoints("heart")), false));
			updateCapacity(b, blocks, itemsRemainingCapacity);
		}

		RandomCollection<String> itemNames = new RandomCollection<>(conf.getMissionItemNames());
		Item item;
		while(blocks.size() > 0){
			b = blocks.getRandom();
			item = new Item(conf, itemNames.getRandom());

			addItemToBlock(b, item);
			updateCapacity(b, blocks, itemsRemainingCapacity);
		}
	}
	
	/**
	 * Updated the remaining capacity of a block
	 * @param b Block which remaining capacity has to be updated
	 * @param blocks List of blocks
	 * @param remainingCapacity Mapping block with their remaing capacity
	 */
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
	
	/**
	 * Add an item into a block
	 * @param b Block where is inserted the item
	 * @param i Item to add
	 */
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
	
	/**
	 * Add an enemy into a block
	 * @param b Block where is inserted the item
	 * @param e Enemy to add
	 */
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
