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

abstract class Configuration {

    protected abstract JsonObject getConfiguration(String id) throws NoSuchElementInConfigurationException;

    protected JsonObject uploadConfiguration(String filename){
        try(JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(filename)))){
            JsonParser parser = new JsonParser();
            return parser.parse(reader).getAsJsonObject();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

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
