package visitors;

import elements.Player;
import managers.observers.scoreboard.ScorePointsManager;
import managers.observers.scoreboard.States;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

/**
 * A visitor that heals the player
 */
public class HealPlayerModifier implements PlayerModifier {

    private int amount;
    private Sound sfx;

    /**
     * Constructor
     * @param amount the healing to do
     */
    public HealPlayerModifier(int amount) {
        this.amount = amount;
    }

    /**
     * Constructor
     * @param amount the healing to do
     * @param sfx a sound effect
     */
    public HealPlayerModifier(int amount, Sound sfx) {
        this.amount = amount;
        this.sfx = sfx;
    }

    /**
     * Does something to the player and plays the sound effect
     * @param player the player
     */
    @Override
    public void accept(Player player) {
        if(sfx != null){
            sfx.play(1, SoundStore.get().getMusicVolume()*1.5f);
        }
        ScorePointsManager.getScorePointsManagerInstance().increase(amount);
        ScorePointsManager.getScorePointsManagerInstance().decrease(0);
        ScorePointsManager.getScorePointsManagerInstance().setState(States.LifePointsAccumulator);
        player.heal(amount);
    }
}
