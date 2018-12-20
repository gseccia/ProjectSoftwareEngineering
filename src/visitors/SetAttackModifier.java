package visitors;

import attacks.Attack;
import attacks.ConsumableAttack;
import elements.Mob;
import elements.Player;
import org.newdawn.slick.Sound;

public class SetAttackModifier implements Visitor {

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
    public void visit(Mob mob) {
        if(sfx != null){
            sfx.play();
        }
        Attack consumable = new ConsumableAttack(mob, special, mob.getAttack(), uses, multiplier);
        mob.setAttack(consumable);
    }
}
