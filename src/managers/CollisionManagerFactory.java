package managers;

import org.newdawn.slick.GameContainer;
import elements.Mob;

/**
 * 
 * @author JBFourierous
 * Factory that creates an appropriate collision manager according the client request.
 */
public class CollisionManagerFactory {
	private Mob player, otherMob;
	private String mapName;
	private GameContainer gc;
	
	public CollisionManagerFactory(Mob player, Mob otherMob, String mapName, GameContainer gc) {
		this.player = player;
		this.otherMob = otherMob;
		this.mapName = mapName;
		this.gc = gc;
	}
	
	public CollisionManager getFactory() {
		if(mapName == null) {
			return new MapCollisionManager(player, mapName, gc);
		} else {
			return new CollisionManagerMob(player, otherMob, gc);
		}
	}
}
