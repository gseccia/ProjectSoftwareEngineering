package missions;

import configuration.EnemyConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Enemy;
import elements.NullAnimationException;
import org.newdawn.slick.SlickException;

/**
 * A class that implements a mission that requires you to kill a certain number of enemies
 * It's a leaf of the composite pattern
 */
public class KillNEnemiesMission extends SimpleInteractionMission {

    public static final String type = "enemy";

    /**
     * Constructor
     * @param targetId the id of the target enemy
     * @param numKill the number of enemies to kill
     */
    public KillNEnemiesMission(String targetId, int numKill) {
        super(targetId, numKill);
    }

    /**
     * Produces the enemies needed for the mission
     * @param acceptor a StorageRoom object
     */
    @Override
    public void produceTargets(StorageRoom acceptor) {
        for(int i=0; i<getTotal(); i++){
            try {
                acceptor.collectEnemy(new Enemy(EnemyConfiguration.getInstance(), getTargetId()));
            } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString(){
        String ret = "Kill " + getTotal() + " " + getTargetId() + " [" + (getTotal()-getNumInteractions()) + "/" + getTotal() + "]";
        if(completed()){
            return ret + "\n- COMPLETED!";
        }
        return ret;
    }
}
