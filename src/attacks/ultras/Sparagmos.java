package attacks.ultras;

import attacks.states.DamageEnemiesState;
import attacks.states.DrawOnCoordinatesWithSoundState;
import blocks.Block;
import configuration.NoSuchElementInConfigurationException;
import configuration.SpecialAttackConfiguration;
import elements.*;
import managers.Directions;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import utils.Constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The class that implements the Sparagmos special attack
 */
public class Sparagmos extends StateSpecialAttack {

    private final static String ID = "sparagmos";
    private final static int RELOADING_TIME = Constants.framerate*7;
    private final static int DAMAGE = 400;

    private Player caster;
    private Map<Integer, Animation> animations;
    private Sound laser;
    private float x, y, width, height;

    /**
     * Constructor
     * @param caster the Player object that casts the attack
     */
    public Sparagmos(Player caster) {
        super(RELOADING_TIME, ID);
        this.caster = caster;
        try {
            SpecialAttackConfiguration conf = SpecialAttackConfiguration.getInstance();

            animations = new HashMap<>();
            animations.put(Directions.UP, conf.getUpAnimation(ID));
            animations.put(Directions.DOWN, conf.getDownAnimation(ID));
            animations.put(Directions.LEFT, conf.getLeftAnimation(ID));
            animations.put(Directions.RIGHT, conf.getRightAnimation(ID));

            laser = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/sparagmos/laser.ogg");

        } catch (SlickException | NoSuchElementInConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draw the current animation at a defined point.
     */
    @Override
    public void draw() {
        if(isDrawable()) {
            super.draw();
        }
    }

    /**
     * @return true if the SpecialAttack blocks the normal course of the game
     */
    @Override
    public boolean isBlocking() {
        return true;
    }

    /**
     * Activates the SpecialAttack
     *
     * @param b the current Block
     */
    @Override
    public void activate(Block b) {
        if(isReady()){
            active = true;
            int direction = caster.getCurrentDirection();
            float shiftX = b.getShiftX()*16;
            float shiftY = b.getShiftY()*16;
            int sx, sy;
            switch (direction){
                case Directions.UP:
                    sx = 4;
                    sy = -1000;
                    x = caster.getX() + shiftX;
                    y = caster.getY() - 1000 + shiftY;
                    width = 14;
                    height = 1000;
                    break;

                case Directions.DOWN:
                    sx = 4;
                    sy = 32;
                    x = caster.getX() + shiftX;
                    y = caster.getY() + shiftY;
                    width = 14;
                    height = 1000;
                    break;

                case Directions.RIGHT:
                    sx = 17;
                    sy = 8;
                    x = caster.getX() + shiftX;
                    y = caster.getY() + shiftY;
                    width = 1000;
                    height = 14;
                    break;

                case Directions.LEFT:
                    sx = -999;
                    sy = 8;
                    x = caster.getX() - 1000 + shiftX;
                    y = caster.getY() + shiftY;
                    width = 1000;
                    height = 14;
                    break;

                default:
                    sx = 0;
                    sy = 0;
                    x = 0;
                    y = 0;
                    width = 0;
                    height = 0;
            }
            setCurrent(animations.get(caster.getCurrentDirection()));
            Set<Enemy> targets = new HashSet<>();
            for(Enemy e : b.getEnemy()){
                if(isAtRange(e)){
                    targets.add(e);
                }
            }
            current = new DrawOnCoordinatesWithSoundState(this, laser, caster.getX()+sx, caster.getY()+sy, new DamageEnemiesState(targets, DAMAGE));
        }

    }

    /**
     * Check if an enemy collides with the beam
     * @param e the Enemy to check
     * @return true if collides
     */
    private boolean isAtRange(Enemy e){
        Rectangle hitbox = new Rectangle(x, y, width, height);
        return hitbox.intersects(e);
    }
}
