package elements;

import attacks.Attack;
import attacks.ShortRangeAttack;
import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utils.Constants;

public class Player extends Mob {

    private boolean isAttacking = false;
    private final int RELOADING_TIME = Constants.framerate;
    private int attackDuration;

    public Player(MobConfiguration configuration, String id) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        super(configuration, id);
        setAttack(new ShortRangeAttack(this));
    }

    @Override
    public Attack getAttack(){
        Attack tmp = super.getAttack();
        tmp.setHitbox();
        return tmp;
    }

    @Override
    public boolean isReadyToAttack(){
        return !isAttacking;
    }

    @Override
    public void hasAttacked() {
        isAttacking = true;
        attackDuration = RELOADING_TIME;
    }

    public void reloadAttack() {
        if(attackDuration > 0) {
            attackDuration--;
        }else{
            isAttacking=false;
        }
    }

    //Overrides the methods of Mob to draw also the attack animation

    /**
     * Draw the current animation at a defined point.
     */
    @Override
    public void draw() {
        if(isAttacking){
            getAttack().draw();
        }
        super.draw();
    }

    /**
     * Changes the current animation with the up one
     */
    @Override
    public void faceUp() throws NullAnimationException {
        super.faceUp();
    }

    /**
     * Changes the current animation with the down one
     */
    @Override
    public void faceDown() throws NullAnimationException {
        super.faceDown();
    }

    /**
     * Changes the current animation with the right one
     */
    @Override
    public void faceRight() throws NullAnimationException {
        super.faceRight();
    }

    /**
     * Changes the current animation with the left one
     */
    @Override
    public void faceLeft() throws NullAnimationException {
        super.faceLeft();
    }

    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillDown() throws NullAnimationException {
        super.faceStillDown();
    }

    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillUp() throws NullAnimationException {
        super.faceStillUp();
    }

    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillRight() throws NullAnimationException {
        super.faceStillRight();
    }

    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillLeft() throws NullAnimationException {
        super.faceStillLeft();
    }

    @Override
    public void attackRight() throws NullAnimationException {
        this.hasAttacked();
        super.attackRight();
    }

    @Override
    public void attackDown() throws NullAnimationException {
        this.hasAttacked();
        super.attackDown();

    }

    @Override
    public void attackUp() throws NullAnimationException {
        this.hasAttacked();
        super.attackUp();
    }

    @Override
    public void attackLeft() throws NullAnimationException {
        this.hasAttacked();
        super.attackLeft();
    }
}