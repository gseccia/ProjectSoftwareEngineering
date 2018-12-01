package elements;

import attacks.Attack;
import attacks.StandardEnemyAttack;
import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import main.Block;
import managers.CollisionDetectionDoor;
import managers.CollisionDetectionWall;
import managers.Directions;
import missions.MissionItem;

import java.util.Random;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


public class Enemy extends Mob implements MissionItem {

    private String id;
    private Block map;
    private int direction, imposed_direction,untilNextAttack;
    private Rectangle vision;
    private int directVision;
    private int lateralVision;
    private int surrendTime;
    private Player player;
    private CollisionDetectionWall wallCollision;
    private CollisionDetectionDoor doorCollision;
    private boolean attack,obstacle,favorY,favorX;
    
    private final int RELOADING_TIME = 20;
    private final int SPEED = 8;
    private final int SURREND_TIME = 150;

    public Enemy(MobConfiguration configuration, String id) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
    	super(configuration, id);
    	this.id = id;
    	direction = Directions.LEFT;
    	setAttack(new StandardEnemyAttack(this));
	}
    
    public Enemy(MobConfiguration configuration, String id, Block map, Player p) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        super(configuration, id);
        this.id = id;
        this.map = map;
        this.player = p;
        direction = Directions.LEFT;     // Suppose initial direction right
		setAttack(new StandardEnemyAttack(this));
    }

    @Override
    public String getID() {
        return id;
    }
    
    public void init(int x,int y) {
    	setLocation(x*map.getMap().getTileWidth(),y*map.getMap().getTileHeight());
    	directVision = 8*map.getMap().getTileWidth();
    	lateralVision = 2*map.getMap().getTileHeight();
    	vision = new Rectangle(getX(), getY(),directVision,lateralVision);  // Vision
    	wallCollision = new CollisionDetectionWall(map.getHitbox());
    	doorCollision = new CollisionDetectionDoor(map.getHitbox());
    	surrendTime = SURREND_TIME;
    	attack = false;
    	obstacle= false;
    	favorX = favorY = false;
    	untilNextAttack = 0;
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
				surrendTime = SURREND_TIME;
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
					wallCollision.setKey( (dirX > 0)?  Directions.RIGHT: Directions.LEFT);
					collideX = !wallCollision.detectCollision(map.getShiftX(), map.getShiftY(), this);
					wallCollision.setKey( (dirY > 0)?  Directions.DOWN: Directions.UP);
					collideY = !wallCollision.detectCollision(map.getShiftX(), map.getShiftY(), this);
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
				
				else if((Math.abs(dirX) > player.getWidth() && !favorY) || favorX) {
					// X movements
					//System.out.println(id+" "+map.getID()+" MOVE X");
					direction = (dirX > 0)?  Directions.RIGHT: Directions.LEFT;
					System.out.println(id+" "+map.getID()+" PLAYER W "+player.getWidth());
					favorX = Math.abs(dirX) > player.getWidth(); //map.getMap().getTileWidth();(dirX < -player.getWidth() && dirX<0) || (dirX>0 && dirX > getWidth());
				}
				
				else if((Math.abs(dirY) > player.getHeight() && !favorX) || favorY) {
					// Y movements
					//System.out.println(id+" "+map.getID()+" MOVE Y");
					direction = (dirY > 0)?  Directions.DOWN: Directions.UP;
					System.out.println(id+" "+map.getID()+" PLAYER H "+player.getHeight());
					favorY = Math.abs(dirY) > player.getHeight(); //map.getMap().getTileHeight() Math.abs(dirY) > player.getHeight() + getHeight();
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
			wallCollision.setKey(direction);
			if(direction!=-1 && (!wallCollision.detectCollision(map.getShiftX(), map.getShiftY(), this) || doorCollision.detectCollision(map.getShiftX(), map.getShiftY(), this)) ) {
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
						x = x - SPEED;
						//System.out.println(id+" "+map.getID()+" LEFT");
						faceLeft();
						break;
					case Directions.RIGHT:
						x = x + SPEED;
						//System.out.println(id+" "+map.getID()+" RIGHT");
						faceRight();
						break;
					case Directions.UP:
						y = y - SPEED;
						//System.out.println(id+" "+map.getID()+" UP");
						faceUp();
						break;
					case Directions.DOWN:
						y = y + SPEED;
						//System.out.println(id+" "+map.getID()+" DOWN");
						faceDown();
						break;
					default: // No movements animation
						switch(getCurrentDirection()) {
							case Directions.LEFT:
								faceStillLeft();
								break;
							case Directions.RIGHT:
								faceStillRight();
								break;
							case Directions.UP:
								faceStillUp();
								break;
							case Directions.DOWN:
								faceStillDown();
								break;
						}
				}
				
			setLocation(x,y); // Position Updating
		}
	}
    
    public Rectangle getVision() {
    	return vision;
    }

    @Override
	public Attack getAttack(){
    	Attack tmp = super.getAttack();
    	tmp.setHitbox();
    	return tmp;
	}

	public int getDirection() {
		return direction;
	}

    public void setPlayer(Player player) {
    	this.player = player;
    }
    
    public void setMap(Block map) {
    	this.map = map;
    }

    @Override
    public boolean isReadyToAttack(){
		return untilNextAttack == 0;
	}

	@Override
	public void hasAttacked() {
    	untilNextAttack = RELOADING_TIME;
	}

	public void reloadAttack() {
    	if(untilNextAttack > 0) {
			untilNextAttack--;
		}
	}
}
