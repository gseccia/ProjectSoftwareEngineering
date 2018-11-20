package configuration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

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
}