package visitors;

import elements.Player;

public class NullModifier implements PlayerModifier {

    /**
     * Does something to the player
     *
     * @param player the player
     */
    @Override
    public void accept(Player player) { }
}
