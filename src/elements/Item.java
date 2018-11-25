package elements;

import missions.MissionItem;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

public class Item extends AnimatedElement implements MissionItem {

    private String id;

    public Item(String id) throws NullAnimationException {
        //TODO change with values from configuration
        super(new Animation(), 0, 0, 0, 0);
        this.id = id;
    }
    
    /**
     * Overrides (?) the original intersects method to avoid annoying collisions due to rubbing
     * @param r colliding element
     * @return true if intersection is verified, otherwise false
     */
    public boolean intersects(Rectangle r){
        if(super.intersects(r)){
            if(r.getY() == (y+height)){
                return false;
            }
            else if((r.getY()+r.getHeight()) == y){
                return false;
            }
            else if(r.getX() == (x+width)){
                return false;
            }
            else if((r.getX()+r.getWidth()) == x){
                return false;
            }
            else{
                return true;
            }
        }
        return false;
    }

    @Override
    public String getID() {
        return id;
    }
}
