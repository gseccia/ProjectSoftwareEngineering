package visitors;

import attacks.Attack;
import attacks.ConsumableAttack;
import elements.Player;

public class SetAttackModifier implements PlayerModifier {

    private Attack special;
    int uses, multiplier;

    public SetAttackModifier(Attack special, int uses, int multiplier) {
        this.special = special;
        this.uses = uses;
        this.multiplier = multiplier;
    }

    /**
     * Does something to the player
     *
     * @param player the player
     */
    @Override
    public void accept(Player player) {
        Attack consumable = new ConsumableAttack(player, special, player.getAttack(), uses, multiplier);
        player.setAttack(consumable);
    }
}
