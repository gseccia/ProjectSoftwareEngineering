package elements;

import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.newdawn.slick.SlickException;

public class Enemy extends Mob{

    private String id;

    public Enemy(MobConfiguration configuration, String id) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        super(configuration, id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
