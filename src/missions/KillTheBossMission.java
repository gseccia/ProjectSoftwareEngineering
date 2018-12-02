package missions;

import configuration.EnemyConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Enemy;
import elements.NotPositiveValueException;
import elements.NullAnimationException;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class KillTheBossMission extends Mission {

    private Set<Enemy> population;

    public KillTheBossMission(String targetId, int powerUp) {
        super(targetId);
        population = new HashSet<>();
        Enemy boss = null;
        int red = new Random().nextInt(255);
        int green = 255 - red;
        int blue = red/2;
        try {
            boss = new Enemy(EnemyConfiguration.getInstance(), targetId);
            boss.makeBoss(powerUp, new Color(red, green, blue));
        } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException | NotPositiveValueException e) {
            e.printStackTrace();
        }
        population.add(boss);

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
        return population.size() == 0;
    }

    /**
     * Check if an item contributes to a mission
     *
     * @param item the item to check
     */
    @Override
    public void check(MissionItem item) {
        population.remove(item);
    }

    /**
     * @return the number of interactions needed to complete the mission
     */
    @Override
    public int getNumInteractions() {
        return 1;
    }

    /**
     * @return the mob set generated by the mission
     */
    @Override
    public Set<Enemy> getEnemySet() {
        return population;
    }

    @Override
    public String toString(){
        String ret = "You have to kill the " + getTargetId() + " boss";
        if(completed()){
            return ret + " - COMPLETED!";
        }
        return ret;
    }
}
