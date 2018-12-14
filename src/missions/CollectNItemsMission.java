package missions;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Item;
import elements.NullAnimationException;
import org.newdawn.slick.SlickException;

public class CollectNItemsMission extends SimpleInteractionMission {

    public static final String type = "item";

    public CollectNItemsMission(String targetId, int numInteractions) {
        super(targetId, numInteractions);
    }

    @Override
    public void produceTargets(StorageRoom acceptor) {
        for(int i=0; i<getTotal(); i++){
            try {
                acceptor.collectItem(new Item(ItemConfiguration.getInstance(), getTargetId()));
            } catch (NullAnimationException | SlickException | NoSuchElementInConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString(){
        String ret = "You have to collect " + getTotal() + " " + getTargetId() + " [" + (getTotal()-getNumInteractions()) + "/" + getTotal() + "]";
        if(completed()){
            return ret + "\n- COMPLETED!";
        }
        return ret;
    }
}
