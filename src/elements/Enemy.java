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
    private boolean attack,obstacle,favorY,favorX;
    
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
    	attack = false;
    	obstacle= false;
    	favorX = favorY = false;
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
			
			
			if(attack) {
				System.out.println(id+" "+map.getID()+" ATTACK MODE ");
				float px,py,dirX,dirY;
				px = player.getX();
				py = player.getY();
				dirX = (px - getX());
				dirY = (py - getY());
				if(obstacle) {
					if(map.getCollisionManager().wallCollision(map.getShiftX(), map.getShiftY(), this, (dirX > 0)?  Directions.RIGHT: Directions.LEFT) &&
							map.getCollisionManager().wallCollision(map.getShiftX(), map.getShiftY(), this, (dirY > 0)?  Directions.DOWN: Directions.UP)) {
						System.out.println(id+" "+map.getID()+" OBSTACLE MODE OFF");
						obstacle = false;
						favorY = (direction == Directions.LEFT || direction == Directions.RIGHT);
						favorX = !favorY;
					}
				}
				else if((dirX < -8 || dirX > 8) || favorX) {
					System.out.println(id+" "+map.getID()+" MOVE X");
					direction = (dirX > 0)?  Directions.RIGHT: Directions.LEFT;
					favorX = !(dirX < -8 || dirX > 8);
				}
				else if((dirY < -8 || dirY > 8) || favorY) {
					System.out.println(id+" "+map.getID()+" MOVE Y");
					direction = (dirY > 0)?  Directions.DOWN: Directions.UP;
					favorY = !(dirY < -8 || dirY > 8);
				}
				else {
					System.out.println(id+" "+map.getID()+" ON PLAYER");
					direction = -1;
				}
			}
			
			if(!map.getCollisionManager().wallCollision(map.getShiftX(), map.getShiftY(), this, direction)) {
					System.out.println(id+" "+map.getID()+" COLLIDE");
					
					int choosen = direction;
					if(attack) {
						// Ignore all walls!  Attack him! Destroy him!
						if(direction == Directions.LEFT || direction == Directions.RIGHT) {
							choosen = (player.getY() - getY() > 0) ? Directions.DOWN : Directions.UP;
						}
						else {
							choosen = (player.getX() - getX() > 0) ? Directions.RIGHT : Directions.LEFT;
						}
						obstacle = true;
						System.out.println(id+" "+map.getID()+" OBSTACLE MODE");
					}
					else {
						//  Choose a random free direction
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
					default:
						faceStill();
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
