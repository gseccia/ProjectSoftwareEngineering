package missions;

import configuration.EnemyConfiguration;
import configuration.ItemConfiguration;
import configuration.MobConfiguration;
import utils.RandomCollection;

import java.util.*;

public class MissionsFactory {

    private final static int numMissions = 3;
    private int capacity, difficulty, recallDifficulty;
    private EnemyConfiguration mobConf;
    private ItemConfiguration itemConf;

    public MissionsFactory(int capacity, int difficulty, EnemyConfiguration mobConf, ItemConfiguration itemConf) {
        this.capacity = capacity;
        this.difficulty = difficulty;
        recallDifficulty = difficulty;
        this.mobConf = mobConf;
        this.itemConf = itemConf;
    }

    public Mission generateMissions() throws NotEnoughMissionsException {
        Mission manager = new MissionManager(mobConf, itemConf);
        Set<Integer> excluded = new HashSet<>();
        while(difficulty > 0){
            int ran = generateRandomId(excluded);
            List<String> targetSet = getMissionIDs(ran);
            Iterator<String> it = targetSet.iterator();
            int used = getMissionCapacity(ran, capacity, difficulty);
            boolean flag = true;
            while (it.hasNext() && flag) {
                flag = !manager.add(instantiateMission(ran, it.next(), used));
            }
            if(!flag){
                capacity -= used;
                difficulty--;
            }
            else{
                excluded.add(ran);
            }
        }

        return manager;
    }

    private int generateRandomId(Set<Integer> excluded) throws NotEnoughMissionsException {
        int ran = new Random().nextInt(numMissions);
        if(excluded.size() >= numMissions){
            throw new NotEnoughMissionsException("");
        }
        while(excluded.contains(ran)){
            ran = (ran+1)%numMissions;
        }
        return ran;
    }

    private List<String> getMissionIDs(int mID){
        switch (mID){
            case 0:
                return new RandomCollection<>(mobConf.getMobNames());

            case 1:
                return new RandomCollection<>(itemConf.getItemNames());

            case 2:
                return new RandomCollection<>(mobConf.getMobNames());

            default:
                return null;
        }

    }

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
