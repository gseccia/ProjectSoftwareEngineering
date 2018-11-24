package elements;

import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import missions.MissionItem;
import org.newdawn.slick.SlickException;

public class Enemy extends Mob implements MissionItem {

    private String id;

    public Enemy(MobConfiguration configuration, String id) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        super(configuration, id);
        this.id = id;
    }

    @Override
    public String getID() {
        return id;
    }
}
