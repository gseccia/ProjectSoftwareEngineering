package elements;

import attacks.*;
import attacks.ultras.HoraHora;
import attacks.ultras.IUF;
import attacks.ultras.Sparagmos;
import attacks.ultras.SpecialAttack;
import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

import utils.Constants;

public class Player extends Mob {

    private boolean isAttacking = false;

    private final int RELOADING_TIME = Constants.framerate;
    private int attackDuration;
    private Sound step;
    private SpecialAttack ultra;

    public Player(MobConfiguration configuration, String id) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        super(configuration, id);
        setAttack(new ShortRangeAttack(this));
        step = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/step.ogg");
        ultra = new IUF(this);
    }

    @Override
    public Attack getAttack(){
        Attack tmp = super.getAttack();
        tmp.setHitbox();
        return tmp;
    }

    public SpecialAttack getUltra() {
        return ultra;
    }

    public void setUltra(SpecialAttack ultra) {
        this.ultra = ultra;
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
        ultra.reload();
    }

    private void playStep(){
        if(!step.playing()){
            step.play(1.0f, SoundStore.get().getMusicVolume() * 0.2f);
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
        ultra.draw();
    }

    /**
     * Changes the current animation with the up one
     */
    @Override
    public void faceUp() throws NullAnimationException {
        playStep();
        super.faceUp();
    }

    /**
     * Changes the current animation with the down one
     */
    @Override
    public void faceDown() throws NullAnimationException {
        playStep();
        super.faceDown();
    }

    /**
     * Changes the current animation with the right one
     */
    @Override
    public void faceRight() throws NullAnimationException {
        playStep();
        super.faceRight();
    }

    /**
     * Changes the current animation with the left one
     */
    @Override
    public void faceLeft() throws NullAnimationException {
        playStep();
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
        getAttack().attack();
        this.hasAttacked();
        super.attackRight();
    }

    @Override
    public void attackDown() throws NullAnimationException {
        getAttack().attack();
        this.hasAttacked();
        super.attackDown();

    }

    @Override
    public void attackUp() throws NullAnimationException {
        getAttack().attack();
        this.hasAttacked();
        super.attackUp();
    }

    @Override
    public void attackLeft() throws NullAnimationException {
        getAttack().attack();
        this.hasAttacked();
        super.attackLeft();
    }
}