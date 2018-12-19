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

public class FindNTrapsMission extends Mission {

    private Set<Item> targets;
    private String id;
    private int numTraps, difficulty;

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
     *
     * @param item the item to check
     */
    @Override
    public void check(MissionTarget item) {
        targets.remove(item);
    }

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
