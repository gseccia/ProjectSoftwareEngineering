package managers;

import elements.Mob;

import java.util.List;

public class CollisionDetectionPlayerAttacksEnemy extends CollisionDetectionStrategy{
    private List<Mob> mobs;
    private Mob enemy;

    public CollisionDetectionPlayerAttacksEnemy(HitboxMaker hitboxMaker){
        this.map = hitboxMaker.getMap();
        this.mobs = hitboxMaker.getMobs();
    }

    @Override
    public boolean detectCollision(int shiftX, int shiftY, Mob player){
        boolean condition = false;
        aligner(shiftX, shiftY, player,false);
        for (Mob mob: mobs){
            if(player.getAttack().intersects(mob)){
                condition = true;
                enemy = mob;
                break;
            }
        }
        player.setLocation(px,py);
        return condition;
    }

    public Mob getEnemy() {
        return enemy;
    }
}
