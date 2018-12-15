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
        if(player == null) aligner(shiftX, shiftY, me, true);
        else aligner(shiftX, shiftY, player, true);
        for (Mob mob : mobs){
            if (me.intersects(mob)){ 
            		collision = true;
            		break;
            }
        }
        if(player != null) {
        	collision = collision || player.intersects(me);
        	player.setLocation(px,py);
        }
        else me.setLocation(px,py);
        return !collision;
	}

}
