package configuration;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class DoorsConfiguration extends Configuration {

    private static final String filename = System.getProperty("user.dir") + "/resource/maps/doors.conf";
    private JsonObject configuration;

    public DoorsConfiguration(){
        configuration = super.uploadConfiguration(filename);
    }

    @Override
    protected JsonObject getConfiguration(String id) throws NoSuchElementInConfigurationException {
        if(configuration.get(id) == null){
            throw new NoSuchElementInConfigurationException();
        }
        return this.configuration.getAsJsonObject(id);
    }

    /**
     * Get all the map names in the configuration
     * @return a set with the map names
     */
    public Set<String> getMapNames() {
        return configuration.keySet();
    }

    /**
     * Given a map, returns the number of doors
     * @param map the map name
     * @return the number of doors
     * @throws NoSuchElementInConfigurationException throws if there is no such name
     */
    public int getDoors(String map) throws NoSuchElementInConfigurationException {
        if(configuration.get(map) == null){
            throw new NoSuchElementInConfigurationException();
        }
        return configuration.get(map).getAsInt();
    }

    /**
     * Given the doors number, returns a map with that doors number
     * @param doors the doors number
     * @return the map name
     */
    public String getRandomGivenDoors(int doors){
        HashMap<Integer, String> tmp = new HashMap<>();
        int index = 0;
        try{
            for(String name : getMapNames()){
                if(getDoors(name) == doors){
                    tmp.put(index, name);
                    index++;
                }
            }
        }catch (NoSuchElementInConfigurationException ex){
            ex.printStackTrace();
        }
        if(index > 0) {
            int getIndex = new Random().nextInt(index);
            return tmp.get(getIndex);
        }
        return null;
    }
}
