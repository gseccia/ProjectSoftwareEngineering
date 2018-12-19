package configuration;

/**
 * Configuration class for the player.
 * Implemented as Singleton
 */
public class PlayerConfiguration extends MobConfiguration {

    private static final String defaultFilename = System.getProperty("user.dir") + "/resource/configurations/player.conf";
    private static PlayerConfiguration instance;

    /**
     * @return the PlayerConfiguration instance
     */
    public static PlayerConfiguration getInstance() {
        if(instance == null){
            instance = new PlayerConfiguration(defaultFilename);
        }
        return instance;
    }

    /**
     * Constructor
     * @param filename the filename of the configuration file
     */
    private PlayerConfiguration(String filename) {
        super(filename);
    }

}
