package elements;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class MobConfiguration {

    private static final String filename = System.getProperty("user.dir") + "/resource/textures/sprites/mobs.conf";
    private static MobConfiguration instance = null;
    private JsonObject configuration;



    public static MobConfiguration getInstance(){
        if(instance == null){
            instance = new MobConfiguration();
        }
        return instance;
    }

    private MobConfiguration(){
        try(JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(filename)))){
            JsonParser parser = new JsonParser();
            configuration = parser.parse(reader).getAsJsonObject();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public JsonObject getMobConfiguration(String mobID){
        return configuration.getAsJsonObject(mobID);
    }
}
