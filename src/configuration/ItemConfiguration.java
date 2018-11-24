package configuration;

import com.google.gson.JsonObject;

import java.util.HashSet;
import java.util.Set;

public class ItemConfiguration extends Configuration {

    @Override
    protected JsonObject getConfiguration(String id) throws NoSuchElementInConfigurationException {
        return null;
    }

    public Set<String> getItemNames(){
        return new HashSet<>();
    }
}
