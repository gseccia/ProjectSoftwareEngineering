package missions;

import java.util.*;

/**
 * The abstract class that defines the behaviour of the missions, based on the Composite pattern
 */
public abstract class Mission {

    private String targetId;

    public String getTargetId() {
        return targetId;
    }

    public Mission(){}

    public Mission(String targetId){
        this.targetId = targetId;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass() && targetId == ((Mission) obj).getTargetId();
    }


    @Override
    public int hashCode() {
        return Objects.hash(targetId);
    }

    /**
     * Adds a mission to the list
     * @param e the mission to add
     * @return true if the mission was added, false otherwise
     */
    public abstract boolean add(Mission e);

    /**
     * @return the number of missions in the component
     */
    public abstract int numMissions();

    /**
     * @return true if the mission was completed, false otherwise
     */
    public abstract boolean completed();

    /**
     * Check if an item contributes to a mission
     * @param item the item to check
     */
    public abstract void check(MissionTarget item);


    /**
     * Produces the items/enemies needed for the mission
     * @param acceptor a StorageRoom object
     */
    public abstract void produceTargets(StorageRoom acceptor);

    /**
     * @return the number of interactions needed to complete the mission
     */
    public abstract int getNumInteractions();
}
