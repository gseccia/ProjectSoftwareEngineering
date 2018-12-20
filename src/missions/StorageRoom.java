package missions;

import elements.Enemy;
import elements.Item;

/**
 * An interface to defines the visitor that collects the items or enemies from the mission
 */
public interface StorageRoom {

    /**
     * Collects an item
     * @param i the item to collect
     */
    void collectItem(Item i);

    /**
     * Collects an enemy
     * @param e the enemy to collect
     */
    void collectEnemy(Enemy e);

}
