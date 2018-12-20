package visitors;

import elements.Mob;

/**
 * A visitor that does absolutely nothing
 */

public class NullModifier implements Visitor {

    /**
     * Does nothing to the player
     *
     * @param player the player
     */
	@Override
	public void visit(Mob mob) {
	
	}


}
