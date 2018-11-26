package missions;

import java.util.HashMap;
import java.util.Map;

public abstract class SimpleInteractionMission extends Mission{

    private int numInteractions;
    private Map<String, Integer> needed;

    public SimpleInteractionMission(String targetId, int numInteractions) {
        super(targetId);
        this.numInteractions = numInteractions;
        needed = new HashMap<>();
        needed.put(targetId, numInteractions);
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
     * @return true if the mission was completed, false otherwise
     */
    @Override
    public boolean completed() {
        return numInteractions == 0;
    }

    /**
     * Check if an item contributes to a mission
     *
     * @param item the item to check
     */
    @Override
    public void check(MissionItem item) {
        if(numInteractions > 0 && getTargetId() == item.getID()){
            numInteractions--;
        }
    }

    protected Map<String, Integer> getNeeded(){
        return needed;
    }

    /**
     * @return the number of interactions needed to complete the mission
     */
    public int getNumInteractions() {
        return numInteractions;
    }

    /**
     * @return the number of missions in the component
     */
    @Override
    public int numMissions() {
        return 0;
    }
}
