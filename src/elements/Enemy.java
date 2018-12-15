package elements;

import attacks.Attack;
import attacks.PointBlankRangeAttack;
import configuration.EnemyConfiguration;
import org.newdawn.slick.util.pathfinding.*;
import configuration.NoSuchElementInConfigurationException;
import blocks.Block;
import managers.CollisionDetectionDoor;
import managers.CollisionDetectionMob;
import managers.CollisionDetectionWall;
import managers.Directions;
import missions.MissionTarget;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import utils.Constants;


public class Enemy extends Mob implements MissionTarget {

    private String id;
    private Block map;
    private int direction,untilNextAttack;
    private Rectangle vision;
    private int directVision;
    private int lateralVision;
    private int surrendTime;
    private Player player;
    private CollisionDetectionWall wallCollision;
    private CollisionDetectionDoor doorCollision;
    private CollisionDetectionMob enemyCollision;
    private boolean attack;
    private int points;
    private Color bossFilter;
    private AStarPathFinder pf;
    
    
    private final int RELOADING_TIME = Constants.framerate;
    private final int SPEED = 8;
    private final int SURREND_TIME = 150;

    public Enemy(EnemyConfiguration configuration, String id) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
    	super(configuration, id);
    	this.id = id;
		this.points = configuration.getMobPoints(id);
    	direction = Directions.LEFT;
    	setAttack(new PointBlankRangeAttack(this));
	}
    
    public Enemy(EnemyConfiguration configuration, String id, Block map, Player p) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        super(configuration, id);
        this.id = id;
        this.map = map;
        this.player = p;
		this.points = configuration.getMobPoints(id);
        direction = Directions.LEFT;     // Suppose initial direction right
		setAttack(new PointBlankRangeAttack(this));
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
    	enemyCollision = new CollisionDetectionMob(map.getHitbox(),this);
    	surrendTime = SURREND_TIME;
    	attack = false;
    	untilNextAttack = 0;
    	pf = new AStarPathFinder(new EncapsulateMap(map.getMap(),map.getHitbox().getDoors()),1000,false);
		
    }
    
    public void draw() {
    	int x,y,px,py;
    	if(getHp()>0) {
    	    px = (int)(getX());
    		py = (int)(getY());
    		x = (int)(getX())-map.getShiftX()*map.getMap().getTileWidth();
    		y = (int)(getY())-map.getShiftY()*map.getMap().getTileHeight();
    		setLocation(x,y);
    		super.draw(bossFilter);
    		setLocation(px,py);
    	}
    }

    public void makeBoss(int powerUp, Color filter) throws NotPositiveValueException {
    	points *= powerUp;
    	setMaxHp(getMaxHp()*powerUp);
    	setHp(getHp()*powerUp);
    	setAttackDamage(getAttackDamage()*powerUp);
    	bossFilter = filter;
	}

	public int getMobPoints() {
		return this.points;
	}
	
	public void visionUpdate() {
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
	}
    
	private int toTile(float x,boolean xaxis) {
		int value = (int)(x/map.getMap().getTileWidth());
		value = (value<0)?0:value;
		if(xaxis)
			value=(value>=map.getMap().getWidth())?map.getMap().getWidth()-1:value;
		else
			value=(value>=map.getMap().getHeight())?map.getMap().getHeight()-1:value;
		return value;
	}
	
	private int attackMode() throws NullAnimationException {
		float x,mx,y,my;
		int dir;
		x = player.getX();
		y = player.getY();
		mx = getX();
		my = getY();
		
		player.setLocation(x + map.getShiftX()*map.getMap().getTileWidth(), y+map.getShiftY()*map.getMap().getTileHeight());
		setLocation(mx + map.getShiftX()*map.getMap().getTileWidth(), my+map.getShiftY()*map.getMap().getTileHeight());
		
		
		Path p = pf.findPath(this, toTile(getX(),true), toTile(getY(),false), toTile(player.getX(),true), toTile(player.getY(),false));
		if(p!=null && p.getLength()>1) {
			int goX = p.getX(1)*map.getMap().getTileWidth();
			int goY = p.getY(1)*map.getMap().getTileHeight();
			if(getX()>goX) dir=Directions.LEFT;
			else if(getX()<goX) dir=Directions.RIGHT;
			else if(getY()>goY) dir=Directions.UP;
			else if(getY()<goY) dir=Directions.DOWN;
			else dir = -1;
		}
		else dir = -1;
		
		setLocation(mx,my);
		player.setLocation(x,y);
		return dir;
	}
	
	public void update() throws NullAnimationException{
		float x,y;
		if(getHp()>0) {
			x = getX();
			y = getY();
			
			setLocation((int)(getX())-map.getShiftX()*map.getMap().getTileWidth(),(int)(getY())-map.getShiftY()*map.getMap().getTileHeight()); // Alignment
			
			// Vision Alignment
			visionUpdate();
			
			// Vision Logic
			if(vision.intersects(player)) {
				// Attack him!
				attack = true;
				surrendTime = SURREND_TIME;
			}
			else if(attack) {
				surrendTime--;
			}
			
			int choosen;	
			if(attack) {
				choosen = attackMode();
				if(surrendTime <= 0) attack = false;
			}
			else {
				choosen = direction;
				wallCollision.setKey(direction);
				enemyCollision.setKey(direction);
				if(!wallCollision.detectCollision(map.getShiftX(), map.getShiftY(), this) || enemyCollision.detectCollision(map.getShiftX(), map.getShiftY(), player) ||doorCollision.detectCollision(map.getShiftX(), map.getShiftY(), this)) {
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
			}
			direction = choosen;
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
