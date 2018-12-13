package missions;

import configuration.EnemyConfiguration;
import configuration.ItemConfiguration;
import elements.Enemy;
import elements.Item;
import utils.RandomCollection;

import java.util.*;

/*
The ID of the missions are:
0 -> KillNEnemies
1 -> CollectNItems
2 -> KillTheBoss
More will be added...

The IDs are internal to this class (not defined anywhere else)
 */

public class ConcreteMissionFactory implements MissionFactory{

    private int itemCapacity, mobCapacity, difficulty, recallDifficulty;
    private EnemyConfiguration enemyConfiguration;
    private ItemConfiguration itemConf;
    private Map<Integer, RandomCollection<String>> availableTargets = new HashMap<>();
    private RandomCollection<Integer> itemMissionsIDs = new RandomCollection<>(List.of(1));
    private RandomCollection<Integer> mobsMissionsIDs = new RandomCollection<>(List.of(0, 2));
    private Mission manager;
    private SetStorageRoom targets;

    /**
     * Constructor of the factory
     * @param enemyConfiguration the instance of EnemyConfiguration
     * @param itemConf the instance of ItemConfiguration
     */
    public ConcreteMissionFactory(ItemConfiguration itemConf, EnemyConfiguration enemyConfiguration) {
        this.enemyConfiguration = enemyConfiguration;
        this.itemConf = itemConf;
        this.manager = new MissionManager();
        targets = new SetStorageRoom();
    }

    /**
     * Generates the missions
     * @param itemCapacity the number of items that can be generated
     * @param mobCapacity the number of mobs that can be generated
     * @param difficulty the number of missions to generate
     * @return an instance of Mission
     * @throws NotEnoughMissionsException if there are no more missions available
     */
    @Override
    public Mission generateMissions(int itemCapacity, int mobCapacity, int difficulty) throws NotEnoughMissionsException {

        this.itemCapacity = itemCapacity;
        this.mobCapacity = mobCapacity;
        this.difficulty = difficulty/3 + 2;
        recallDifficulty = difficulty;

        //Generates items mission
        generation(itemMissionsIDs, itemCapacity, this.difficulty/2);
        this.difficulty -= this.difficulty/2;

        //Generates mobs missions
        generation(mobsMissionsIDs, mobCapacity, this.difficulty);

        manager.produceTargets(targets);

        return manager;
    }

    /**
     * @return the items needed by the missions
     */
    @Override
    public Set<Item> targetItems(){
        return targets.getItemSet();
    }

    /**
     * @return the enemies needed by the missions
     */
    @Override
    public Set<Enemy> targetEnemies(){
        return targets.getEnemySet();
    }

    /**
     * Generate a set of missions, adding them to the mission manager
     * @param targetIDs a RandomCollection of mission ID
     * @param targetCapacity the total number of target to generate
     * @param limit the number of missions to generate
     * @throws NotEnoughMissionsException
     */
    private void generation(RandomCollection<Integer> targetIDs, int targetCapacity, int limit) throws NotEnoughMissionsException {
        int generated = 0;
        while (generated < limit){
            if (targetIDs.size() == 0) throw new NotEnoughMissionsException("Not enough missions!");
            Integer ran = targetIDs.getRandom();

            List<String> targetSet = getMissionIDs(ran);
            Iterator<String> it = targetSet.iterator();
            int used = getMissionCapacity(ran, targetCapacity, (limit-generated));
            boolean flag = true;
            String targetId = "";
            while (it.hasNext() && flag){
                targetId = it.next();
                flag = !manager.add(instantiateMission(ran, targetId, used));
            }
            if (!flag) {
                targetCapacity -= used;
                generated++;
                targetSet.remove(targetId);
            }
            else{
                targetIDs.remove(ran);
            }
        }

    }

    /**
     * @param mID the mission ID
     * @return the IDs of the items/enemies that a certain mission can use
     */
    private List<String> getMissionIDs(int mID){
        switch (mID){
            case 0:
                if(availableTargets.get(0) == null){
                    availableTargets.put(0, new RandomCollection<>(enemyConfiguration.getMobNames()));
                }
                return availableTargets.get(0);

            case 1:
                if(availableTargets.get(1) == null){
                    availableTargets.put(1, new RandomCollection<>(itemConf.getMissionItemNames()));
                }
                return availableTargets.get(1);

            case 2:
                if(availableTargets.get(2) == null){
                    availableTargets.put(2, new RandomCollection<>(enemyConfiguration.getMobNames()));
                }
                return availableTargets.get(2);

            default:
                return null;
        }

    }

    /**
     * Depending on the mission ID, instantiate it
     * @param mID the mission ID
     * @param targetID the enemy/item ID
     * @param targetNum the number of enemy/item used by the mission
     * @return a Mission object
     */
    private Mission instantiateMission(int mID, String targetID, int targetNum){
        switch (mID){
            case 0:
                return new KillNEnemiesMission(targetID, targetNum);

            case 1:
                return new CollectNItemsMission(targetID, targetNum);

            case 2:
                return new KillTheBossMission(targetID, recallDifficulty);

            default:
                return null;
        }
    }

    /**
     * @param mID the mission ID
     * @param capacity the total capacity left
     * @param difficulty the number of missions left
     * @return the number of items/enemies used by the mission
     */
    private int getMissionCapacity(int mID, int capacity, int difficulty) {
        switch (mID) {
            case 0:
                return capacity/difficulty;

            case 1:
                return capacity/difficulty;

            case 2:
                return 1;

            default:
                return 0;
        }
    }

}
