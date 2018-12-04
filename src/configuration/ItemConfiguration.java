package configuration;

import com.google.gson.JsonObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import java.util.Set;

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

    public static ItemConfiguration getInstance(){
        if(instance == null){
            instance = new ItemConfiguration(filename);
        }
        return instance;
    }
    
    private ItemConfiguration(String filename){
        this.configuration = super.uploadConfiguration(filename);
    }

    public Set<String> getItemNames(){
        return configuration.keySet();
    }

    public Animation getItemAnimation(String id) throws SlickException, NoSuchElementInConfigurationException {
        return generateAnimation(id, "animation");
    }

    public int getHeight(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("height") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("height").getAsInt();
    }

    public int getWidth(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("width") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("width").getAsInt();
    }
    
    /*
     * Metodo per il PointsAccumulator cosicch� ogni oggetto conserva nel file di configurazione
     * i punti associati al tipo di item
     */
    public int getItemPoints(String id) throws NoSuchElementInConfigurationException {
        if(getConfiguration(id).get("points") == null) {
            throw new NoSuchElementInConfigurationException();
        }
        return this.getConfiguration(id).get("points").getAsInt();
    }
}
