package managers;

import elements.Mob;

public interface MapCollisionInterface extends Directions{

    /**
     * Checks if a mob collides with a wall
     * @param shiftX the x shift of the map
     * @param shiftY the y shift of the map
     * @param player the mob that collides
     * @param key the direction of the movement
     * @return true if collide, false otherwise
     */
    boolean wallCollision(int shiftX, int shiftY, Mob player, int key);

    /**
     * Checks if a player collides with a door
     * @param shiftX the x shift of the map
     * @param shiftY the y shift of the map
     * @param player the player object that collides
     * @return true if collide, false otherwise
     */
    boolean doorCollision(int shiftX, int shiftY, Mob player);
}
