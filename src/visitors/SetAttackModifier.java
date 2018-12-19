package visitors;

import attacks.Attack;
import attacks.ConsumableAttack;
import elements.Player;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

/**
 * A visitor that sets the attack of the player
 */
public class SetAttackModifier implements PlayerModifier {

    private Attack special;
    private Sound sfx;
    int uses, multiplier;

    /**
     * Constructor
     * @param special the special attack to set
     * @param sfx a sound effect
     * @param uses the number of uses of the special attack
     * @param multiplier a multiplier of the base attack of the player
     */
    public SetAttackModifier(Attack special, Sound sfx, int uses, int multiplier) {
        this.special = special;
        this.sfx = sfx;
        this.uses = uses;
        this.multiplier = multiplier;
    }

    /**
     * Assigns the new temporary attack to the player and plays a sound
     * @param player the player
     */
    @Override
    public void accept(Player player) {
        if(sfx != null){
            sfx.play(1, SoundStore.get().getMusicVolume()*1.5f);
        }
        Attack consumable = new ConsumableAttack(player, special, player.getAttack(), uses, multiplier);
        player.setAttack(consumable);
    }
}
