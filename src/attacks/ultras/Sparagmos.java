package attacks.ultras;

import attacks.states.DamageEnemiesState;
import attacks.states.DrawOnCoordinatesWithSoundState;
import attacks.states.SpecialAttackState;
import blocks.Block;
import configuration.NoSuchElementInConfigurationException;
import configuration.SpecialAttackConfiguration;
import elements.*;
import managers.Directions;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import utils.Constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Sparagmos extends AnimatedElement implements SpecialAttack {

    private final static String id = "sparagmos";

    private final int RELOADING_TIME = Constants.framerate*7;
    private final int DAMAGE = 400;
    private int remaining = 0;
    private Image icon;
    private Intro intro;
    private boolean active, drawable;
    private SpecialAttackState current;
    private Player caster;
    private Map<Integer, Animation> animations;
    private Sound laser;
    float x, y, width, height;

    public Sparagmos(Player caster) {
        super();
        this.caster = caster;
        try {
            SpecialAttackConfiguration conf = SpecialAttackConfiguration.getInstance();
            icon = conf.getIcon(id);

            intro = new Intro(new Animation());

            animations = new HashMap<>();
            animations.put(Directions.UP, conf.getUpAnimation(id));
            animations.put(Directions.DOWN, conf.getDownAnimation(id));
            animations.put(Directions.LEFT, conf.getLeftAnimation(id));
            animations.put(Directions.RIGHT, conf.getRightAnimation(id));

            laser = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/sparagmos/laser.ogg");

        } catch (SlickException | NoSuchElementInConfigurationException | NullAnimationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recharges the attack
     */
    @Override
    public void reload() {
        if(remaining > 0){
            remaining--;
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
     * @return true if the SpecialAttack is ready to be used
     */
    @Override
    public boolean isReady() {
        return remaining == 0;
    }

    /**
     * @return true if the SpecialAttack blocks the normal course of the game
     */
    @Override
    public boolean isBlocking() {
        return true;
    }

    /**
     * @return true if the SpecialAttack is currently active
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * @return the icon of the SpecialAttack
     */
    @Override
    public Image getIcon() {
        return icon;
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
     * Executes an iteration step of the move
     */
    @Override
    public void iterationStep() {
        if(isActive() && current != null) {

            if (!current.executed()) current.execute();

            if (current.executed() && current.finished()) current = current.next();
        }
        else{
            active = false;
            remaining = RELOADING_TIME;
        }
    }

    @Override
    public void setDrawable(boolean flag) {
        drawable = flag;
    }

    @Override
    public boolean isDrawable() {
        return drawable;
    }

    @Override
    public Intro getIntro() {
        return intro;
    }

    private boolean isAtRange(Enemy e){
        Rectangle hitbox = new Rectangle(x, y, width, height);
        return hitbox.intersects(e);
    }
}
