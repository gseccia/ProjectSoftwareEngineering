package elements;

import configuration.ItemConfiguration;
import configuration.NoSuchElementInConfigurationException;
import missions.MissionTarget;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import spawns.Spawner;
import visitors.NullModifier;
import visitors.Visitor;

import java.util.HashSet;
import java.util.Set;

/**
 * This class reprensents an item (collectable object).
 */
public class Item extends AnimatedElement implements MissionTarget, Visitor, Spawner {

    private String id;
    private int itemPoints;
    private Visitor visitor = new NullModifier();
    private Color filter;
    private boolean trap;
    private Set<Enemy> spawns = new HashSet<>();

    public Item(ItemConfiguration configuration, String id) throws NullAnimationException, SlickException, NoSuchElementInConfigurationException {
        super(configuration.getItemAnimation(id),
                configuration.getWidth(id),
                configuration.getHeight(id),
                0, 0);
        this.id = id;
        this.itemPoints = configuration.getItemPoints(id);
        this.filter = null;
        trap = false;
    }

    public Item(ItemConfiguration configuration, String id, Visitor visitor, Color filter, boolean trap) throws NullAnimationException, SlickException, NoSuchElementInConfigurationException {
        super(configuration.getItemAnimation(id),
                configuration.getWidth(id),
                configuration.getHeight(id),
                0, 0);
        this.id = id;
        this.visitor = visitor;
        this.itemPoints = configuration.getItemPoints(id);
        this.filter = filter;
        this.trap = trap;
    }

    public Item(ItemConfiguration configuration, String id, Visitor visitor, boolean trap) throws NullAnimationException, SlickException, NoSuchElementInConfigurationException {
        super(configuration.getItemAnimation(id),
                configuration.getWidth(id),
                configuration.getHeight(id),
                0, 0);
        this.id = id;
        this.visitor = visitor;
        this.itemPoints = configuration.getItemPoints(id);
        this.filter = null;
        this.trap = trap;
    }
    
    /**
     * Check if the object intersects with the rectangle r without considering the rubbing cases.
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
    public void visit(Mob mob) {
        visitor.visit(mob);
    }

    /**
     * Draw the current animation at a defined point.
     */
    @Override
    public void draw() {
        super.draw(filter);
    }

    public boolean isTrap(){
        return trap;
    }

    /**
     * @return a set of enemy to spawn
     */
    @Override
    public Set<Enemy> getSpawns() {
        return spawns;
    }

    /**
     * @param target the spawns
     */
    @Override
    public void setSpawns(Set<Enemy> target) {
        spawns = target;
    }
}
