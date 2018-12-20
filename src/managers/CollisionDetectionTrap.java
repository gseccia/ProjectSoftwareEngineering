package managers;

import elements.Item;
import elements.Mob;

import java.util.LinkedList;
import java.util.List;

/**
 * Collision detector for traps
 */
public class CollisionDetectionTrap extends CollisionDetectionStrategy {

    private List<Item> targets;
    private List<Item> hit;


    public CollisionDetectionTrap(HitboxMaker hitbox) {
        targets = hitbox.getItems();
        this.map = hitbox.getMap();
    }

    @Override
    public boolean detectCollision(int shiftX, int shiftY, Mob player) {
        hit = new LinkedList<>();
        for(int i=0; i<targets.size(); i++){
            Item target = targets.get(i);
            if(player.getAttack().intersects(target) && !player.isReadyToAttack()){
                hit.add(target);
                targets.remove(i);
            }
        }
        return !hit.isEmpty();
    }

    public List<Item> getCollisions(){
        return hit;
    }
}
