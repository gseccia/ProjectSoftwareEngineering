package elements;

import attacks.StandardPlayerAttack;
import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.newdawn.slick.SlickException;

public class Player extends Mob {

    public Player(MobConfiguration configuration, String id) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        super(configuration, id);
        setAttack(new StandardPlayerAttack(this));
    }
}
