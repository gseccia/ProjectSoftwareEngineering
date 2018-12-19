package configuration;

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
     * @return a Set containing all the mobs' ids
     */
    public Set<String> getMobNames(){
        return getIdSet();
    }
}
