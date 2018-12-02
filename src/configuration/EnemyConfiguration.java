package configuration;

import java.util.Set;

public class EnemyConfiguration extends MobConfiguration {

    private static final String defaultFilename = System.getProperty("user.dir") + "/resource/configurations/enemy.conf";
    private static EnemyConfiguration instance;

    public static EnemyConfiguration getInstance() {
        if(instance == null){
            instance = new EnemyConfiguration(defaultFilename);
        }
        return instance;
    }

    private EnemyConfiguration(String filename){
        super(filename);
    }

    public int getMobPoints(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("points") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("points").getAsInt();
    }

    public Set<String> getMobNames(){
        return getIdSet();
    }
}
