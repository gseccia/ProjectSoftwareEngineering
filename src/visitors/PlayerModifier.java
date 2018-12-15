package visitors;

import elements.Player;

public interface PlayerModifier {

    /**
     * Does something to the player
     * @param player the player
     */
    void accept(Player player);

}
