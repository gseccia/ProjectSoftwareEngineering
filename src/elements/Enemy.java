package elements;

import attacks.Attack;
import attacks.PointBlankRangeAttack;
import configuration.EnemyConfiguration;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.pathfinding.*;
import configuration.NoSuchElementInConfigurationException;
import managers.strategy.CollisionDetectionDoor;
import managers.strategy.CollisionDetectionWall;
import managers.strategy.Directions;
import blocks.Block;
import missions.MissionTarget;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import utils.Constants;

/**
 * This class represents an enemy
 */
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
    private boolean attack;
    private int points;
    private Color bossFilter;
    private AStarPathFinder pf;
    private int RELOADING_TIME;
	private Sound activeSound;

    private final int SPEED = 8;
    private final int SURREND_TIME = 150;

    public Enemy(EnemyConfiguration configuration, String id) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
    	super(configuration, id);
    	this.id = id;
		this.points = configuration.getMobPoints(id);
    	direction = Directions.LEFT;
    	RELOADING_TIME = Constants.framerate * configuration.getAttackLatency(id);
    	activeSound = configuration.getActiveSound(id);
		setAttack(new PointBlankRangeAttack(this, configuration.getAttackSound(id)));
	}
    
    public Enemy(EnemyConfiguration configuration, String id, Block map, Player p) throws NoSuchElementInConfigurationException, SlickException, NullAnimationException {
        super(configuration, id);
        this.id = id;
        this.map = map;
        this.player = p;
		this.points = configuration.getMobPoints(id);
        direction = Directions.LEFT;
		RELOADING_TIME = Constants.framerate * configuration.getAttackLatency(id);
		activeSound = configuration.getActiveSound(id);
		setAttack(new PointBlankRangeAttack(this, configuration.getAttackSound(id)));
    }

    @Override
    public String getID() {
        return id;
    }
    
    /**
     * Initialize the object in the map where is placed
     * @param x tile position on x-axis
     * @param y tile position on y-axis
     */
    public void init(int x,int y) {
    	setLocation(x*map.getMap().getTileWidth(),y*map.getMap().getTileHeight());
    	directVision = 8*map.getMap().getTileWidth();
    	lateralVision = 2*map.getMap().getTileHeight();
    	vision = new Rectangle(getX(), getY(),directVision,lateralVision);  // Vision
    	wallCollision = new CollisionDetectionWall(map.getHitbox());
    	doorCollision = new CollisionDetectionDoor(map.getHitbox());
    	surrendTime = SURREND_TIME;
    	attack = false;
    	untilNextAttack = 0;
    	pf = new AStarPathFinder(new EncapsulateMap(map.getMap(),map.getHitbox().getDoors()),1000,false);
		
    }
    
    /**
     * Allows to draw the enemy
     */
    @Override
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
    
    /**
     * Allows to make this object a boss
     * @param powerUp A positive multiplier that improves the statistics of the enemy
     * @param filter Color to apply on this object
     * @throws NotPositiveValueException powerUP must be positive
     */
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
	
	/**
	 * Update the vision rectangle of an enemy
	 */
	private void visionUpdate() {
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
    
	/**
	 * Convert x from pixels in tiles
	 * @param x pixel value
	 * @param xaxis if Asserted indicates that x is on x-axis
	 * @return tile value corresponding to x pixel
	 */
	private int toTile(float x,boolean xaxis) {
		int value = (int)(x/map.getMap().getTileWidth());
		value = (value<0)?0:value;
		if(xaxis)
			value=(value>=map.getMap().getWidth())?map.getMap().getWidth()-1:value;
		else
			value=(value>=map.getMap().getHeight())?map.getMap().getHeight()-1:value;
		return value;
	}
	
	/**
	 * Define the attack mode behaviour
	 * @return Next action to perform
	 */
	private int attackMode() {
		float x,mx,y,my;
		int dir;
		x = player.getX();
		y = player.getY();
		mx = getX();
		my = getY();
		
		player.setLocation(x + map.getShiftX()*map.getMap().getTileWidth(), y+map.getShiftY()*map.getMap().getTileHeight());
		setLocation(mx + map.getShiftX()*map.getMap().getTileWidth(), my+map.getShiftY()*map.getMap().getTileHeight());
		
		
		Path p = pf.findPath(this, toTile(getX(),true), toTile(getY(),false), toTile(player.getX(),true), toTile(player.getY(),false));
		if(p!=null && p.getLength()>1 && !this.intersects(player)) {
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
	
	/**
	 * Define the behaviour of this object and updates it
	 * @throws NullAnimationException if the Animation is null
	 */
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
			
			int chosen;
			if(attack) {
				chosen = attackMode();
				if(surrendTime <= 0) attack = false;
			}
			else {
				chosen = direction;
				wallCollision.setKey(direction);
				if(!wallCollision.detectCollision(map.getShiftX(), map.getShiftY(), this) || doorCollision.detectCollision(map.getShiftX(), map.getShiftY(), this)) {

					if(!activeSound.playing()){
						activeSound.play(1, SoundStore.get().getMusicVolume()*0.5f);
					}

					//  Choose a random free direction
						Random r = new Random();
						while(chosen == direction) {
							switch(r.nextInt(4)) {
									case 0:
										chosen = Directions.UP;
										break;
									case 1:
										chosen = Directions.RIGHT;
										break;
									case 2:
										chosen = Directions.DOWN;
										break;
									case 3:
										chosen = Directions.LEFT;
										break;
							}
						}
				}
			}
			direction = chosen;
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
							attackLeft();
							break;
						case Directions.RIGHT:
							attackRight();
							break;
						case Directions.UP:
							attackUp();
							break;
						case Directions.DOWN:
							attackDown();
							break;
					}
			}
					
			setLocation(x,y); // Position Updating
		}
	}
	
    @Override
	public Attack getAttack(){
    	Attack tmp = super.getAttack();
    	tmp.setHitbox();
    	return tmp;
	}

    public void setPlayer(Player player) {
    	this.player = player;
    }
    
    public void setMap(Block map) {
    	this.map = map;
    }

	/**
	 * Check if this object it is ready to attack
	 */
    @Override
    public boolean isReadyToAttack(){
		return untilNextAttack == 0;
	}

	/**
	 * Reset loading time to attack
	 */
	@Override
	public void hasAttacked() {
    	getAttack().attack();
		untilNextAttack = RELOADING_TIME;
	}
	
	/**
	 * Update loading time to attack
	 */
	public void reloadAttack() {
    	if(untilNextAttack > 0) {
			untilNextAttack--;
		}
	}
}
