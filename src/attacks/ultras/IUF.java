package attacks.ultras;

import attacks.states.*;
import blocks.Block;
import configuration.NoSuchElementInConfigurationException;
import configuration.SpecialAttackConfiguration;
import elements.*;
import managers.ResourceManager;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import utils.Constants;

import java.util.HashSet;
import java.util.Set;

public class IUF extends StateSpecialAttack {

    private final static String ID = "iuf";
    private final static int RELOADING_TIME = Constants.framerate*10;
    private final static int DAMAGE = 100;

    private Set<AnimatedElement> flames;
    private Intro intro;
    private Player caster;
    private Sound introSound, blaze;

    public IUF(Player caster) {
        super(RELOADING_TIME, ID);
        this.caster = caster;
        try {
            intro = new Intro(SpecialAttackConfiguration.getInstance().getIntro(ID));

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
            try {
                SpecialAttackConfiguration conf = SpecialAttackConfiguration.getInstance();
                active = true;
                flames = new HashSet<>();
                for (Enemy e : b.getEnemy()) {
                    AnimatedElement tmp = new Intro(conf.generateAnimation(ID, "flame"));
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
}
