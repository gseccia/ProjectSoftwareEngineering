package configuration;

public class PlayerConfiguration extends MobConfiguration {

    private static final String defaultFilename = System.getProperty("user.dir") + "/resource/configurations/player.conf";
    private static PlayerConfiguration instance;

    public static PlayerConfiguration getInstance() {
        if(instance == null){
            instance = new PlayerConfiguration(defaultFilename);
        }
        return instance;
    }

    private PlayerConfiguration(String filename) {
        super(filename);
    }

}
