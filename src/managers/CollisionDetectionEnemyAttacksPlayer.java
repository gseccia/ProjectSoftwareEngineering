package managers;

import elements.Mob;

public class CollisionDetectionEnemyAttacksPlayer extends CollisionDetectionStrategy {
    private Mob collidedMob;
    private int attackDemage;
    private int collisionTime;

    public CollisionDetectionEnemyAttacksPlayer(HitboxMaker hitbox){
        this.mobs = hitbox.getMobs();
        this.map = hitbox.getMap();
        collisionTime = 0;
    }

    @Override
    public boolean detectCollision(int shiftX, int shiftY, Mob player){
        attackDemage = 0;
        boolean collision = false;
        aligner(shiftX, shiftY, player, false);
        for (Mob mob : this.mobs){
            if (mob.getAttack().intersects(player) && collisionTime <= 0){
                collision = true;
                this.collidedMob = mob;
                collisionTime = 50;
                attackDemage += mob.getAttackDamage();
                System.out.println("ATTACK "+mob.getX()+" "+mob.getAttackDamage());
            }
        }
        collisionTime--;
        player.setLocation(px,py);
        return collision;
    }

    public Mob getCollidedMob(){
        return this.collidedMob;
    }

    public int getAttackDamage(){
        return attackDemage;
    }
}