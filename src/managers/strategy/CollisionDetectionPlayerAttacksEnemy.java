package managers.strategy;

import elements.Mob;

import java.util.LinkedList;
import java.util.List;

/**
 * Attack detector for player with the enemies
 */
public class CollisionDetectionPlayerAttacksEnemy extends CollisionDetectionStrategy{
    private List<Mob> mobs;
    private List<Mob> enemy;

    public CollisionDetectionPlayerAttacksEnemy(HitboxMaker hitboxMaker){
        this.map = hitboxMaker.getMap();
        this.mobs = hitboxMaker.getMobs();
    }

    @Override
    public boolean detectCollision(int shiftX, int shiftY, Mob player){
        enemy = new LinkedList<>();
        aligner(shiftX, shiftY, player,false);
        for(int i=0; i<mobs.size(); i++){
            if(mobs.get(i).getHp() <= 0){
                mobs.remove(i);
            }
            else if(player.getAttack().intersects(mobs.get(i)) && !player.isReadyToAttack()){
                enemy.add(mobs.get(i));
            }
        }
        player.setLocation(px,py);
        return !enemy.isEmpty();
    }

    public List<Mob> getEnemy() {
        return enemy;
    }
}
