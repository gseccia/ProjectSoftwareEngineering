package elements;

import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import main.java.Block;
import managers.Directions;
import missions.MissionItem;

import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


public class Enemy extends Mob implements MissionItem {

    private String id;
    private Block map;
    private int direction,imposed_direction;
    private Rectangle vision;
    private int directVision;
    private int lateralVision;
    private int speed,surrendTime;
    private Player player;
    private boolean attack,obstacle,favorY,favorX;

    public Enemy(MobConfiguration configuration, String id) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
    	super(configuration, id);
    	this.id = id;
    	direction = Directions.LEFT;
	}
    
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
    	directVision = 8*map.getMap().getTileWidth();
    	lateralVision = 2*map.getMap().getTileHeight();
    	vision = new Rectangle(getX(), getY(),directVision,lateralVision);  // Vision
    	speed = 4;
    	surrendTime = 150;
    	attack = false;
    	obstacle= false;
    	favorX = favorY = false;
    	imposed_direction = -1;
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
			
			
			setLocation((int)(getX())-map.getShiftX()*map.getMap().getTileWidth(),(int)(getY())-map.getShiftY()*map.getMap().getTileHeight()); // Alignment
			
			// Vision Alignment
			switch(direction) {
				case Directions.LEFT:
					vision.setBounds(getX()-directVision + getWidth(), getY(),directVision,lateralVision);
					break;
				case Directions.RIGHT:
					vision.setBounds(getX(), getY(),directVision,lateralVision);
					break;
				case Directions.UP:
					vision.setBounds(getX(), getY() + getHeight()-directVision,lateralVision,directVision);
					break;
				case Directions.DOWN:
					vision.setBounds(getX(), getY(),lateralVision,directVision);
					break;
			}
			
			// Vision Logic
			if(vision.intersects(player)) {
				// Attack him!
				attack = true;
				surrendTime = 150;
				//System.out.println(id+" "+map.getID()+" ATTACK!");
			}
			else if(attack) {
				surrendTime--;
				//System.out.println(id+" "+map.getID()+" SURREDING "+surrendTime);
			}
			
			// Attack Mode Logic
			if(attack) {
				//System.out.println(id+" "+map.getID()+" ATTACK MODE ");
				
				float px,py,dirX,dirY;
				px = player.getX();
				py = player.getY();
				dirX = (px - getX());
				dirY = (py - getY());
				
				if(obstacle) {
					// Obstacle management
					boolean collideX,collideY;
					collideX = !map.getCollisionManager().wallCollision(map.getShiftX(), map.getShiftY(), this, (dirX > 0)?  Directions.RIGHT: Directions.LEFT);
					collideY = !map.getCollisionManager().wallCollision(map.getShiftX(), map.getShiftY(), this, (dirY > 0)?  Directions.DOWN: Directions.UP);
					if(!collideX && !collideY){
						
						boolean tmp =favorX;
						favorX = favorY;
						favorY = tmp;
						
						obstacle = false;
						imposed_direction = -1;
						//System.out.println(id+" "+map.getID()+" NOT COLLISION AROUND");
					}
					else if(!collideX) {
						favorX = true;
						favorY = false;
						//System.out.println(id+" "+map.getID()+"  NOT COLLISION X");
					}
					else if(!collideY) {
						favorX = false;
						favorY = true;
						//System.out.println(id+" "+map.getID()+"  NOT COLLISION Y");
					}
					else {
						attack = false;
						favorX = favorY = false;
						obstacle = false;
						//System.out.println(id+" "+map.getID()+"  I QUIT ");
					}
					
				}
				
				else if(((dirX < -8 || dirX > 8) && !favorY) || favorX) {
					// X movements
					//System.out.println(id+" "+map.getID()+" MOVE X");
					direction = (dirX > 0)?  Directions.RIGHT: Directions.LEFT;
					
					favorX = (dirX < -8 || dirX > 8);
				}
				
				else if(((dirY < -8 || dirY > 8) && !favorX) || favorY) {
					// Y movements
					//System.out.println(id+" "+map.getID()+" MOVE Y");
					direction = (dirY > 0)?  Directions.DOWN: Directions.UP;
					
					favorY = (dirY < -8 || dirY > 8);
				}
				else {
					// On player! Not needed movements
					//System.out.println(id+" "+map.getID()+" ON PLAYER");
					direction = -1;
					favorX = favorY = false;
				}
				
				 if(surrendTime == 0) attack = false; // Surrend to attack him ---> NEVER GIVE UP!
			}
			
			// Movements' logic  after a wall collision!
			if(direction!=-1 && !map.getCollisionManager().wallCollision(map.getShiftX(), map.getShiftY(), this, direction)) {
					//System.out.println(id+" "+map.getID()+" COLLIDE");
					
					int choosen = direction;
					if(attack) {
						// Attack Mode Movements Logic if there is an obstacle!
						if(obstacle) {
							
							if(direction == Directions.LEFT || direction == Directions.RIGHT) {
								choosen = (player.getY() - getY() >0) ? Directions.DOWN : Directions.UP;
							}
							else {
								choosen = (player.getX() - getX() >0) ? Directions.RIGHT : Directions.LEFT;
							}
							imposed_direction = choosen;
							
							//System.out.println(id+" "+map.getID()+" DEVIATION ---> IMPOSED MOVE "+imposed_direction);
						}
						else {
							//System.out.println(id+" "+map.getID()+" OBSTACLE MODE");
							obstacle = true;
							if(direction == Directions.LEFT || direction == Directions.RIGHT) {
								choosen = (direction == Directions.RIGHT) ? Directions.LEFT : Directions.RIGHT;
							}
							else {
								choosen = (direction == Directions.UP) ? Directions.DOWN : Directions.UP;
							}
						}
						
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
					
					if(imposed_direction ==-1) direction = choosen;
					else direction = imposed_direction;
			}
			
			setLocation(x,y); // Restore Correct Position
			
			// Movements Action!
			switch(direction) {
					case Directions.LEFT:
						x = x - speed;
						//System.out.println(id+" "+map.getID()+" LEFT");
						faceLeft();
						break;
					case Directions.RIGHT:
						x = x + speed;
						//System.out.println(id+" "+map.getID()+" RIGHT");
						faceRight();
						break;
					case Directions.UP:
						y = y - speed;
						//System.out.println(id+" "+map.getID()+" UP");
						faceUp();
						break;
					case Directions.DOWN:
						y = y + speed;
						//System.out.println(id+" "+map.getID()+" DOWN");
						faceDown();
						break;
					default:
						faceStill();
				}
				
			setLocation(x,y); // Position Updating
		}
	}
    
    public Rectangle getVision() {
    	return vision;
    }
}
