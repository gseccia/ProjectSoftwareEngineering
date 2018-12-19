package missions;

import configuration.EnemyConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Enemy;
import elements.NotPositiveValueException;
import elements.NullAnimationException;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.util.Random;

public class KillTheBossMission extends Mission {

    private Enemy boss;

    /**
     * Creates the mission selecting a boss mob (an ordinary mob with powered-up stats and changed color
     * @param targetId the mob
     * @param powerUp the strengthening factor
     */
    public KillTheBossMission(String targetId, int powerUp) {
        super(targetId);
        int red = new Random().nextInt(255);
        int green = 255 - red;
        int blue = red/2;
        try {
            boss = new Enemy(EnemyConfiguration.getInstance(), targetId);
            boss.makeBoss(powerUp, new Color(red, green, blue));
        } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException | NotPositiveValueException e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds a mission to the list
     *
     * @param e the mission to add
     * @return true if the mission was added, false otherwise
     */
    @Override
    public boolean add(Mission e) {
        return false;
    }

    /**
     * @return the number of missions in the component
     */
    @Override
    public int numMissions() {
        return 0;
    }

    /**
     * @return true if the mission was completed, false otherwise
     */
    @Override
    public boolean completed() {
        return boss == null;
    }

    /**
     * Check if an item contributes to a mission
     *
     * @param item the item to check
     */
    @Override
    public void check(MissionTarget item) {
        if(item.equals(boss)) boss = null;
    }

    @Override
    public void produceTargets(StorageRoom acceptor) {
        acceptor.collectEnemy(boss);
    }

    /**
     * @return the number of interactions needed to complete the mission
     */
    @Override
    public int getNumInteractions() {
        return 1;
    }

    @Override
    public String toString(){
        String ret = "Kill the " + getTargetId() + " boss";
        if(completed()){
            return ret + "\n- COMPLETED!";
        }
        return ret;
    }
}
