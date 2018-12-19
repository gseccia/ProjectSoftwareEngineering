package visitors;

import elements.Player;

/**
 * A visitor that does absolutely nothing
 */
public class NullModifier implements PlayerModifier {

    /**
     * Does nothing to the player
     *
     * @param player the player
     */
    @Override
    public void accept(Player player) { }
}
