package missions;

import elements.Enemy;
import elements.Item;

import java.util.Map;
import java.util.Set;



public abstract class Mission {

    private String targetId;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass() && targetId == ((Mission) obj).getTargetId();
    }

    /**
     * Adds a mission to the list
     * @param e the mission to add
     * @return true if the mission was added, false otherwise
     */
    public abstract boolean add(Mission e);

    /**
     * @return true if the mission was completed, false otherwise
     */
    public abstract boolean completed();

    /**
     * Check if an item contributes to a mission
     * @param item the item to check
     */
    public abstract void check(MissionItem item);

    /**
     * @return the items population needed by the mission (id, number)
     */
    public abstract Map<String, Integer> getItemPopulation();

    /**
     * @return the item set generated by the mission
     */
    public abstract Set<Item> getItemSet();

    /**
     * @return the enemies population needed by the mission (id, number)
     */
    public abstract Map<String, Integer> getEnemyPopulation();

    /**
     * @return the mob set generated by the mission
     */
    public abstract Set<Enemy> getEnemySet();
}
