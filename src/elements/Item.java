package elements;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import missions.MissionItem;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Item extends AnimatedElement implements MissionItem {

    private String id;
    private int itemPoints;

    public Item(ItemConfiguration configuration, String id) throws NullAnimationException, SlickException, NoSuchElementInConfigurationException {
        super(configuration.getItemAnimation(id),
                configuration.getWidth(id),
                configuration.getHeight(id),
                0, 0);
        this.id = id;
        this.itemPoints = 1;
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

	public int getItemPoints() {
		return this.itemPoints;
	}
}
