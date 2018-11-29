package missions;

import java.util.Map;

public class CollectNItemsMission extends SimpleInteractionMission {

    public static final String type = "item";

    public CollectNItemsMission(String targetId, int numInteractions) {
        super(targetId, numInteractions);
    }

    /**
     * @return the items population needed by the mission (id, number)
     */
    @Override
    public Map<String, Integer> getItemPopulation() {
        return getNeeded();
    }

    @Override
    public String toString(){
        String ret = "You have to collect " + getTotal() + " " + getTargetId() + " [" + (getTotal()-getNumInteractions()) + "/" + getTotal() + "]";
        if(completed()){
            return ret + " - COMPLETED!";
        }
        return ret;
    }
}
