package missions;

import configuration.EnemyConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Enemy;
import elements.NullAnimationException;
import org.newdawn.slick.SlickException;

public class KillNEnemiesMission extends SimpleInteractionMission {

    public static final String type = "enemy";

    public KillNEnemiesMission(String targetId, int numKill) {
        super(targetId, numKill);
    }

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
        String ret = "You have to kill " + getTotal() + " " + getTargetId() + " [" + (getTotal()-getNumInteractions()) + "/" + getTotal() + "]";
        if(completed()){
            return ret + " - COMPLETED!";
        }
        return ret;
    }
}
