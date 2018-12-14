package attacks;

import attacks.states.*;
import blocks.Block;
import configuration.AttackConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.AnimatedElement;
import elements.Enemy;
import elements.NullAnimationException;
import elements.Player;
import main.ResourceManager;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import utils.Constants;

import java.util.HashSet;
import java.util.Set;

public class HoraHora extends AnimatedElement implements SpecialAttack {

    private final int RELOADING_TIME = Constants.framerate*10;
    private final float MAXIMUM_DISTANCE = 128;
    private int remaining = 0;
    private Image icon;
    private boolean active, drawable;
    private SpecialAttackState current;
    private Player caster;
    private Sound starting, firstHora, horaHora, ending;

    public HoraHora(Player caster) {
        super();
        this.caster = caster;
        Animation tmp = null;
        try {
            icon = new Image(System.getProperty("user.dir") + "/resource/textures/attack/horahora/icon.png");

            tmp = AttackConfiguration.getInstance().getRightAnimation("horahora");
            setCurrent(tmp);

            starting = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/begin.ogg");
            firstHora = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/StartingHora.ogg");
            horaHora = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/Hora.ogg");
            ending = new Sound(System.getProperty("user.dir") + "/resource/audio/sfx/horahora/end.ogg");
        } catch (SlickException | NoSuchElementInConfigurationException e) {
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

            prev = new SoundState(ending, new HandleMusicState(ResourceManager.getInstance(), 1, prev));

            for(Enemy e : b.getEnemy()){
                if (enemyIsAtRange(caster.getX(), e.getX()-b.getShiftX()*16, caster.getY(), e.getY()-b.getShiftY()*16)) {
                    targets.add(e);
                    SpecialAttackState tmp = new DrawOnTargetWithSoundState(this, e, b.getShiftX(), b.getShiftY(), horaHora, prev);
                    prev = tmp;
                }
            }

            current = new HandleMusicState(ResourceManager.getInstance(), 4, new SoundState(starting, new SoundState(firstHora, prev)));

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
}
