package visitors;

import elements.Player;

public class DamagePlayerModifier implements PlayerModifier{

    private int amount;

    public DamagePlayerModifier(int amount) {
        this.amount = amount;
    }

    /**
     * Does something to the player
     *
     * @param player the player
     */
    @Override
    public void accept(Player player) {
        player.damage(amount);
    }
}
