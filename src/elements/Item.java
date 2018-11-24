package elements;

import missions.MissionItem;
import org.newdawn.slick.Animation;

public class Item extends AnimatedElement implements MissionItem {

    private String id;

    public Item(String id) throws NullAnimationException {
        //TODO change with values from configuration
        super(new Animation(), 0, 0, 0, 0);
        this.id = id;
    }

    @Override
    public String getID() {
        return id;
    }
}
