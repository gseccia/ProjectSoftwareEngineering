package missions;

import configuration.EnemyConfiguration;
import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Enemy;
import elements.Item;
import elements.NullAnimationException;
import org.newdawn.slick.SlickException;
import utils.RandomCollection;

import java.util.HashSet;
import java.util.Set;

public class SaveTheNPCMission extends Mission {

    private String id;
    private Item npc = null;
    private Set<Enemy> spawns;

    public SaveTheNPCMission(String targetId, int numSpawn) {
        super(targetId);
        this.id = targetId;
        spawns = new HashSet<>();

        RandomCollection<String> mobNames = new RandomCollection<>(EnemyConfiguration.getInstance().getMobNames());
        String spawnId = mobNames.getRandom();

        try {
            for(int i=0; i<numSpawn; i++){
                    Enemy e = new Enemy(EnemyConfiguration.getInstance(), spawnId);
                    spawns.add(e);
            }

            npc = new Item(ItemConfiguration.getInstance(), targetId);
            npc.setSpawns(spawns);
        } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Adds a mission to the list
     *
     * @param e the mission to add
     * @return true if the mission was added, false otherwise
     */
    @Override
    public boolean add(Mission e) {
        return false;
    }

    /**
     * @return the number of missions in the component
     */
    @Override
    public int numMissions() {
        return 0;
    }

    /**
     * @return true if the mission was completed, false otherwise
     */
    @Override
    public boolean completed() {
        return npc == null;
    }

    /**
     * Check if an item contributes to a mission
     *
     * @param item the item to check
     */
    @Override
    public void check(MissionTarget item) {
        if(item.equals(npc)){
            npc = null;
        }
    }

    @Override
    public void produceTargets(StorageRoom acceptor) {
        acceptor.collectItem(npc);
    }

    /**
     * @return the number of interactions needed to complete the mission
     */
    @Override
    public int getNumInteractions() {
        return 1;
    }

    @Override
    public String toString(){
        String ret = "Save "+id+"!";
        if(completed()){
            return ret + "\n- COMPLETED!";
        }
        return ret;
    }
}
