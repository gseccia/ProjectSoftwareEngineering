package managers;

import elements.Mob;

public class CollisionDetectionMobAttacksPlayer extends CollisionDetectionStrategy {
    private Mob collidedMob;
    private int attackDemage;

    public CollisionDetectionMobAttacksPlayer(HitboxMaker hitbox){
        this.mobs = hitbox.getMobs();
        this.map = hitbox.getMap();
    }

    @Override
    public boolean detectCollision(int shiftX, int shiftY, Mob player){
        attackDemage = 0;
        boolean collision = false;
        aligner(shiftX, shiftY, player, false);
        for (Mob mob : this.mobs){
            if (mob.intersects(player)){
                collision = true;
                this.collidedMob = mob;
                attackDemage += mob.getAttackDamage();
            }
        }
        player.setLocation(px,py);
        return collision;
    }

    public Mob getCollidedMob(){
        return this.collidedMob;
    }

    public int getAttackDemage(){
        return attackDemage;
    }
}