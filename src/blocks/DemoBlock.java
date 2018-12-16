package blocks;

import java.awt.Font;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.*;

import elements.EncapsulateMap;
import elements.Enemy;
import elements.Item;
import elements.Player;
import managers.Directions;
import managers.Wall;
import main.gamestates.GameStates;
import main.gamestates.StatesUtils;
import managers.observers.scoreboard.ScorePointsManager;
import map.MapGraph;
import missions.Mission;

public class DemoBlock extends Block{
	
	private Path currentPath;
	private int currentStep;
	private AStarPathFinder pf;
	private HashMap<Wall,Integer> doorLabel;
	private final int UNEXPLORED=0,EXPLORED=1;
	private Wall lastDoor;
	private boolean discovery;
	private boolean firstTime;
	private EncapsulateMap tmp;
	private static int updating = 0;
	private static int displayMessage = 0;
	private static boolean blocked = true;
	private String showString;
	
	protected DemoBlock(int state, String mapName) {
		super(state, mapName);
	}
	
	@Override
	public void initBlock(Player player,Map<Block,Set<Enemy>> population,Map<Block,Set<Item>> items,
			MapGraph graph, Mission missionGenerated, ScorePointsManager spm) throws SlickException
	{
		firstTime = true;
		super.initBlock(player, population, items, graph, missionGenerated, spm);
		tmp = new EncapsulateMap(getMap(),getHitbox().getDoors());
		pf = new AStarPathFinder(tmp,5000,false);
		enemy = new HashSet<>();
		currentPath = null;
		currentStep = 1;
		doorLabel = new HashMap<>();
		for(Wall door:getHitbox().getDoors()) {
			doorLabel.put(door, UNEXPLORED);
			System.out.println((tmp.blocked(null, tileConversion(door.getX(),true,false), tileConversion(door.getY(),true,false))? "NOT VALID":"VALID"));
		}
		lastDoor = null;
		discovery = true;
	}
	
	private int tileConversion(float value, boolean xaxis,boolean player) {
		int shiftX = (player)? getShiftX():0;
		int shiftY = (player)? getShiftY():0;
		int tile = (xaxis)? (int)(value/getMap().getTileWidth())+shiftX : (int)(value/getMap().getTileHeight())+shiftY;
		if(tile < 0 ) tile = 0;
		if(!xaxis && tile >= getMap().getHeight()) tile = getMap().getHeight()-1;
		else if(xaxis && tile >= getMap().getWidth()) tile = getMap().getWidth() -1;
		return tile;
	}
	
	private void generateTPath(Shape targetElement,boolean align) {
		int playerTileX, playerTileY, targetTileX, targetTileY;
		playerTileX = tileConversion(player.getX(),true,true);
		playerTileY = tileConversion(player.getY(),false,true);
		targetTileX = tileConversion(targetElement.getX(),true,align);
		targetTileY = tileConversion(targetElement.getY(),false,align);
		System.out.println("TARGET TILE "+targetTileX+" "+targetTileY);
		if(tmp.blocked(null, targetTileX, targetTileY)){
			System.out.println("BLOCKED");
		}
		else System.out.println("FREE");
		// System.out.println("PLAYER TILE "+playerTileX+" "+playerTileY);
		currentPath = pf.findPath(player, playerTileX, playerTileY, targetTileX, targetTileY);
		currentStep = 1;
	}
	
	@Override
	public void setCharacterSpawn(int d) {
		super.setCharacterSpawn(d);
		currentPath = null;
		currentStep = 1;
		if(discovery && !firstTime) {
			lastDoor = getHitbox().getDoors().get(d-1);
			doorLabel.put(lastDoor, EXPLORED);
			//System.out.println("BLOCCO " + super.getMapName() + " PORTA DISCOVERY -> "+tileConversion(lastDoor.getX(),true,false)+", "+tileConversion(lastDoor.getY(),false,false)+" <--> "+doorLabel.get(lastDoor));
			discovery = false;
		}
		System.out.println("STATE");
		for(Wall door:doorLabel.keySet()) {
			System.out.println("BLOCCO " + super.getMapName() + " PORTA "+((door == lastDoor)?"DISCOVERY":"")+" -> "+tileConversion(door.getX(),true,false)+", "+tileConversion(door.getY(),false,false)+" <--> "+doorLabel.get(door));
			
		}
		firstTime = false;
	}
	
	private void generatePath() {
		if((item.isEmpty() && enemy.isEmpty())) {
			// change map
			Wall doorSelected = null;
			Iterator<Wall> iter = doorLabel.keySet().iterator();
			while(iter.hasNext() && doorSelected==null) {
				Wall door = iter.next();
				if(doorLabel.get(door)==UNEXPLORED) {
					System.out.println("PORTA TROVATA");
					doorLabel.put(door, EXPLORED);					
					doorSelected = door;
				}
			}
			if(doorSelected == null) {
				doorSelected = lastDoor;
			    System.out.println("ASSIGNED LAST DOOR");
			}
			if(doorSelected != null) {
				generateTPath(doorSelected,false);
				if(currentPath==null)System.out.println("NON PATH - "+getMap().getWidth()+" "+getMap().getHeight());
			}
			else {
				System.out.println("Impossible errore");
			}
		}
		else if(enemy.isEmpty()){
			// item collecting
			generateTPath((Shape)item.toArray()[0],true);
		}
		else {
			// enemy killing
			generateTPath((Shape)enemy.toArray()[0],false);
		}
	}
	
	private int logicMovements() {
		if(currentPath == null || currentStep > currentPath.getLength() -1) {
			generatePath();
		}
		
		int direction;
		
		if(currentPath != null && currentStep<currentPath.getLength()) {
			int x,y,px,py;
			
			x =currentPath.getX(currentStep);
			y =currentPath.getY(currentStep);
			
			px = tileConversion(player.getX(),true,true);
			py = tileConversion(player.getY(),false,true);
			if(px < x) direction = Directions.RIGHT;
			else if(px > x) direction = Directions.LEFT;
			else if(py < y) direction = Directions.DOWN;
			else if(py > y) direction = Directions.UP;
			else direction = Directions.KEY_M;
		}
		else {
			direction = Directions.KEY_M;
			System.out.println("Attacca a caso");
		}
		return direction;
	}

	@Override
	public void generateNextLevel(GameContainer gc, StateBasedGame currentGame) {
		try {
            currentGame.init(gc);
            currentGame.enterState(GameStates.STARTING_POINT.getState());
        } catch (SlickException e) {
            e.printStackTrace();
        }
	}
	
	private void renderText(String showString,int x,int y) {
		// custom font settings
			uniFont = StatesUtils.changeSizeAndStyle(uniFont, 16f, Font.BOLD);
			Color fontColor = new Color (255, 0, 255);
			Color borderColor = new Color(105, 2, 2);
			StatesUtils.applyBorder(uniFont, showString, x, y, borderColor);
			uniFont.drawString(x, y, showString, fontColor);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		super.render(gc, sbg, g);
		
		switch(displayMessage) {
			case 0:
				showString = "Your name is at top-left corner";
				break;
			case 1:
				showString = "Your life is at left";
				break;
			case 2:
				showString = "Your score is at top-right corner";
				break;
			case 3:
				showString = "Your level is at center";
				break;
			case 4:
				showString = "Let's play!";
				break;
			default:
				showString = "";
				break;
		}  
		renderText(showString,((Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue())-uniFont.getWidth(showString))/2,250);
		
		if(currentPath != null) {
			g.setColor(Color.orange);
			for(int i=0;i<currentPath.getLength();i++) {
				g.drawRect((currentPath.getX(i)-getShiftX())*getMap().getTileWidth(),(currentPath.getY(i)-getShiftY())*getMap().getTileHeight(),16,16);
			}
			g.setColor(Color.green);
		}
	}
	
	public void update(GameContainer gc, StateBasedGame gs, int delta) {
		if(!blocked)super.update(gc, gs, delta);
		if(displayMessage<5 && updating>50) {
			displayMessage++;
			updating = 0;
		}
		else if(displayMessage<5) updating++;
		else blocked = false;
	}

	@Override
	protected boolean isPaused(Input in) {
		return in.isKeyPressed(in.KEY_P);
	}
	
	private boolean check(int direction) {
		boolean result = logicMovements() == direction;
		if(result) {
			currentStep ++;
		}
		return result;
	}

	@Override
	protected boolean goDown(Input in) {
		return check(Directions.DOWN);
	}

	@Override
	protected boolean goUp(Input in) {
		return check(Directions.UP);
	}

	@Override
	protected boolean goRight(Input in) {
		return check(Directions.RIGHT);
	}

	@Override
	protected boolean goLeft(Input in) {
		return check(Directions.LEFT);
	}

	@Override
	protected boolean attack(Input in) {
		return check(Directions.KEY_M);
	}

	/**
	 * Check if the user wants to perform the special attack
	 *
	 * @param in the Input object
	 * @return true if the player wants to perform the special attack
	 */
	@Override
	protected boolean special(Input in) { 
		return check(Directions.KEY_M);
	}
}
