package managers;

import elements.NullAnimationException;

/**
 * 
 * @author JBFourierous
 * This interface defines the method to detect a collision with any collidable element into the map.
 */
public interface CollisionManagerMKII {
	public void detectCollision() throws NullAnimationException;
}
