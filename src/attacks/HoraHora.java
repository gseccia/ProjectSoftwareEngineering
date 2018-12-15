package attacks;

import attacks.states.*;
import blocks.Block;
import configuration.AttackConfiguration;
import configuration.NoSuchElementInConfigurationException;
import configuration.SpecialAttackConfiguration;
import elements.*;
import main.ResourceManager;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import utils.Constants;

import java.util.HashSet;
import java.util.Set;

public class HoraHora extends AnimatedElement implements SpecialAttack {

    private final static String id = "horahora";

    private final int RELOADING_TIME = Constants.framerate*15;
    private final float MAXIMUM_DISTANCE = 128;
    private int remaining = 0;
    private Image icon;
    private boolean active, drawable;
    private SpecialAttackState current;
    private Player caster;
    private Sound starting, firstHora, horaHora, ending;
    private Intro intro;

    public HoraHora(Player caster) {
        super();
        this.caster = caster;
        try {
            icon = SpecialAttackConfiguration.getInstance().getIcon(id);

            Animation tmp;
            tmp = SpecialAttackConfiguration.getInstance().getRightAnimation(id);
            setCurrent(tmp);

            intro = new Intro(SpecialAttackConfiguration.getInstance().getIntro(id));

            starting = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/begin.ogg");
            firstHora = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/StartingHora.ogg");
            horaHora = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/Hora.ogg");
            ending = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/end.ogg");
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
        if(intro.playing()){
            intro.draw();
        }
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

    @Override
    public void setDrawable(boolean flag) {
        drawable = flag;
    }

    @Override
    public boolean isDrawable() {
        return drawable;
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
    		Set<Enemy> targets = new HashSet<>();
    		SpecialAttackState prev = new KillEnemiesState(targets);

    		prev = new SoundState(ending, new HandleMusicState(ResourceManager.getInstance(), -1, prev));

    		for(Enemy e : b.getEnemy()){
    			if (enemyIsAtRange(caster.getX(), e.getX()-b.getShiftX()*16, caster.getY(), e.getY()-b.getShiftY()*16)) {
    				targets.add(e);
    				prev = new DrawOnTargetWithSoundState(this, e, b.getShiftX(), b.getShiftY(), horaHora, prev);
    			}
    		}

    		current = new HandleMusicState(ResourceManager.getInstance(), 4, new SoundState(starting, new DrawIntroState(intro, firstHora, caster.getX()-50, caster.getY()-60, prev)));
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

    private boolean enemyIsAtRange(float x1, float x2, float y1, float y2){
        return Math.abs(x1-x2) < MAXIMUM_DISTANCE && Math.abs(y1-y2) < MAXIMUM_DISTANCE;
    }

    @Override
    public Intro getIntro() {
        return intro;
    }
}
