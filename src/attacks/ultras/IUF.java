package attacks.ultras;

import attacks.states.*;
import blocks.Block;
import configuration.NoSuchElementInConfigurationException;
import configuration.SpecialAttackConfiguration;
import elements.*;
import main.ResourceManager;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import utils.Constants;

import java.util.HashSet;
import java.util.Set;

public class IUF extends AnimatedElement implements SpecialAttack {

    private final static String id = "iuf";

    private Set<AnimatedElement> flames;
    private final int RELOADING_TIME = Constants.framerate*10;
    private final int DAMAGE = 100;
    private int remaining = 0;
    private Image icon;
    private Intro intro;
    private boolean active, drawable;
    private SpecialAttackState current;
    private Player caster;
    private Sound introSound, blaze;

    public IUF(Player caster) {
        super();
        this.caster = caster;
        try {
            intro = new Intro(SpecialAttackConfiguration.getInstance().getIntro(id));

            icon = SpecialAttackConfiguration.getInstance().getIcon(id);

            introSound = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/iuf/iuf.ogg");
            blaze = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/iuf/burning.ogg");
        } catch (NullAnimationException | SlickException | NoSuchElementInConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draw the current animation at a defined point.
     */
    @Override
    public void draw() {
        if(intro.playing()){
            intro.draw();
        }
        if(isDrawable()){
            for(AnimatedElement flame : flames){
                flame.draw();
            }
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
            try {
                SpecialAttackConfiguration conf = SpecialAttackConfiguration.getInstance();
                active = true;
                flames = new HashSet<>();
                for (Enemy e : b.getEnemy()) {
                    AnimatedElement tmp = new Intro(conf.generateAnimation(id, "flame"));
                    tmp.setX(e.getX()-b.getShiftX()*16);
                    tmp.setY(e.getY()-b.getShiftY()*16-120);
                    flames.add(tmp);
                }
                current = new HandleMusicState(ResourceManager.getInstance(), -1, new DamageEnemiesState(b.getEnemy(), DAMAGE));
                if(!b.getEnemy().isEmpty()){
                    current = new DrawWithSoundState(this, blaze, current);
                }
                current = new HandleMusicState(ResourceManager.getInstance(), -1,
                        new DrawIntroState(intro, introSound, caster.getX()-30, caster.getY()-28, current));
            } catch (NullAnimationException | SlickException | NoSuchElementInConfigurationException e) {
                e.printStackTrace();
            }
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
}
