package configuration;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class MobConfiguration extends Configuration{
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
        this.configuration = super.uploadConfiguration(filename);
    }

    public int getHp(String id){
        return this.getConfiguration(id).get("hp").getAsInt();
    }

    public int getHeight(String id){
        return this.getConfiguration(id).get("height").getAsInt();
    }

    public int getWidth(String id){
        return this.getConfiguration(id).get("width").getAsInt();
    }

    public int getAttack(String id){
        return this.getConfiguration(id).get("attack").getAsInt();
    }

    public Animation getFaceUp(String id) throws SlickException {
        return generateAnimation(id,"up");
    }

    public Animation getFaceDown(String id) throws SlickException {
        return generateAnimation(id,"down");
    }

    public Animation getFaceLeft(String id) throws SlickException {
        return generateAnimation(id,"left");
    }

    public Animation getFaceRight(String id) throws SlickException {
        return generateAnimation(id,"right");
    }


    @Override
    protected JsonObject getConfiguration(String id) {
        return this.configuration.getAsJsonObject(id);
    }

    public Animation getFaceStill(String id) throws SlickException {
        return generateAnimation(id,"still");
    }

    private Animation generateAnimation(String id, String movement) throws SlickException {
        // load configuration from id
        JsonObject conf = this.getConfiguration(id);
        // load configuration for movement type
        JsonObject mov= conf.getAsJsonObject(movement);
        // load frame for movement type
        JsonArray images = mov.getAsJsonArray("frames");
        // load duration for each frame
        JsonArray duration = mov.getAsJsonArray("duration");
        // setup an animation as a sequence of image and movements
        Image[] arr = new Image[images.size()];
        int[] dur = new int[images.size()];
        String basePath = conf.get("base_folder").getAsString()+"/"+movement+"/";
        for(int i = 0; i< images.size(); i++){
            arr[i] = new Image(basePath+images.get(i).getAsString());
            dur[i] = duration.get(i).getAsInt();
        }
        return new Animation(arr, dur);
    }
}
