package managers;

import elements.Mob;

public class CollisionDetectionAttack extends CollisionDetectionStrategy {
    private Mob collidedMob;

    public CollisionDetectionAttack(HitboxMaker hitbox){
        this.mobs = hitbox.getMobs();
        this.map = hitbox.getMap();
    }

    @Override
    public boolean detectCollision(int shiftX, int shiftY, Mob player){
        boolean collision = false;
        aligner(shiftX, shiftY, player, false);
        for (Mob mob : this.mobs){
            if (mob.intersects(player)){
                collision = true;
                this.collidedMob = mob;
                break;
            }
        }
        player.setLocation(px,py);
        return collision;
    }

    public Mob getCollidedMob(){
        return this.collidedMob;
    }
}