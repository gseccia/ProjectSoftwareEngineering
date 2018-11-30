package managers;

import elements.Mob;

public class CollisionDetectionEnemyAttacksPlayer extends CollisionDetectionStrategy {
    private Mob collidedMob;
    private int attackDamage;

    public CollisionDetectionEnemyAttacksPlayer(HitboxMaker hitbox){
        this.mobs = hitbox.getMobs();
        this.map = hitbox.getMap();
    }

    @Override
    public boolean detectCollision(int shiftX, int shiftY, Mob player){
        attackDamage = 0;
        boolean collision = false;
        aligner(shiftX, shiftY, player, false);
        for (Mob mob : this.mobs){
            if (mob.getAttack().intersects(player) && mob.isReadyToAttack()){
                mob.hasAttacked();
                collision = true;
                this.collidedMob = mob;
                attackDamage += mob.getAttackDamage();
            }
        }
        player.setLocation(px,py);
        return collision;
    }

    public Mob getCollidedMob(){
        return this.collidedMob;
    }

    public int getAttackDamage(){
        return attackDamage;
    }
}