package missions;

import elements.Enemy;
import elements.Item;

import java.util.Set;

public interface MissionFactory {

    /**
     * Generates the missions
     * @param itemCapacity the number of items that can be generated
     * @param mobCapacity the number of mobs that can be generated
     * @param difficulty the number of missions to generate
     * @return an instance of Mission
     * @throws NotEnoughMissionsException if there are no more missions available
     */
    Mission generateMissions(int itemCapacity, int mobCapacity, int difficulty) throws NotEnoughMissionsException;

    /**
     * @return the items needed by the missions
     */
    Set<Item> targetItems();

    /**
     * @return the enemies needed by the missions
     */
    Set<Enemy> targetEnemies();
}
