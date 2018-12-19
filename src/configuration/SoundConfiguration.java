package configuration;

import com.google.gson.JsonObject;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Configuration class for the sounds.
 * Implemented as Singleton
 */
public class SoundConfiguration extends Configuration {

    private static final String filename = System.getProperty("user.dir") + "/resource/configurations/sound.conf";
    private static SoundConfiguration instance;
    private JsonObject configuration;

    /**
     * Constructor
     * @param filename the filename of the configuration file
     */
    private SoundConfiguration(String filename) {
        this.configuration = super.uploadConfiguration(filename);
    }

    @Override
    protected JsonObject getConfiguration(String id) throws NoSuchElementInConfigurationException {
        if(configuration.get(id) == null){
            throw new NoSuchElementInConfigurationException();
        }
        return this.configuration.getAsJsonObject(id);
    }

    /**
     * @return the instance of the SoundConfiguration
     */
    public static SoundConfiguration getInstance() {
        if(instance == null){
            instance = new SoundConfiguration(filename);
        }
        return instance;
    }

    /**
     * @param id the id of an item/mob
     * @return the healing sound
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     * @throws SlickException a slick exception
     */
    public Sound getHealSound(String id) throws NoSuchElementInConfigurationException, SlickException {
        String path = getConfiguration(id).get("heal").getAsString();
        return new Sound(System.getProperty("user.dir")+"/"+path);
    }

    /**
     * @param id the id of an item/mob
     * @return the power up sound
     * @throws NoSuchElementInConfigurationException if there is no such id in the configuration
     * @throws SlickException a slick exception
     */
    public Sound getPowerUpSound(String id) throws NoSuchElementInConfigurationException, SlickException {
        String path = getConfiguration(id).get("power").getAsString();
        return new Sound(System.getProperty("user.dir")+"/"+path);
    }
}
