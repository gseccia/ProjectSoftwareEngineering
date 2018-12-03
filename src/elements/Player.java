package elements;

import attacks.Attack;
import attacks.ShortRangeAttack;
import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player extends Mob {

    private boolean isAttacking = false;

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
    public boolean isReadyToAttack() {
        return true;
    }

    @Override
    public void hasAttacked() { }

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
        isAttacking = false;
        super.faceUp();
    }

    /**
     * Changes the current animation with the down one
     */
    @Override
    public void faceDown() throws NullAnimationException {
        isAttacking = false;
        super.faceDown();
    }

    /**
     * Changes the current animation with the right one
     */
    @Override
    public void faceRight() throws NullAnimationException {
        isAttacking = false;
        super.faceRight();
    }

    /**
     * Changes the current animation with the left one
     */
    @Override
    public void faceLeft() throws NullAnimationException {
        isAttacking = false;
        super.faceLeft();
    }

    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillDown() throws NullAnimationException {
        isAttacking = false;
        super.faceStillDown();
    }

    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillUp() throws NullAnimationException {
        isAttacking = false;
        super.faceStillUp();
    }

    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillRight() throws NullAnimationException {
        isAttacking = false;
        super.faceStillRight();
    }

    /**
     * Changes the current animation with the still one
     */
    @Override
    public void faceStillLeft() throws NullAnimationException {
        isAttacking = false;
        super.faceStillLeft();
    }

    @Override
    public void attackRight() throws NullAnimationException {
        isAttacking = true;
        super.attackRight();
    }

    @Override
    public void attackDown() throws NullAnimationException {
        isAttacking = true;
        super.attackDown();
    }

    @Override
    public void attackUp() throws NullAnimationException {
        isAttacking = true;
        super.attackUp();
    }

    @Override
    public void attackLeft() throws NullAnimationException {
        isAttacking = true;
        super.attackLeft();
    }
}