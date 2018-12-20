package visitors;

import elements.Mob;
import elements.Player;
import managers.observers.scoreboard.ScorePointsManager;
import managers.observers.scoreboard.States;
import org.newdawn.slick.Sound;

public class HealPlayerModifier implements Visitor {

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
	public void visit(Mob mob) {
        if(sfx != null){
            sfx.play();
        }
        ScorePointsManager.getScorePointsManagerInstance().increase(amount);
        ScorePointsManager.getScorePointsManagerInstance().decrease(0);
        ScorePointsManager.getScorePointsManagerInstance().setState(States.LifePointsAccumulator);
        mob.heal(amount);

	}
}
