package elements;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import missions.MissionTarget;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import visitors.PlayerModifier;

public class Item extends AnimatedElement implements MissionTarget, PlayerModifier {

    private String id;
    private int itemPoints;
    private PlayerModifier visitor = null;
    private Color filter;

    public Item(ItemConfiguration configuration, String id) throws NullAnimationException, SlickException, NoSuchElementInConfigurationException {
        super(configuration.getItemAnimation(id),
                configuration.getWidth(id),
                configuration.getHeight(id),
                0, 0);
        this.id = id;
        this.itemPoints = ItemConfiguration.getInstance().getItemPoints(this.id);
        this.filter = null;
    }

    public Item(ItemConfiguration configuration, String id, PlayerModifier visitor, Color filter) throws NullAnimationException, SlickException, NoSuchElementInConfigurationException {
        super(configuration.getItemAnimation(id),
                configuration.getWidth(id),
                configuration.getHeight(id),
                0, 0);
        this.id = id;
        this.visitor = visitor;
        this.itemPoints = ItemConfiguration.getInstance().getItemPoints(this.id);
        this.filter = filter;
    }

    public Item(ItemConfiguration configuration, String id, PlayerModifier visitor) throws NullAnimationException, SlickException, NoSuchElementInConfigurationException {
        super(configuration.getItemAnimation(id),
                configuration.getWidth(id),
                configuration.getHeight(id),
                0, 0);
        this.id = id;
        this.visitor = visitor;
        this.itemPoints = ItemConfiguration.getInstance().getItemPoints(this.id);
        this.filter = null;
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

    /**
     * Does something to the player
     *
     * @param player the player
     */
    @Override
    public void accept(Player player) {
        if(visitor != null){
            visitor.accept(player);
        }
    }

    /**
     * Draw the current animation at a defined point.
     */
    @Override
    public void draw() {
        super.draw(filter);
    }
}
