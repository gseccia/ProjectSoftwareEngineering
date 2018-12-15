package managers;

import elements.Mob;

public class CollisionDetectionMob extends CollisionDetectionStrategy{
	private Mob me;
	
	public CollisionDetectionMob(HitboxMaker hitbox, Mob me){
        this.mobs = hitbox.getMobs();
        this.me = me;
        this.map = hitbox.getMap();
    }
	
	public void setKey(int key) {
		this.key = key;
	}
	
	@Override
	public boolean detectCollision(int shiftX, int shiftY, Mob player) {
        boolean collision = false;
        for (Mob mob : mobs){
            if (me.intersects(mob)){ 
            		collision = true;
            		break;
            }
        }
        return !collision;
	}

}
