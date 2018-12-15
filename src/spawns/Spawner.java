package spawns;

import elements.Enemy;

import java.util.Set;

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
