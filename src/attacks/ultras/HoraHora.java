package attacks.ultras;

import attacks.states.*;
import blocks.Block;
import configuration.NoSuchElementInConfigurationException;
import configuration.SpecialAttackConfiguration;
import elements.*;
import managers.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import utils.Constants;

import java.util.HashSet;
import java.util.Set;

/**
 * The class that implements the Hora! special attack
 */
public class HoraHora extends StateSpecialAttack {

    private final static String ID = "horahora";
    private final static int RELOADING_TIME = Constants.framerate*15;
    private final static int MAXIMUM_DISTANCE = 128;

    private Player caster;
    private Sound starting, firstHora, horaHora, ending;
    private Intro intro;

    /**
     * Constructor
     * @param caster the Player object that casts the attack
     */
    public HoraHora(Player caster) {
        super(RELOADING_TIME, ID);
        this.caster = caster;
        try {
            Animation tmp;
            tmp = SpecialAttackConfiguration.getInstance().getRightAnimation(ID);
            setCurrent(tmp);

            intro = new Intro(SpecialAttackConfiguration.getInstance().getIntro(ID));

            starting = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/begin.ogg");
            firstHora = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/StartingHora.ogg");
            horaHora = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/Hora.ogg");
            ending = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/end.ogg");
        } catch (SlickException | NoSuchElementInConfigurationException | NullAnimationException e) {
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
     * @param b the current Block
     */
    @Override
    public void activate(Block b) {
    	if(isReady()){
    		active = true;
    		Set<Enemy> targets = new HashSet<>();
    		SpecialAttackState prev = new KillEnemiesState(targets);

    		prev = new SoundState(ending, new HandleMusicState(ResourceManager.getInstance(), -1, prev));

    		for(Enemy e : b.getEnemy()){
    			if (enemyIsAtRange(caster.getX(), e.getX()-b.getShiftX()*16, caster.getY(), e.getY()-b.getShiftY()*16)) {
    				targets.add(e);
    				prev = new DrawOnTargetWithSoundState(this, e, b.getShiftX(), b.getShiftY(), horaHora, prev);
    			}
    		}

    		current = new HandleMusicState(ResourceManager.getInstance(), -1, new SoundState(starting, new DrawIntroState(intro, firstHora, caster.getX()-50, caster.getY()-60, prev)));
    	}
    }

    /**
     * Check if the enemy at the x2, y2 coordinates is in the range of the ultra
     * @param x1 the caster's x
     * @param x2 the enemy's x
     * @param y1 the caster's y
     * @param y2 the enemy's y
     * @return true if the enemy is at range
     */
    private boolean enemyIsAtRange(float x1, float x2, float y1, float y2){
        return Math.abs(x1-x2) < MAXIMUM_DISTANCE && Math.abs(y1-y2) < MAXIMUM_DISTANCE;
    }
}
