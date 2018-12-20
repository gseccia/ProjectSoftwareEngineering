package missions;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Item;
import elements.NullAnimationException;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import visitors.DamagePlayerModifier;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * A class that implements a mission that requires you to find a certain number of traps
 * It's a leaf of the composite pattern
 */
public class FindNTrapsMission extends Mission {

    private Set<Item> targets;
    private String id;
    private int numTraps, difficulty;

    /**
     * Constructor
     * @param id the item id
     * @param numTraps the number of traps to find
     * @param difficulty the difficulty of the level (will be used for damage calculation)
     */
    public FindNTrapsMission(String id, int numTraps, int difficulty) {
        super(id);
        this.id = id;
        this.numTraps = numTraps;
        this.difficulty = difficulty;
        targets = new HashSet<>();
    }

    /**
     * Adds a mission to the list
     *
     * @param e the mission to add
     * @return true if the mission was added, false otherwise
     */
    @Override
    public boolean add(Mission e) {
        return false;
    }

    /**
     * @return the number of missions in the component
     */
    @Override
    public int numMissions() {
        return 0;
    }

    /**
     * @return true if the mission was completed, false otherwise
     */
    @Override
    public boolean completed() {
        return targets.isEmpty();
    }

    /**
     * Check if an item contributes to a mission
     * Only the items generated by this mission are valid
     * @param item the item to check
     */
    @Override
    public void check(MissionTarget item) {
        targets.remove(item);
    }

    /**
     * Produces the items needed for the mission
     * @param acceptor a StorageRoom object
     */
    @Override
    public void produceTargets(StorageRoom acceptor) {
        ItemConfiguration conf = ItemConfiguration.getInstance();
        int damage = difficulty*5;
        int green = new Random().nextInt(127)+128;
        int red = 255 - green;
        int blue = red/2;
        Color filter = new Color(red, green, blue);

        for(int i=0; i<numTraps; i++) {
            try {
                Item target = new Item(conf, id, new DamagePlayerModifier(damage), filter, true);
                acceptor.collectItem(target);
                targets.add(target);
            } catch (NullAnimationException | SlickException | NoSuchElementInConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return the number of interactions needed to complete the mission
     */
    @Override
    public int getNumInteractions() {
        return numTraps;
    }

    @Override
    public String toString(){
        String ret = "Find " + numTraps + " traps [" + (numTraps-targets.size()) + "/" + numTraps +"]";
        if(completed()){
            return ret + "\n- COMPLETED!";
        }
        return ret;
    }
}
