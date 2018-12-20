package missions;


/**
 * Represents a generic item (or mob) that can trigger a mission
 */
public interface MissionTarget {

    /**
     * @return the ID of the target
     */
    String getID();

}
