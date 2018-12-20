package configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Abstract class that reads the configurations file in a JSON format
 */
abstract class Configuration {

    /**
     * Returns all the options related to a certain id
     * @param id the id of the option
     * @return a JSON object containing the data
     * @throws NoSuchElementInConfigurationException if the id isn't in the configuration
     */
    protected abstract JsonObject getConfiguration(String id) throws NoSuchElementInConfigurationException;

    /**
     * Reads a JSON configuration from file. The configuration must be made up of a single JSON object whose keys are the
     * different ids of the configuration. The value associated to each id is a JSON object that contains the various options
     * in any format
     * @param filename the configuration's filename
     * @return a JSON object containing the configuration
     */
    JsonObject uploadConfiguration(String filename){
        try(JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(filename)))){
            JsonParser parser = new JsonParser();
            return parser.parse(reader).getAsJsonObject();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Generate an animation. In the configuration must be a single object with an array for the animation images and an
     * array for the images' durations.
     * @param id the id of the element
     * @param animation the id of the animation
     * @return an Animation object
     * @throws SlickException a Slick exception
     * @throws NoSuchElementInConfigurationException if there is no id or animation id in the configuration
     */
    public Animation generateAnimation(String id, String animation) throws SlickException, NoSuchElementInConfigurationException {
        // load configuration from id
        JsonObject conf = this.getConfiguration(id);
        // load configuration for movement type
        JsonObject mov = conf.getAsJsonObject(animation);
        if(mov == null){
            throw new NoSuchElementInConfigurationException();
        }
        // load frame for movement type
        JsonArray images = mov.getAsJsonArray("frames");
        // load duration for each frame
        JsonArray duration = mov.getAsJsonArray("duration");
        // setup an animation as a sequence of image and movements
        Image[] arr = new Image[images.size()];
        int[] dur = new int[images.size()];
        String basePath = conf.get("base_folder").getAsString()+animation+"/";
        for(int i = 0; i< images.size(); i++){
            arr[i] = new Image(basePath+images.get(i).getAsString());
            dur[i] = duration.get(i).getAsInt();
        }
        return new Animation(arr, dur);
    }
}
