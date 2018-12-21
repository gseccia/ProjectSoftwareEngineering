package blocks;

import java.awt.Font;
import java.util.HashMap;
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

import configuration.PlayerCommands;
import elements.EncapsulateMap;
import elements.Enemy;
import elements.Item;
import elements.Player;
import elements.Wall;
import managers.ResourceManager;
import main.gamestates.GameStates;
import main.gamestates.Settings;
import main.gamestates.StatesUtils;
import managers.observers.scoreboard.ScorePointsManager;
import managers.strategy.Directions;
import map.MapGraph;
import missions.Mission;


/**
 * This class allows the demo execution of the game
 */
public class DemoBlock extends Block{
	
	private Path currentPath;
	private int currentStep,nextLevel;
	private AStarPathFinder pf;
	private HashMap<Wall,Integer> doorLabel;
	private final int UNEXPLORED=0,EXPLORED=1;
	private Wall lastDoor,doorSelected;
	private boolean discovery;
	private boolean firstTime;
	private static int updating = 0;
	private static int displayMessage = 0;
	private static boolean blocked = true;
	private static String[] showString = 
		{"Your name is at top-left corner","Your life is at left","Your score is at top-right corner",
				"Your special attack is at right","Your level is at center",
				"Press "+ ((PlayerCommands.getPlayerCommandsInstance().getUp() == Input.KEY_W)? "WASD":"ARROWs") +" to move in each direction","Press "+((PlayerCommands.getPlayerCommandsInstance().getAttack1() == Input.KEY_M)? "M":"Z")+" to attack",
				"Press "+((PlayerCommands.getPlayerCommandsInstance().getAttack2() == Input.KEY_SPACE)? "SPACE":"X")+" to use the special attack","Let's play!",""};
	
	
	/**
	 * Costructor of the demo block
	 * @param identifier of block as state of the game
	 * @param mapName name of map associated to this block
	 */
	protected DemoBlock(int state, String mapName) {
		super(state, mapName);
	}


	@Override
	public void initBlock(Player player,Map<Block,Set<Enemy>> population,Map<Block,Set<Item>> items,
			MapGraph graph, Mission missionGenerated, ScorePointsManager spm) throws SlickException
	{
		firstTime = true;
		super.initBlock(player, population, items, graph, missionGenerated, spm);
		pf = new AStarPathFinder(new EncapsulateMap(getMap(),getHitbox().getDoors()),5000,false);
		currentPath = null;
		currentStep = 1;
		doorLabel = new HashMap<>();
		for(Wall door:getHitbox().getDoors()) {
			doorLabel.put(door, UNEXPLORED);
		}
		continueString = "";
		doorSelected = null;
		lastDoor = null;
		discovery = true;
		nextLevel = 0;
	}
	
	@Override
	protected void reset(StateBasedGame gs) {
		updating = 0;
		displayMessage = 0;
		blocked = true;
		gs.enterState(GameStates.GAMEOVER.getState());
	}
	
	/**
	 * Convert value from pixels in tiles
	 * 
	 * @param value value to be converted
	 * @param xaxis Asserted if value is on x-axis
	 * @param player Asserted if it needs to execute the alignment
	 * @return tile value associated to the input parameter value
	 */
	private int tileConversion(float value, boolean xaxis,boolean player) {
		int shiftX = (player)? getShiftX():0;
		int shiftY = (player)? getShiftY():0;
		int tile = (xaxis)? (int)(value/getMap().getTileWidth())+shiftX : (int)(value/getMap().getTileHeight())+shiftY;
		if(tile < 0 ) tile = 0;
		if(!xaxis && tile >= getMap().getHeight()) tile = getMap().getHeight()-1;
		else if(xaxis && tile >= getMap().getWidth()) tile = getMap().getWidth() -1;
		return tile;
	}
	
	/**
	 * Generate a new path from player position to target element position
	 * 
	 * @param targetElement Element to reach
	 * @param align Asserted if it needs to execute the alignment of the target element
	 */
	private void generateTPath(Shape targetElement,boolean align) {
		int playerTileX, playerTileY, targetTileX, targetTileY;
		playerTileX = tileConversion(player.getX(),true,true);
		playerTileY = tileConversion(player.getY(),false,true);
		targetTileX = tileConversion(targetElement.getX(),true,align);
		targetTileY = tileConversion(targetElement.getY(),false,align);
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
			discovery = false;
		}
		firstTime = false;
	}
	
	/**
	 * Generate the next path
	 */
	private void generatePath() {
		if((item.isEmpty() && enemy.isEmpty())) {
			// change map
			Iterator<Wall> iter = doorLabel.keySet().iterator();
			while(iter.hasNext() && doorSelected==null) {
				Wall door = iter.next();
				if(doorLabel.get(door)==UNEXPLORED) {					
					doorSelected = door;
				}
			}
			if(doorSelected == null) {
				doorSelected = lastDoor;
			}
			if(doorSelected != null) {
				generateTPath(doorSelected,false);
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
	
	/**
	 * Provides next action to execute
	 * @return action to execute
	 */
	private int logicMovements() {
		if(doorSelected != null && currentPath!=null && currentStep >= currentPath.getLength() -2) {
			doorLabel.put(doorSelected, EXPLORED);
			doorSelected = null;
		}
		else if(currentPath == null || currentStep > currentPath.getLength() -1) {
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
		}
		return direction;
	}

	@Override
	public void generateNextLevel(GameContainer gc, StateBasedGame currentGame) {
		if(nextLevel > 50) {
    		ResourceManager.getInstance().setState(1);
        	levelMusicMustBeStarted = true;
        	try {
                currentGame.init(gc);
                currentGame.enterState(GameStates.STARTING_POINT.getState());
            } catch (SlickException e) {
                e.printStackTrace();
            }
		}
		nextLevel++;
	}
	
	/**
	 * Display text on screen
	 * 
	 * @param showString Text to display
	 * @param x x-value where diplays text
	 * @param y y-value where diplays text
	 */
	private void renderText(String showString,int x,int y) {
		// custom font settings
			uniFont = StatesUtils.changeSizeAndStyle(uniFont, 16f, Font.BOLD);
			Color fontColor = new Color (255, 0, 255);
			Color borderColor = new Color(105, 2, 2);
			StatesUtils.applyBorder(uniFont, showString, x, y, borderColor);
			uniFont.drawString(x, y, showString, fontColor);
			uniFont.destroy();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		super.render(gc, sbg, g);
		renderText(showString[displayMessage],((Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue())-uniFont.getWidth(showString[displayMessage]))/2,250);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) {
		if(!blocked)super.update(gc, gs, delta);
		if(displayMessage < showString.length-1 && updating>50) {
			displayMessage++;
			updating = 0;
		}
		else if(displayMessage< showString.length-1) updating++;
		else blocked = false;
	}

	@Override
	protected boolean isPaused(Input in) {
		return in.isKeyPressed(in.KEY_P);
	}
	
	/**
	 * Check if next action is the expected one and eventually update the current step
	 * 
	 * @param action action expected
	 * @return Asserted if next action is the expected one
	 */
	private boolean check(int action) {
		boolean result = logicMovements() == action;
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
		return check(Directions.KEY_M) && enemy.size() > 0;
	}

	@Override
	protected boolean special(Input in) { 
		return check(Directions.KEY_M) && enemy.size() > 0;
	}

}
