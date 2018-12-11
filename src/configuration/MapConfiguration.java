package configuration;

import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class MapConfiguration extends Configuration {

    private static final String filename = System.getProperty("user.dir") + "/resource/configurations/maps.conf";
    private static MapConfiguration instance = null;
    private JsonObject configuration;

    public static MapConfiguration getInstance(){
        if(instance == null){
            instance = new MapConfiguration();
        }
        return instance;
    }

    private MapConfiguration(){
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
        return configuration.getAsJsonObject(map).get("doors").getAsInt();
    }

    /**
     * Given a map, returns the capacity of items
     * @param map the map name
     * @return the item capacity
     * @throws NoSuchElementInConfigurationException throws if there is no such name
     */
    public int getItemCapacity(String map) throws NoSuchElementInConfigurationException {
        if(configuration.get(map) == null){
            throw new NoSuchElementInConfigurationException();
        }
        return configuration.getAsJsonObject(map).get("items").getAsInt();
    }

    /**
     * Given a map, returns the mob capacity
     * @param map the map name
     * @return the mob capacity
     * @throws NoSuchElementInConfigurationException throws if there is no such name
     */
    public int getMobCapacity(String map) throws NoSuchElementInConfigurationException {
        if(configuration.get(map) == null){
            throw new NoSuchElementInConfigurationException();
        }
        return configuration.getAsJsonObject(map).get("mobs").getAsInt();
    }

    /**
     * Given the doors number, returns a map with that doors number
     * @param doors the doors number
     * @return the map name
     */
    public String getRandomGivenDoors(int doors){
        LinkedList<String> tmp = new LinkedList<>();
        int index = 0;
        try{
            for(String name : getMapNames()){
                if(getDoors(name) == doors){
                    tmp.add(name);
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
