package missions;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Item;
import elements.NullAnimationException;
import org.newdawn.slick.SlickException;

/**
 * The class implements a mission that requires you to collect a certain number of items.
 * It's a leaf of the composite pattern
 */
public class CollectNItemsMission extends SimpleInteractionMission {

    public static final String type = "item";

    /**
     * Constructor
     * @param targetId the id of the item to find
     * @param numInteractions the number of items to find
     */
    public CollectNItemsMission(String targetId, int numInteractions) {
        super(targetId, numInteractions);
    }

    /**
     * Produces the items needed for the mission
     * @param acceptor a StorageRoom object
     */
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
