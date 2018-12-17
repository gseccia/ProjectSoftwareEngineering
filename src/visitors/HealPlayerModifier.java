package visitors;

import elements.Player;
import managers.observers.scoreboard.ScorePointsManager;
import managers.observers.scoreboard.States;

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
        ScorePointsManager.getScorePointsManagerInstance().increase(amount);
        ScorePointsManager.getScorePointsManagerInstance().setState(States.LifePointsAccumulator);
        player.heal(amount);
    }
}
