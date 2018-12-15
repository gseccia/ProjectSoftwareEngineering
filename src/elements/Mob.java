package elements;

import attacks.Attack;
import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import managers.Directions;
import org.newdawn.slick.*;
import org.newdawn.slick.util.pathfinding.Mover;

import java.util.*;

/**
 * This class represent all the mobile objects in the game, that have at least four different animations
 * (for the four directions) a HP value and an attack value. Probably this will become abstract
 */
public abstract class Mob extends AnimatedElement implements MultiAnimatable, Movable, Mover {

    /**
     * hp are the current hp, maxHP the total hp
     */
    private int hp, maxHp, attackDamage;
    private int currentDirection = Directions.DOWN;
    private Attack attack;
    private HashMap<String, Animation> faces;

    //NON SERVE A NIENTE
    //DA CANCELLARE
    public Mob(MobConfiguration configuration, String id, int x, int y) throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        super(configuration.getFaceStill(id), configuration.getWidth(id), configuration.getHeight(id), x, y);
        this.hp = configuration.getHp(id);
        this.maxHp = 100;
        this.attackDamage = configuration.getAttack(id);
        faces = new HashMap<>();
        Animation faceLeft = configuration.getFaceLeft(id);
        Animation faceRight = configuration.getFaceRight(id);
        Animation faceUp = configuration.getFaceUp(id);
        Animation faceDown = configuration.getFaceDown(id);
        Animation standStillDown = configuration.getFaceStill(id);
        Animation standStillUp = configuration.getFaceStill(id);
        Animation standStillLeft = configuration.getFaceStill(id);
        Animation standStillRight = configuration.getFaceStill(id);
        Animation attackDown = configuration.getAttackDown(id);
        Animation attackUp = configuration.getAttackUp(id);
        Animation attackLeft = configuration.getAttackLeft(id);
        Animation attackRight = configuration.getAttackRight(id);
        generateMap(faceLeft, faceRight, faceUp, faceDown, standStillDown, standStillUp, standStillRight, standStillLeft, attackDown, attackUp, attackLeft, attackRight);

    }

    public Mob(MobConfiguration configuration, String id) throws NullAnimationException, NoSuchElementInConfigurationException, SlickException {
        super(configuration.getFaceStillDown(id), configuration.getWidth(id), configuration.getHeight(id), 0, 0);
        this.hp = configuration.getHp(id);
        this.maxHp = hp;
        this.attackDamage = configuration.getAttack(id);
        faces = new HashMap<>();
        Animation faceLeft = configuration.getFaceLeft(id);
        Animation faceRight = configuration.getFaceRight(id);
        Animation faceUp = configuration.getFaceUp(id);
        Animation faceDown = configuration.getFaceDown(id);
        Animation standStillDown = configuration.getFaceStillDown(id);
        Animation standStillUp = configuration.getFaceStillUp(id);
        Animation standStillLeft = configuration.getFaceStillLeft(id);
        Animation standStillRight = configuration.getFaceStillRight(id);
        Animation attackDown = configuration.getAttackDown(id);
        Animation attackUp = configuration.getAttackUp(id);
        Animation attackLeft = configuration.getAttackLeft(id);
        Animation attackRight = configuration.getAttackRight(id);
        generateMap(faceLeft, faceRight, faceUp, faceDown, standStillDown, standStillUp, standStillRight, standStillLeft, attackDown, attackUp, attackLeft, attackRight);
    }
    
    /**
     * Creates a map with the animations
     *
     * @param faceLeft   the left animation
     * @param faceRight  the right animation
     * @param faceUp     the up animation
     * @param faceDown   the down animation
     * @param standStillDown the still animation down
     * @param standStillUp the sill animation up
     * @param standStillRight the still animation right
     * @param standStillLeft the still animation left
     * @param attackLeft the attack left animation
     * @param attackRight the attack right animation
     * @param attackUp the attack up animation
     * @param attackDown the attack down animation
     */
    private void generateMap(Animation faceLeft, Animation faceRight, Animation faceUp, Animation faceDown, Animation standStillDown, 
    		Animation standStillUp, Animation standStillRight, Animation standStillLeft, Animation attackDown, Animation attackUp,  Animation attackLeft, Animation attackRight) {
    	
        faces.put("left", faceLeft);
        faces.put("right", faceRight);
        faces.put("up", faceUp);
        faces.put("down", faceDown);
        faces.put("stillDown", standStillDown);
        faces.put("stillUp", standStillUp);
        faces.put("stillRight", standStillRight);
        faces.put("stillLeft", standStillLeft);
        faces.put("attackLeft", attackLeft);
        faces.put("attackRight", attackRight);
        faces.put("attackUp", attackUp);
        faces.put("attackDown", attackDown);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp < 0) {
            this.hp = 0;
        } else {
            this.hp = hp;
        }
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) throws NotPositiveValueException {
        if (maxHp <= 0) {
            throw new NotPositiveValueException("MaxHP value cannot be less or equal than 0!");
        }
        this.maxHp = maxHp;
    }

    public void heal(int amount){
        hp += amount;
        if(hp > maxHp){
            hp = maxHp;
        }
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public Attack getAttack() {
        return attack;
    }

    protected void setAttack(Attack attack){
        this.attack = attack;
    }

	public void setAttackDamage(int attackDamage) throws NotPositiveValueException {
        if (attackDamage < 0) {
            throw new NotPositiveValueException("Attack Damage value cannot be less or equal than 0!");
        }
        this.attackDamage = attackDamage;
    }

    /**
     * Reduces the current hp value of the client by an amount
     *
     * @param amount the damage amount
     */
    public void damage(int amount) {
        this.hp -= amount;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    /**
     * Changes the current animation with the up one
     */
    @Override
    public void faceUp() throws NullAnimationException {
        setCurrent(faces.get("up"));
        setCurrentDirection(Directions.UP);
    }

    /**
     * Changes the current animation with the down one
     */
    @Override
    public void faceDown() throws NullAnimationException {
        setCurrent(faces.get("down"));
        setCurrentDirection(Directions.DOWN);
    }

    /**
     * Changes the current animation with the right one
     */
    @Override
    public void faceRight() throws NullAnimationException {
        setCurrent(faces.get("right"));
        setCurrentDirection(Directions.RIGHT);
    }

    /**
     * Changes the current animation with the left one
     */
    @Override
    public void faceLeft() throws NullAnimationException {
        setCurrent(faces.get("left"));
        setCurrentDirection(Directions.LEFT);
    }

    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillDown() throws NullAnimationException {
        setCurrent(faces.get("stillDown"));
    }
    
    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillUp() throws NullAnimationException {
        setCurrent(faces.get("stillUp"));
    }
    
    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillRight() throws NullAnimationException {
        setCurrent(faces.get("stillRight"));
    }
    
    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillLeft() throws NullAnimationException {
        setCurrent(faces.get("stillLeft"));
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

	@Override
	public void attackDown() throws NullAnimationException {
		setCurrent(faces.get("attackDown"));
	}

	@Override
	public void attackUp() throws NullAnimationException {
		setCurrent(faces.get("attackUp"));
	}

	@Override
	public void attackLeft() throws NullAnimationException {
		setCurrent(faces.get("attackLeft"));	
	}

	@Override
	public void attackRight() throws NullAnimationException {
		setCurrent(faces.get("attackRight"));
	}

    public abstract boolean isReadyToAttack();

    public abstract void hasAttacked();
}