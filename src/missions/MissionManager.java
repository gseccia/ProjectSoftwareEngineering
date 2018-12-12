package missions;

import java.util.HashSet;
import java.util.Set;

public class MissionManager extends Mission {

    private Set<Mission> missions;

    public MissionManager(){
        this.missions = new HashSet<>();
    }

    /**
     * Adds a mission to the list
     *
     * @param e the mission to add
     * @return true if the mission was added, false otherwise
     */
    @Override
    public boolean add(Mission e) {
        return missions.add(e);
    }

    /**
     * @return the number of missions in the component
     */
    @Override
    public int numMissions() {
        return missions.size();
    }

    /**
     * @return true if the mission was completed, false otherwise
     */
    @Override
    public boolean completed() {
        boolean flag = true;
        for(Mission m : missions){
            flag = flag && m.completed();
        }
        return flag;
    }

    /**
     * Check if an item contributes to a mission
     *
     * @param item the item to check
     */
    @Override
    public void check(MissionTarget item) {
        for(Mission m : missions){
            m.check(item);
        }
    }

    @Override
    public void produceTargets(StorageRoom acceptor) {
        for(Mission m : missions){
            m.produceTargets(acceptor);
        }
    }

    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        for(Mission m : missions){
            ret.append(m.toString()).append("\n");
        }
        return ret.toString();
    }

    /**
     * @return the number of interactions needed to complete the mission
     */
    @Override
    public int getNumInteractions() {
        int sum = 0;
        for(Mission m : missions){
            sum += m.getNumInteractions();
        }
        return sum;
    }
}
