package configuration;

import com.google.gson.JsonObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

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

    public static AttackConfiguration getInstance(){
        if(instance == null){
            instance = new AttackConfiguration(filename);
        }
        return instance;
    }

    private AttackConfiguration(String filename){
        this.configuration = super.uploadConfiguration(filename);
    }

    public Animation getRightAnimation(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"right");
    }

    public Animation getLeftAnimation(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"left");
    }

    public Animation getUpAnimation(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"up");
    }

    public Animation getDownAnimation(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id,"down");
    }
}
