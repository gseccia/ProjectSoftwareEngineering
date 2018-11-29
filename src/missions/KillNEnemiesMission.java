package missions;

import java.util.Map;

public class KillNEnemiesMission extends SimpleInteractionMission {

    public static final String type = "enemy";

    public KillNEnemiesMission(String targetId, int numKill) {
        super(targetId, numKill);
    }

    /**
     * @return the enemies population needed by the mission (id, number)
     */
    @Override
    public Map<String, Integer> getEnemyPopulation() {
        return getNeeded();
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
