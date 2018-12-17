package visitors;

import elements.Player;
import managers.observers.scoreboard.ScorePointsManager;
import managers.observers.scoreboard.States;
import org.newdawn.slick.Sound;

public class HealPlayerModifier implements PlayerModifier {

    private int amount;
    private Sound sfx;

    public HealPlayerModifier(int amount) {
        this.amount = amount;
    }

    public HealPlayerModifier(int amount, Sound sfx) {
        this.amount = amount;
        this.sfx = sfx;
    }

    /**
     * Does something to the player
     *
     * @param player the player
     */
    @Override
    public void accept(Player player) {
        if(sfx != null){
            sfx.play();
        }
        ScorePointsManager.getScorePointsManagerInstance().increase(amount);
        ScorePointsManager.getScorePointsManagerInstance().setState(States.LifePointsAccumulator);
        player.heal(amount);
    }
}
