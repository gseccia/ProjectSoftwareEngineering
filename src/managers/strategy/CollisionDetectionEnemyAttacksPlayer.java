package managers.strategy;

import elements.Mob;

/**
 * Attack detector for an enemy against the player
 */
public class CollisionDetectionEnemyAttacksPlayer extends CollisionDetectionStrategy {
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
                attackDamage += mob.getAttackDamage();
            }
        }
        player.setLocation(px,py);
        return collision;
    }

    public int getAttackDamage(){
        return attackDamage;
    }
}