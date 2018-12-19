package visitors;

import attacks.Attack;
import attacks.ConsumableAttack;
import elements.Player;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

public class SetAttackModifier implements PlayerModifier {

    private Attack special;
    private Sound sfx;
    int uses, multiplier;

    public SetAttackModifier(Attack special, int uses, int multiplier) {
        this.special = special;
        this.uses = uses;
        this.multiplier = multiplier;
    }

    public SetAttackModifier(Attack special, Sound sfx, int uses, int multiplier) {
        this.special = special;
        this.sfx = sfx;
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
        if(sfx != null){
            sfx.play(1, SoundStore.get().getMusicVolume()*1.5f);
        }
        Attack consumable = new ConsumableAttack(player, special, player.getAttack(), uses, multiplier);
        player.setAttack(consumable);
    }
}
