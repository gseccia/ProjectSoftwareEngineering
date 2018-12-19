package visitors;

import elements.Player;

/**
 * An interface that specifies the behaviour of the visitors that modify the player
 */
public interface PlayerModifier {

    /**
     * Does something to the player
     * @param player the player
     */
    void accept(Player player);

}
