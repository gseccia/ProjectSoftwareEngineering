package configuration;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.*;

public class MobConfiguration extends Configuration{
    private final String filename = System.getProperty("user.dir") + "/resource/textures/sprites/mobs.conf";
    private static MobConfiguration instance = null;
    private JsonObject configuration = null;

    public static MobConfiguration getInstance(){
        if(instance == null){
            instance = new MobConfiguration();
        }
        return instance;
    }

    private MobConfiguration(){
        this.configuration = super.uploadConfiguration(this.filename);
    }

    public Animation getFaceUp(){
        return null;
    }
    public Animation getFaceDown(){
        return null;
    }

    public Animation getFaceLeft(){
        return null;
    }


    @Override
    protected JsonObject getConfiguration(String id) {
        return this.configuration.getAsJsonObject(id);
    }

    @Override
    public Animation getFaceStill(String id) {
        return null;
    }

    private Animation generateAnimation(String id, String movement) throws SlickException {
        JsonArray images = this.configuration.getAsJsonObject(id).getAsJsonObject("still").getAsJsonArray("frames");
        Image[] arr = new Image[images.size()];
        int[] dur = new int[images.size()];
        for(int i=0; i< images.size(); i++){
            arr[i] = new Image(basePath+images.get(i).getAsString());
            dur[i] = duration.get(i).getAsInt();
        }
        return new Animation(arr, dur);
    }
}
