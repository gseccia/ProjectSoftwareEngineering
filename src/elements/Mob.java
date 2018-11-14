package elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.newdawn.slick.*;
import java.util.*;

/**
 * This class represent all the mobile objects in the game, that have at least four different animations
 * (for the four directions) a HP value and an attack value. Probably this will become abstract
 */
public class Mob extends AnimatedElement implements MultiAnimatable{

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
        Animation still = generateAnimation("resource/textures/sprites/guntan/still/", new String[]{"0.png", "1.png", "2.png", "3.png"}, new int[]{200, 13, 13, 13});
        return new Mob(100, 100, still, new Animation(), new Animation(), new Animation(), new Animation(), 16, 33, 0, 0);
    }

    /**
     * To be deleted
     * @param basePath
     * @param images
     * @param duration
     * @return
     * @throws SlickException
     */
    private static Animation generateAnimation(String basePath, String[] images, int[] duration) throws SlickException{
        Image[] arr = new Image[images.length];
        for(int i=0; i< images.length; i++){
            arr[i] = new Image(basePath+images[i]);
        }
        return new Animation(arr, duration);
    }

    private Mob(int hp, int attackDamage, Animation standStill, Animation faceLeft, Animation faceRight, Animation faceUp, Animation faceDown, int width, int height, int x, int y) {
        super(standStill, width, height, x, y);
        this.hp = hp;
        this.maxHp = hp;
        this.attackDamage = attackDamage;
        faces = new HashMap<String, Animation>();
        generateMap(faceLeft, faceRight, faceUp, faceDown, standStill);
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

    //Getter and setter
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
    @Override
    public void faceUp(){
        setCurrent(faces.get("up"));
    }

    /**
     * Changes the current animation with the down one
     */
    @Override
    public void faceDown(){
        setCurrent(faces.get("down"));
    }

    /**
     * Changes the current animation with the right one
     */
    @Override
    public void faceRight(){
        setCurrent(faces.get("right"));
    }

    /**
     * Changes the current animation with the left one
     */
    @Override
    public void faceLeft(){
        setCurrent(faces.get("left"));
    }

    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStill(){
        setCurrent(faces.get("still"));
    }

    /**
     * Move the character of a certain increment on the x axis based on the current position
     *
     * @param dx the increment of x position
     */
    @Override
    public void moveX(int dx) {
        setX(getX() + dx);
    }

    /**
     * Move the character of a certain position on the y axis based on the current position
     *
     * @param dy the increment of the y position
     */
    @Override
    public void moveY(int dy) {
        setY(getY() + dy);
    }
}