package visitors;

import elements.Player;

public class HealPlayerModifier implements PlayerModifier {

    private int amount;

    public HealPlayerModifier(int amount) {
        this.amount = amount;
    }

    /**
     * Does something to the player
     *
     * @param player the player
     */
    @Override
    public void accept(Player player) {
        player.heal(amount);
    }
}
