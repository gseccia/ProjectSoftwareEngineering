package elements;

import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import main.java.Block;
import managers.Directions;
import missions.MissionItem;

import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


public class Enemy extends Mob implements MissionItem,Runnable {

    private String id;
    private Block map;
    private int direction;
    private Rectangle vision;
    private int directVision = 10;
    private int lateralVision = 1;
    private Player player;
    
    public Enemy(MobConfiguration configuration, String id, Block map, Player p) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        super(configuration, id);
        this.id = id;
        this.map = map;
        this.player = p;
        direction = Directions.LEFT;     // Suppose initial direction right
    }

    @Override
    public String getID() {
        return id;
    }
    
    public void init(int x,int y) {
    	setLocation(x+map.getShiftX()*map.getMap().getTileWidth(),y-map.getShiftY()*map.getMap().getTileHeight());
    	directVision *= map.getMap().getTileWidth();
    	lateralVision *= map.getMap().getTileHeight();
    	vision = new Rectangle(getX(), getY(),directVision,lateralVision);  // Vision 
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
    	boolean attack;
		 // Until I'm alive
		if(getHp()>0) {
			x = getX();
			y = getY();
			
			
			setLocation((int)(getX())-map.getShiftX()*map.getMap().getTileWidth(),(int)(getY())-map.getShiftY()*map.getMap().getTileHeight());
			
			switch(direction) {
				case Directions.LEFT:
					vision.setBounds(getX()-directVision, getY(),directVision,lateralVision);
					break;
				case Directions.RIGHT:
					vision.setBounds(getX(), getY(),directVision,lateralVision);
					break;
				case Directions.UP:
					vision.setBounds(getX(), getY()-directVision,lateralVision,directVision);
					break;
				case Directions.DOWN:
					vision.setBounds(getX(), getY(),lateralVision,directVision);
					break;
			}
			
			if(vision.intersects(player)) {
				// Attack him!
				attack = true;
				System.out.println(id+" "+map.getID()+" ATTACK!");
			}
			else attack = false;
			
			
			if(!map.getCollisionManager().wallCollision(map.getShiftX(), map.getShiftY(), this, direction)) {
					System.out.println(id+" "+map.getID()+" COLLIDE");
					
					//  Choose a random free direction
					int choosen = direction;
					Random r = new Random();
					while(choosen == direction) {
						switch(r.nextInt(4)) {
							case 0:
								choosen = Directions.UP;
								break;
							case 1:
								choosen = Directions.RIGHT;
								break;
							case 2:
								choosen = Directions.DOWN;
								break;
							case 3:
								choosen = Directions.LEFT;
								break;
						}
					}
					
					direction = choosen;	
				}
				
				setLocation(x,y);
				switch(direction) {
					case Directions.LEFT:
						x = x - 1;
						System.out.println(id+" "+map.getID()+" LEFT");
						faceLeft();
						break;
					case Directions.RIGHT:
						x = x + 1;
						System.out.println(id+" "+map.getID()+" RIGHT");
						faceRight();
						break;
					case Directions.UP:
						y = y - 1;
						System.out.println(id+" "+map.getID()+" UP");
						faceUp();
						break;
					case Directions.DOWN:
						y = y + 1;
						System.out.println(id+" "+map.getID()+" DOWN");
						faceDown();
						break;
				}
			setLocation(x,y);
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
