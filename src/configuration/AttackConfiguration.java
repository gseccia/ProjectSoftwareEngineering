package configuration;

import com.google.gson.JsonObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.Set;

/**
 * Reads the configuration file for the attacks and creates the animations and the sounds.
 * Implemented using the singleton pattern
 */
public class AttackConfiguration extends Configuration {

    private static final String filename = System.getProperty("user.dir") + "/resource/configurations/attack.conf";
    private static AttackConfiguration instance = null;
    private JsonObject configuration;

    @Override
    protected JsonObject getConfiguration(String id) throws NoSuchElementInConfigurationException {
        if(configuration.get(id) == null){
            throw new NoSuchElementInConfigurationException();
        }
        return this.configuration.getAsJsonObject(id);
    }

    /**
     * @return the instance of the singleton
     */
    public static AttackConfiguration getInstance(){
        if(instance == null){
            instance = new AttackConfiguration(filename);
        }
        return instance;
    }

    /**
     * Constructor
     * @param filename the attack configuration file
     */
    AttackConfiguration(String filename){
        this.configuration = super.uploadConfiguration(filename);
    }

    /**
     * Creates the right attack animation
     * @param id the attack id
     * @return the right attack animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if the id is not present in the configuration
     */
    public Animation getRightAnimation(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"right");
    }

    /**
     * Creates the left attack animation
     * @param id the attack id
     * @return the left attack animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if the id is not present in the configuration
     */
    public Animation getLeftAnimation(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"left");
    }

    /**
     * Creates the up attack animation
     * @param id the attack id
     * @return the up attack animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if the id is not present in the configuration
     */
    public Animation getUpAnimation(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"up");
    }

    /**
     * Creates the down attack animation
     * @param id the attack id
     * @return the down attack animation
     * @throws SlickException a slick exception
     * @throws NoSuchElementInConfigurationException if the id is not present in the configuration
     */
    public Animation getDownAnimation(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"down");
    }

    /**
     * Creates the sound associated to the attack
     * @param id the attack id
     * @return the attack sound
     * @throws NoSuchElementInConfigurationException if the id is not present in the configuration
     * @throws SlickException a slick exception
     */
    public Sound getAttackSound(String id) throws NoSuchElementInConfigurationException, SlickException {
        String path = getConfiguration(id).get("sound").getAsString();
        return new Sound(System.getProperty("user.dir")+"/"+path);
    }

    /**
     * @return a Set containing the ids of the attacks contained in the configuration
     */
    Set<String> getKeySet() {
        return configuration.keySet();
    }
}
