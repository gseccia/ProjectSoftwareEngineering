package elements;

import configuration.MobConfiguration;
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
     * Probably it will be refactored
     * @param id the mob id
     * @return
     */
    public static Mob generate(String id, int x, int y) throws SlickException, NullAnimationException{
        return new Mob(
                configuration.getHp(id),
                configuration.getAttack(id),
                configuration.getFaceStill(id),
                configuration.getFaceLeft(id),
                configuration.getFaceRight(id),
                configuration.getFaceUp(id),
                configuration.getFaceDown(id),
                configuration.getWidth(id),
                configuration.getHeight(id),
                x, y);
    }


    /**
     * Probably it will be refactored
     * @param id the mob id
     * @return
     */
    public static Mob generate(String id) throws SlickException, NullAnimationException{
            return new Mob(
                    configuration.getHp(id),
                    configuration.getAttack(id),
                    configuration.getFaceStill(id),
                    configuration.getFaceLeft(id),
                    configuration.getFaceRight(id),
                    configuration.getFaceUp(id),
                    configuration.getFaceDown(id),
                    configuration.getWidth(id),
                    configuration.getHeight(id),
                    0, 0);
    }

    private Mob(int hp, int attackDamage, Animation standStill, Animation faceLeft, Animation faceRight, Animation faceUp, Animation faceDown, int width, int height, int x, int y) throws NullAnimationException {
        super(standStill, width, height, x, y);
        this.hp = hp;
        this.maxHp = hp;
        this.attackDamage = attackDamage;
        faces = new HashMap<String, Animation>();
        generateMap(faceLeft, faceRight, faceUp, faceDown, standStill);
    }

    /**
     * Creates a map with the animations
     * @param faceLeft
     * @param faceRight
     * @param faceUp
     * @param faceDown
     */
    private void generateMap(Animation faceLeft, Animation faceRight, Animation faceUp, Animation faceDown, Animation standStill){
        faces.put("left", faceLeft);
        faces.put("right", faceRight);
        faces.put("up", faceUp);
        faces.put("down", faceDown);
        faces.put("still", standStill);
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
    public void faceUp() throws NullAnimationException{
        setCurrent(faces.get("up"));
    }

    /**
     * Changes the current animation with the down one
     */
    @Override
    public void faceDown() throws NullAnimationException{
        setCurrent(faces.get("down"));
    }

    /**
     * Changes the current animation with the right one
     */
    @Override
    public void faceRight() throws NullAnimationException{
        setCurrent(faces.get("right"));
    }

    /**
     * Changes the current animation with the left one
     */
    @Override
    public void faceLeft() throws NullAnimationException{
        setCurrent(faces.get("left"));
    }

    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStill() throws NullAnimationException{
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

    /**
     * Set the absolute position of a charachter
     *
     * @param x
     * @param y
     */
    @Override
    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }
}