package managers;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import elements.Mob;

public class CollisionDetectionMob extends CollisionDetectionStrategy{
	
	public CollisionDetectionMob(HitboxMaker hitbox){
        this.mobs = hitbox.getMobs();
        this.map = hitbox.getMap();
    }
	
	public void setKey(int key) {
		this.key = key;
	}
	
	private float area(Shape rect) {
		return rect.getHeight()*rect.getWidth();
	}
	
	private Rectangle intersection(Shape a,Shape b) {
		float x,y,w,h;
		x = Math.max(a.getX(),b.getX());
		y = Math.max(a.getY(),b.getY());
		w = Math.min(a.getX() + a.getWidth(), b.getX() + b.getWidth()) - x;
		h = Math.min(a.getY() + a.getHeight(), b.getY() + b.getHeight()) - y;
		
		return new Rectangle(x,y,w,h);
	}
	
	private Rectangle union(Shape a,Shape b) {
		float x,y,w,h;
		x = Math.min(a.getX(),b.getX());
		y = Math.min(a.getY(),b.getY());
		w = Math.max(a.getX() + a.getWidth(), b.getX() + b.getWidth()) - x;
		h = Math.max(a.getY() + a.getHeight(), b.getY() + b.getHeight()) - y;
		
		return new Rectangle(x,y,w,h);
	}
	
	@Override
	public boolean detectCollision(int shiftX, int shiftY, Mob player) {
        boolean collision = false;
        aligner(shiftX, shiftY, player, false);
        for (Mob mob : this.mobs){
            if (player.intersects(mob)){ 
                collision = true;
                break;
            }
        }
        player.setLocation(px,py);
        return true;
	}

}
