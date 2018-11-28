package missions;

import elements.Enemy;
import elements.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KillNEnemiesMission extends SimpleInteractionMission {

    public static final String type = "enemy";

    public KillNEnemiesMission(String targetId, int numKill) {
        super(targetId, numKill);
    }

    /**
     * @return the items population needed by the mission (id, number)
     */
    @Override
    public Map<String, Integer> getItemPopulation() {
        return new HashMap<>();
    }

    /**
     * @return the item set generated by the mission
     */
    @Override
    public Set<Item> getItemSet() {
        return new HashSet<>();
    }

    /**
     * @return the enemies population needed by the mission (id, number)
     */
    @Override
    public Map<String, Integer> getEnemyPopulation() {
        return getNeeded();
    }

    /**
     * @return the mob set generated by the mission
     */
    @Override
    public Set<Enemy> getEnemySet() {
        return new HashSet<>();
    }

    @Override
    public String toString(){
        return "You have to kill " + getNumInteractions() + " " + getTargetId();
    }
}
