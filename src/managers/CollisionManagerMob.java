package managers;

import org.newdawn.slick.GameContainer;
import elements.Mob;

/**
 * 
 * @author marco
 * This class manages the collision of the character with the AI mobs.
 */
public class CollisionManagerMob implements CollisionManagerMKII {
	private Mob player, otherMob;
	private GameContainer gc;
	
	public CollisionManagerMob(Mob player, Mob otherMob, GameContainer gc) {
		super();
		this.player = player;
		this.otherMob = otherMob;
		this.gc = gc;
	}

	@Override
	public void detectCollision() {
		// TODO Auto-generated method stub
		if(player.intersects(otherMob)) {
			//CHE DIAVOLO SUCCEDE?
		}
	}

}
