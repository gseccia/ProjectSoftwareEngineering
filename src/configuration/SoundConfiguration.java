package configuration;

import com.google.gson.JsonObject;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundConfiguration extends Configuration {

    private static final String filename = System.getProperty("user.dir") + "/resource/configurations/sound.conf";
    private static SoundConfiguration instance;
    private JsonObject configuration;

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

    public static SoundConfiguration getInstance() {
        if(instance == null){
            instance = new SoundConfiguration(filename);
        }
        return instance;
    }

    public Sound getHealSound(String id) throws NoSuchElementInConfigurationException, SlickException {
        String path = getConfiguration(id).get("heal").getAsString();
        return new Sound(System.getProperty("user.dir")+"/"+path);
    }

    public Sound getPowerUpSound(String id) throws NoSuchElementInConfigurationException, SlickException {
        String path = getConfiguration(id).get("power").getAsString();
        return new Sound(System.getProperty("user.dir")+"/"+path);
    }
}
