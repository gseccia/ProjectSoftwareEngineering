package missions;

import configuration.EnemyConfiguration;
import configuration.ItemConfiguration;
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

public class MissionsFactory {

    private final static int numMissions = 3;
    private int capacity, difficulty, recallDifficulty;
    private EnemyConfiguration enemyConfiguration;
    private ItemConfiguration itemConf;

    /**
     * Constructor of the factory
     * @param capacity the number of items/mobs that can be generated
     * @param difficulty the number of missions to generate
     * @param enemyConfiguration the instance of EnemyConfiguration
     * @param itemConf the instance of ItemConfiguration
     */
    public MissionsFactory(int capacity, int difficulty, EnemyConfiguration enemyConfiguration, ItemConfiguration itemConf) {
        this.capacity = capacity;
        this.difficulty = difficulty;
        recallDifficulty = difficulty;
        this.enemyConfiguration = enemyConfiguration;
        this.itemConf = itemConf;
    }

    /**
     * Generates the missions
     * @return an instance of Mission
     * @throws NotEnoughMissionsException if there are no more missions available
     */
    public Mission generateMissions() throws NotEnoughMissionsException {
        Mission manager = new MissionManager(enemyConfiguration, itemConf);
        Set<Integer> excluded = new HashSet<>();
        while(difficulty > 0){      //Continue until a number of missions equals to difficulty is created
            int ran = generateRandomId(excluded);   //Generate a random number x so that 0 < x < types of mission available and excludes the unavailable missions (explained later)
            List<String> targetSet = getMissionIDs(ran);    //Returns all the possible items/enemies associated to that mission
            Iterator<String> it = targetSet.iterator();
            int used = getMissionCapacity(ran, capacity, difficulty);   //Returns how many mob/items that mission will need
            boolean flag = true;
            while (it.hasNext() && flag) {                                      //Tries to instantiate the mission with a target
                flag = !manager.add(instantiateMission(ran, it.next(), used));  //If the mission already exists, tries to change target
            }                                                                   //Exits if there are no more targets available
            if(!flag){      //If the mission was added, reduce capacity and number of missions to be generated
                capacity -= used;
                difficulty--;
            }
            else{                   //If the mission was not added, mark it as unavailable so it won't be chosen anymore
                excluded.add(ran);
            }
        }

        return manager;
    }

    /**
     * Generate a mission ID
     * @param excluded the ID not to be generated
     * @return the mission ID
     * @throws NotEnoughMissionsException if there are no more missions available
     */
    private int generateRandomId(Set<Integer> excluded) throws NotEnoughMissionsException {
        int ran = new Random().nextInt(numMissions); //Generates a random int x so that 0 < x < types of mission available
        if(excluded.size() >= numMissions){     //If the unavailable missions are more than the available one
            throw new NotEnoughMissionsException("");
        }
        while(excluded.contains(ran)){ //If the random ID generated corresponds to an unavailable mission, choose the first available
            ran = (ran+1)%numMissions;
        }
        return ran;
    }

    /**
     * @param mID the mission ID
     * @return the IDs of the items/enemies that a certain mission can use
     */
    private List<String> getMissionIDs(int mID){
        switch (mID){
            case 0:
                return new RandomCollection<>(enemyConfiguration.getMobNames());

            case 1:
                return new RandomCollection<>(itemConf.getItemNames());

            case 2:
                return new RandomCollection<>(enemyConfiguration.getMobNames());

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
