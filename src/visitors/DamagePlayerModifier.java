package visitors;

import elements.Mob;
import managers.observers.scoreboard.ScorePointsManager;
import managers.observers.scoreboard.States;

/**
 * A visitor that damage the player
 */
public class DamagePlayerModifier implements Visitor{

    private int amount;

    /**
     * Constructor
     * @param amount the damage to deal
     */
    public DamagePlayerModifier(int amount) {
        this.amount = amount;
    }

    /**
     * Does something to the player
     *
     * @param player the player
     */
	@Override
	public void visit(Mob mob) {
    	ScorePointsManager.getScorePointsManagerInstance().decrease(amount);
        ScorePointsManager.getScorePointsManagerInstance().increase(0);
        ScorePointsManager.getScorePointsManagerInstance().setState(States.LifePointsAccumulator);
        mob.damage(amount);
		
	}
}
