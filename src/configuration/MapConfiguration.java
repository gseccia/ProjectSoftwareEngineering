package configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import converter.XMLToHashMapConverter;

public class MapConfiguration extends Configuration {
	private static final String filename = System.getProperty("user.dir") + "/resource/maps/maps.conf";
    private static MapConfiguration instance = null;
    private JsonObject configuration = null;

    public static MapConfiguration getInstance(){
        if(instance == null){
            instance = new MapConfiguration();
        }
        return instance;
    }
    
    private MapConfiguration() 
    {
    	// instance configuration
    	// try to open the file if it does not exist create it using createConfigurationFile()
    	// the methods assumes the maps are in "/resource/maps/" directory
    	
    	try {
			createConfigurationFile();
			this.configuration = super.uploadConfiguration(filename);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
    	
    }

    public void createConfigurationFile() throws ParserConfigurationException, SAXException, IOException {
    	HashMap<String,ArrayList<String>> dataMap = prepareJsonObject();

        //1. Create a Gson instance with pretty format
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //2. Convert object to JSON string and save into a file directly
        try (FileWriter writer = new FileWriter(MapConfiguration.filename, false)) {
            gson.toJson(dataMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private HashMap<String,ArrayList<String>> prepareJsonObject() throws ParserConfigurationException, SAXException, IOException {
    	HashMap<String,ArrayList<String>> new_data = new HashMap<>();
    	
    	HashMap<String,ArrayList<String>> data = XMLToHashMapConverter.getLayersFromXml();
    	for(HashMap.Entry<String, ArrayList<String>> entry: data.entrySet()) {
    		if (entry.getKey().matches(".*/.*")) {
    			new_data.put(
    					entry.getKey().split("/")[1].split("\\.")[0], 
    					entry.getValue()
    						);
    		} else
    		{
    			new_data.put(
    					entry.getKey().split("\\.")[0], 
    					entry.getValue()
    						);
    		}
    	}
		System.out.println(new_data);
    	return new_data;
    }

	@Override
	protected JsonObject getConfiguration(String id) {
		// carica il conf file da nome mappa=id
		return this.configuration.getAsJsonObject(id);
	}

	public TiledMap getMapTiled(String id) {
		/* Get MapTiled object from file name
		 @param id is the name of the file without extension 
		 */
		TiledMap tm = null;
        try {
			tm = new TiledMap(id);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return tm;
	}
	
	public ArrayList<String> getLayers(String id){
<<<<<<< Updated upstream
        JsonArray ja = this.configuration.getAsJsonArray();
        ArrayList<String> listLayers = new ArrayList<>();
        for (JsonElement e : ja)
        {
        	listLayers.add(e.getAsString());
=======
        JsonArray ja = this.configuration.getAsJsonArray(id);
        ArrayList<String> listLayers = new ArrayList<>();
        for(JsonElement je : ja) {
        	listLayers.add(je.getAsString());
>>>>>>> Stashed changes
        }
        return listLayers;
    }
}
