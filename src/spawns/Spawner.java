package spawns;

import elements.Enemy;

import java.util.Set;

/**
 * An interface that defines the behaviour of all the objects that have to spawn enemies
 */
public interface Spawner {

    /**
     * @return a set of enemy to spawn
     */
    Set<Enemy> getSpawns();

    /**
     * @param target the spawns
     */
    void setSpawns(Set<Enemy> target);
}
