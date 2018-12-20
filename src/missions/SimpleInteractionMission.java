package missions;

/**
 * An abstract class, subclass of all the missions that requires you to interact with a generic type of object
 * It's concrete subclasses are leaf of the composite
 */
public abstract class SimpleInteractionMission extends Mission{

    private int numInteractions, total;

    /**
     * Constructor
     * @param targetId the target id
     * @param numInteractions the number of interactions needed to complete the mission
     */
    SimpleInteractionMission(String targetId, int numInteractions) {
        super(targetId);
        this.numInteractions = numInteractions;
        this.total = numInteractions;
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
     * All the items with the correct ID are valid
     * @param item the item to check
     */
    @Override
    public void check(MissionTarget item) {
        if(numInteractions > 0 && getTargetId().equals(item.getID())){
            numInteractions--;
        }
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

    /**
     * @return total number of interactions
     */
    int getTotal() {
        return total;
    }
}
