package elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.newdawn.slick.*;
import java.util.*;

/**
 * This class represent all the mobile objects in the game, that have at least four different animations
 * (for the four directions) a HP value and an attack value
 */
public class Mob extends InteractiveElement {

    /**
     * hp are the current hp, maxHP the total hp
     */
    private int hp, maxHp, attackDamage;
    private HashMap<String, Animation> faces;
    private static MobConfiguration configuration = MobConfiguration.getInstance();

    /**
     * Probably it will be changed
     * @param id the mob id
     * @return
     */
    public static Mob generate(String id) throws SlickException{
        JsonObject mobConf = configuration.getMobConfiguration(id);
        JsonObject tmp = mobConf.getAsJsonObject("still");
        Animation still = generateAnimation(mobConf.get("base_folder").getAsString()+"still/", tmp.getAsJsonArray("frames"), tmp.getAsJsonArray("duration"));
        return new Mob(mobConf.get("hp").getAsInt(), mobConf.get("attack").getAsInt(), still, new Animation(), new Animation(), new Animation(), new Animation(), mobConf.get("width").getAsInt(), mobConf.get("height").getAsInt());
    }



    private Mob(int hp, int attackDamage, Animation standStill, Animation faceLeft, Animation faceRight, Animation faceUp, Animation faceDown) {
        super(standStill);
        this.hp = hp;
        this.maxHp = hp;
        this.attackDamage = attackDamage;
        faces = new HashMap<String, Animation>();
        generateMap(faceLeft, faceRight, faceUp, faceDown, standStill);
    }

    private Mob(int hp, int attackDamage, Animation standStill, Animation faceLeft, Animation faceRight, Animation faceUp, Animation faceDown, int width, int height) {
        super(standStill, width, height);
        this.hp = hp;
        this.maxHp = hp;
        this.attackDamage = attackDamage;
        faces = new HashMap<String, Animation>();
        generateMap(faceLeft, faceRight, faceUp, faceDown, standStill);
    }

    private Mob(int hp, int attackDamage, Animation standStill, Animation faceLeft, Animation faceRight, Animation faceUp, Animation faceDown, int width, int height, int x, int y) {
        super(standStill, width, height, x, y);
        this.hp = hp;
        this.maxHp = hp;
        this.attackDamage = attackDamage;
        faces = new HashMap<String, Animation>();
        generateMap(faceLeft, faceRight, faceUp, faceDown, standStill);
    }

    private static Animation generateAnimation(String basePath, JsonArray images, JsonArray duration) throws SlickException {
        Image[] arr = new Image[images.size()];
        int[] dur = new int[images.size()];
        for(int i=0; i< images.size(); i++){
            arr[i] = new Image(basePath+images.get(i).getAsString());
            dur[i] = duration.get(i).getAsInt();
        }
        return new Animation(arr, dur);
    }

    /**
     * Creates a map with the animations. Subclasses can override to add more faces
     * @param faceLeft
     * @param faceRight
     * @param faceUp
     * @param faceDown
     */
    protected void generateMap(Animation faceLeft, Animation faceRight, Animation faceUp, Animation faceDown, Animation standStill){
        faces.put("left", faceLeft);
        faces.put("right", faceRight);
        faces.put("up", faceUp);
        faces.put("down", faceDown);
        faces.put("still", standStill);
    }

    protected HashMap<String, Animation> getFaces() {
        return faces;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Reduces the current hp value of the client by an amount
     * @param amount
     */
    public void damage(int amount){
        this.hp -= amount;
    }

    /**
     * Changes the current animation with the up one
     */
    public void goUp(){
        setCurrent(faces.get("up"));
    }

    /**
     * Changes the current animation with the down one
     */
    public void goDown(){
        setCurrent(faces.get("down"));
    }

    /**
     * Changes the current animation with the right one
     */
    public void goRight(){
        setCurrent(faces.get("right"));
    }

    /**
     * Changes the current animation with the left one
     */
    public void goLeft(){
        setCurrent(faces.get("left"));
    }
}