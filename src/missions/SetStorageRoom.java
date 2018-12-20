package missions;

import elements.Enemy;
import elements.Item;

import java.util.HashSet;
import java.util.Set;

/**
 * An implementation of StorageRoom, that collects items and enemies
 */
public class SetStorageRoom implements StorageRoom {

    private Set<Enemy> enemySet;
    private Set<Item> itemSet;

    public SetStorageRoom() {
        enemySet = new HashSet<>();
        itemSet = new HashSet<>();
    }

    /**
     * Collects an item
     *
     * @param i the item to collect
     */
    @Override
    public void collectItem(Item i) {
        itemSet.add(i);
    }

    /**
     * Collects an enemy
     *
     * @param e the enemy to collect
     */
    @Override
    public void collectEnemy(Enemy e) {
        enemySet.add(e);
    }

    public Set<Enemy> getEnemySet() {
        return enemySet;
    }

    public Set<Item> getItemSet() {
        return itemSet;
    }
}
