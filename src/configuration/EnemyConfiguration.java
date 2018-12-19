package configuration;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.Set;

/**
 * Configuration class for the enemies.
 * Implemented as Singleton
 */
public class EnemyConfiguration extends MobConfiguration {

    private static final String defaultFilename = System.getProperty("user.dir") + "/resource/configurations/enemy.conf";
    private static EnemyConfiguration instance;

    /**
     * @return the instance of the singleton
     */
    public static EnemyConfiguration getInstance() {
        if(instance == null){
            instance = new EnemyConfiguration(defaultFilename);
        }
        return instance;
    }

    /**
     * Constructor
     * @param filename the filename of the configuration file
     */
    private EnemyConfiguration(String filename){
        super(filename);
    }

    /**
     * @param id the mob id
     * @return the mob points
     * @throws NoSuchElementInConfigurationException if there isn't this id in the configuration
     */
    public int getMobPoints(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("points") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("points").getAsInt();
    }

    /**
     * @param id the mob id
     * @return the time to wait between two attack in frames
     * @throws NoSuchElementInConfigurationException if there isn't this id in the configuration
     */
    public int getAttackLatency(String id) throws NoSuchElementInConfigurationException {
        return getConfiguration(id).get("delay").getAsInt();
    }

    /**
     * @param id the mob id
     * @return an active sound
     * @throws NoSuchElementInConfigurationException if there isn't this id in the configuration
     * @throws SlickException a slick exception
     */
    public Sound getActiveSound(String id ) throws NoSuchElementInConfigurationException, SlickException {
        return new Sound(System.getProperty("user.dir")+"/"+getConfiguration(id).get("active_sound").getAsString());
    }

    /**
     * @param id the mob id
     * @return an attack sound
     * @throws NoSuchElementInConfigurationException if there isn't this id in the configuration
     * @throws SlickException a slick exception
     */
    public Sound getAttackSound(String id ) throws NoSuchElementInConfigurationException, SlickException {
        return new Sound(System.getProperty("user.dir")+"/"+getConfiguration(id).get("attack_sound").getAsString());
    }

    /**
     * @return a Set containing all the mobs' ids
     */
    public Set<String> getMobNames(){
        return getIdSet();
    }
}
