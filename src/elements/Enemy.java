package elements;

import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import main.java.Block;
import managers.Directions;
import missions.MissionItem;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


public class Enemy extends Mob implements MissionItem,Runnable {

    private String id;
    private Block map;
    private int direction;
    private Rectangle vision;
    private Player player;
    
    public Enemy(MobConfiguration configuration, String id, Block map, Player p) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        super(configuration, id);
        this.id = id;
        this.map = map;
        this.player = p;
        vision = new Rectangle(0,0,3,1);  // Vision 
        direction = Directions.LEFT;     // Suppose initial direction right
        
    }

    @Override
    public String getID() {
        return id;
    }
    
    public void init(int x,int y) {
    	setLocation(x+map.getShiftX()*map.getMap().getTileWidth(),y-map.getShiftY()*map.getMap().getTileHeight());
    	vision.setLocation(getX(), getY());
    }
    
    public void draw() {
    	int x,y,px,py;
    	if(getHp()>0) {
    	    px = (int)(getX());
    		py = (int)(getY());
    		x = (int)(getX())-map.getShiftX()*map.getMap().getTileWidth();
    		y = (int)(getY())-map.getShiftY()*map.getMap().getTileHeight();
    		setLocation(x,y);
    		super.draw();
    		setLocation(px,py);
    	}
    }
    
    public void update() throws NullAnimationException {
    	float x,y;
		 // Until I'm alive
		if(getHp()>0) {
			x = getX();
			y = getY();
			
			if (direction == Directions.RIGHT) vision.setLocation(getX()-map.getShiftX()*map.getMap().getTileWidth()-vision.getWidth(), getY()-map.getShiftY()*map.getMap().getTileHeight());
			else vision.setLocation(getX()-map.getShiftX()*map.getMap().getTileWidth(), getY()-map.getShiftY()*map.getMap().getTileHeight());
			if(vision.intersects(player)) {
				// Attack him!
				System.out.println(id+" "+map.getID()+" ATTACK!");
			}
			else {
				
				setLocation((int)(getX())-map.getShiftX()*map.getMap().getTileWidth(),(int)(getY())-map.getShiftY()*map.getMap().getTileHeight());
				
				if(!map.getCollisionManager().wallCollision(map.getShiftX(), map.getShiftY(), this, direction)) {
					System.out.println(id+" "+map.getID()+" COLLIDE");
					if (direction == Directions.RIGHT) {
						direction = Directions.LEFT;
					}
					else {
						direction = Directions.RIGHT;
					}
				}
				
				setLocation(x,y);
				switch(direction) {
					case Directions.LEFT:
						x = x - 1;//map.getMap().getTileWidth();
						System.out.println(id+" "+map.getID()+" LEFT");
						faceLeft();
						break;
					case Directions.RIGHT:
						x = x + 1;//map.getMap().getTileHeight();
						System.out.println(id+" "+map.getID()+" RIGHT");
						faceRight();
						break;
				}
				setLocation(x,y);
			}
		}
    }

    
	@Override
	public void run() {
		try {
			while(getHp()>0) {
				update();
				Thread.sleep(100);
			}
		} catch (NullAnimationException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
