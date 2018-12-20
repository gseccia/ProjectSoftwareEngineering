package configuration;

import com.google.gson.JsonObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import java.util.HashSet;
import java.util.Set;

/**
 * Configuration class for the items.
 * Implemented as Singleton
 */
public class ItemConfiguration extends Configuration {

    private static final String filename = System.getProperty("user.dir") + "/resource/configurations/item.conf";
    private static ItemConfiguration instance = null;
    private JsonObject configuration;

    @Override
    protected JsonObject getConfiguration(String id) throws NoSuchElementInConfigurationException {
        if(configuration.get(id) == null){
            throw new NoSuchElementInConfigurationException();
        }
        return this.configuration.getAsJsonObject(id);
    }

    /**
     * @return the ItemConfiguration instance
     */
    public static ItemConfiguration getInstance(){
        if(instance == null){
            instance = new ItemConfiguration(filename);
        }
        return instance;
    }

    /**
     * Constructor
     * @param filename the filename of the configuration
     */
    private ItemConfiguration(String filename){
        this.configuration = super.uploadConfiguration(filename);
    }

    /**
     * @return a set containing all the item's ids
     */
    public Set<String> getItemNames(){
        return configuration.keySet();
    }

    /**
     * @return a set containing only the ids of the items that can be used for the missions
     */
    public Set<String> getMissionItemNames() {
        Set<String> ret = new HashSet<>();
        for(String name : configuration.keySet()){
            if(configuration.getAsJsonObject(name).get("class").getAsString().equals("mission")){
                ret.add(name);
            }
        }
        return ret;
    }

    /**
     * @return a set containing the ids of the NPC
     */
    public Set<String> getNPCNames() {
        Set<String> ret = new HashSet<>();
        for(String name : configuration.keySet()){
            if(configuration.getAsJsonObject(name).get("class").getAsString().equals("npc")){
                ret.add(name);
            }
        }
        return ret;
    }

    /**
     * Creates the animation for a certain item
     * @param id the item id
     * @return an Animation object
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public Animation getItemAnimation(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id, "animation");
    }

    /**
     * @param id the item id
     * @return the height of the item
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public int getHeight(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("height") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("height").getAsInt();
    }

    /**
     * @param id the item id
     * @return the width of the item
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public int getWidth(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("width") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("width").getAsInt();
    }

    /**
     * @param id the item id
     * @return the points of the item
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     */
    public int getItemPoints(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("points") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("points").getAsInt();
    }

}
