package blocks;

import java.awt.Font;
import java.util.*;

import managers.*;
import map.Edge;
import map.MapGraph;
import map.Vertex;
import missions.Mission;
import managers.observers.scoreboard.LifePointsAccumulatorObserver;
import managers.observers.scoreboard.PointsAccumulatorObserver;
import managers.observers.scoreboard.ScoreFileObserver;
import managers.observers.scoreboard.ScorePointsManager;
import managers.observers.scoreboard.States;
import managers.strategy.CollisionDetectionDoor;
import managers.strategy.CollisionDetectionEnemyAttacksPlayer;
import managers.strategy.CollisionDetectionItem;
import managers.strategy.CollisionDetectionPlayerAttacksEnemy;
import managers.strategy.CollisionDetectionTrap;
import managers.strategy.CollisionDetectionWall;
import managers.strategy.Directions;
import managers.strategy.HitboxMaker;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import configuration.PlayerCommands;

import elements.Enemy;
import elements.Item;
import elements.Mob;
import elements.Player;
import elements.Wall;
import main.gamestates.GameStates;
import main.gamestates.Pause;
import main.gamestates.StatesUtils;
import elements.NullAnimationException;

/**
 * This class allows to play in a single block of the map
 */
public abstract class Block extends BasicGameState
{
	private CollisionDetectionWall wallCollision;
	private CollisionDetectionDoor doorCollision;
	private CollisionDetectionItem itemCollision;
	private CollisionDetectionEnemyAttacksPlayer enemyCollision;
	private CollisionDetectionPlayerAttacksEnemy attackCollision;
	private CollisionDetectionTrap trapCollision;
	private TiledMap map;
	protected Player player;
	protected Set<Enemy> enemy;
	protected Set<Item> item;
	private int state;
	private int mapX, mapY, prevMapX, prevMapY;
	private boolean dead;
	private HitboxMaker hitbox;
	private String mapName;
	private MapGraph graph;
	private Vertex vertex;
	private Mission mission;
	private int levelNumber;
	private int key = Directions.DOWN;
	private ScorePointsManager scoreManager;
	private ScoreFileObserver sfo;
	private PointsAccumulatorObserver pao;
	private LifePointsAccumulatorObserver lpao;
	private ResourceManager rs;
	private MusicManager mm;
	protected boolean levelMusicMustBeStarted;
	private boolean completedMusicMustBeStarted;//Fonts
	protected org.newdawn.slick.UnicodeFont uniFont;
	protected String continueString;
	private String charName;
	private PlayerCommands pc = PlayerCommands.getPlayerCommandsInstance();
	private boolean doorEntered;


	protected Block(int state,String mapName)
	{
		this.state = state;
		this.mapName = mapName;
		this.rs = ResourceManager.getInstance();
		this.mm = MusicManager.getInstance(this.rs);
	}


	public void resetCompletedLevelMusic() {
		completedMusicMustBeStarted = true;
	}

	/**
	 *  This function initializes the block
	 * @param player is the user character
	 * @param population map that contains information about enemies in the blocks
	 * @throws SlickException if the map is not loaded correctly
	 */
	public void initBlock(Player player,Map<Block,Set<Enemy>> population,Map<Block,Set<Item>> items,
			MapGraph graph, Mission missionGenerated, ScorePointsManager spm) throws SlickException
	{
		map = new TiledMap("resource/maps/Complete"+mapName+"/"+mapName+".tmx");
		this.vertex = graph.getVertex(this);
		this.graph=graph;
		enemy = population.get(this);
		item = items.get(this);
		mission = missionGenerated;
		this.player = player;

		hitbox = new HitboxMaker(map, new LinkedList<>(enemy));
		hitbox.initiateHitbox();
		hitbox.setItems(new ArrayList<>(item));

		wallCollision = new CollisionDetectionWall(hitbox);
		doorCollision = new CollisionDetectionDoor(hitbox);
		itemCollision = new CollisionDetectionItem(hitbox);
		enemyCollision = new CollisionDetectionEnemyAttacksPlayer(hitbox);
		attackCollision = new CollisionDetectionPlayerAttacksEnemy(hitbox);
		trapCollision = new CollisionDetectionTrap(hitbox);


		// initialize score manager and observers
		this.scoreManager = spm;
		sfo = ScoreFileObserver.getInstance(this.scoreManager);
		pao = PointsAccumulatorObserver.getInstance(this.scoreManager);
		lpao = new LifePointsAccumulatorObserver(this.scoreManager);
		this.scoreManager.setNamePlayer("Armando");

//		Initialize Music completed level
		resetCompletedLevelMusic();
//		Init font
		StatesUtils.initFont();
	}

	@Override
	/**
	 * The block is initialized. Items and enemies are placed into the map
	 */
	public void init(GameContainer gc, StateBasedGame arg1) {
		setCharacterSpawn(1);
		doorEntered = false;
		int x, y,w,h;

		// Enemies spawn from a set of a random spawn points
		setEnemiesSpawn(enemy);

		Random r = new Random();
		boolean[][] occupied = hitbox.getOccupiedTiles();
		Wall tempWall = new Wall(0, 0, map.getTileWidth(), map.getTileHeight());
		for(Item i : item) {
			w = (int)(Math.ceil(i.getHeight()/map.getTileHeight()));
			h = (int)(Math.ceil(i.getWidth()/map.getTileWidth()));
			y = r.nextInt(map.getHeight()- h);
			x = r.nextInt(map.getWidth()- w);


			tempWall.setCenterX(x);
			tempWall.setCenterY(y);
			while(occupied[x][y] || occupied[x][y+h] || occupied[x+w][y] ) {

				y = r.nextInt(map.getHeight()-h);
				x = r.nextInt(map.getWidth()-w);

				tempWall.setCenterX(x);
				tempWall.setCenterY(y);

				for(Item j : item) {
					if(j.intersects(tempWall)) {
						tempWall.setCenterX(x+r.nextInt(10));
						tempWall.setCenterY(y+r.nextInt(10));
					}

				}
			}
			i.setLocation((x)*map.getTileWidth(),y*map.getTileHeight());
		}

		prevMapX = 0;
		prevMapY = 0;
		uniFont = StatesUtils.initFont();
	}

	/**
	 * Insert new enemies into the map
	 * @param newSpawns Enemies spawned
	 */
	private void updateEnemies(Set<Enemy> newSpawns){
		for(Enemy e : newSpawns){
			e.setPlayer(player);
			e.setMap(this);
		}
		enemy.addAll(newSpawns);
		setEnemiesSpawn(newSpawns);
		hitbox.updateMobs(new LinkedList<>(newSpawns));
	}

	/**
	 * Place enemies into the spawn points of the map
	 * @param enemies Enemies to be placed
	 */
	private void setEnemiesSpawn(Iterable<Enemy> enemies){
		int x, y, n = 1;
		for(Enemy e : enemies) {
			x = Integer.parseInt(map.getMapProperty("spawnX" + n, "-1"));
			y = Integer.parseInt(map.getMapProperty("spawnY" + n, "-1"));
			if (x == -1 || y == -1) {
				x = Integer.parseInt(map.getMapProperty("spawnX1", "-1"));
				y = Integer.parseInt(map.getMapProperty("spawnY1", "-1"));
				n = 1;
			}
			n++;
			e.init(x, y);
		}
	}

	/**
	 * Generate the next level
	 * @param gc the game container object
	 * @param currentGame the current game
	 */
	public abstract void generateNextLevel(GameContainer gc, StateBasedGame currentGame);

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) {
		g.scale(1.5f, 1.5f);
		map.render(0,0, mapX,mapY,mapX+50,mapY+50);
		for(Enemy e : enemy)
		{
			e.draw();
		}
		player.draw();
		for(Item i: item)
		{
			i.draw();
		}

		if(player.getUltra().isReady()){
			g.drawImage(player.getUltra().getIcon(), (Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue())-18*7, 50);
		}
		else{
			g.drawImage(player.getUltra().getIcon(), (Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue())-18*7, 50, Color.gray);
		}

		if(mission.completed()){
			g.setColor(Color.white);
            try {
            	uniFont = StatesUtils.changeSizeAndStyle(uniFont, 16f, Font.BOLD);

            	int width = ((Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue()) - uniFont.getWidth("LEVEL COMPLETED!"))/2;
            	int height = ((Long.valueOf(Math.round(gc.getHeight()/1.5)).intValue()))/2;
                g.fillRect(0, 0, gc.getWidth(), gc.getHeight(), new Image(System.getProperty("user.dir") + "/resource/textures/transitions/background.png"), 0, 0);

                StatesUtils.applyBorder(uniFont, "LEVEL COMPLETED!", width, height-15, new Color(105, 2, 2));
                uniFont.drawString(width, height-15, "LEVEL COMPLETED!", new Color(201, 2, 2));

                width = ((Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue()) - uniFont.getWidth(continueString))/2;

                StatesUtils.applyBorder(uniFont, continueString, width, height+15, new Color(105, 2, 2));
				uniFont.drawString(width, height+15, continueString, new Color(201, 2, 2));

				uniFont.destroy();

            	if(completedMusicMustBeStarted) {
            		this.rs.setState(2);
            		completedMusicMustBeStarted = false;
            	}
            	 generateNextLevel(gc, arg1);

            } catch (SlickException e) {
                e.printStackTrace();
            }
            
           

        }



		uniFont = StatesUtils.changeSizeAndStyle(uniFont, 16f, Font.BOLD);
		Color fontColor = new Color (255, 0, 255);
		Color borderColor = new Color(105, 2, 105);

//		LEFT SIDE OF THE SCREEN
//		Draw Char name and life
		int xChar = 10;
		int yChar = 15;

		StatesUtils.applyBorder(uniFont, charName, xChar, yChar, borderColor);
		uniFont.drawString(xChar, yChar, charName, fontColor);


		//		Draw hearts below char name
		int xHeart = xChar + 90;
		int yHeart = yChar + uniFont.getHeight(charName)+5;
		lpao.renderHearts(g, xHeart, yHeart+2);
//		Draw hp as a number 0-100 for debug
//		TODO delete in final version
		StatesUtils.applyBorder(uniFont, String.valueOf(player.getHp()), xHeart + 10, yHeart, borderColor);
		uniFont.drawString(xHeart + 10, yHeart, String.valueOf(player.getHp()), fontColor);


//		CENTER SIDE OF THE SCREEN
//		Draw level
		int xLevel = ((Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue())-uniFont.getWidth("Level "+this.levelNumber))/2;
		int yLevel = 15; //	30(height hearts) - 0(points) / 2
		StatesUtils.applyBorder(uniFont, "Level "+this.levelNumber, xLevel, yLevel, borderColor);
		uniFont.drawString(xLevel, yLevel, "Level "+this.levelNumber, fontColor);

//		RIGHT SIDE OF THE SCREEN
//		Draw points on the screen
		int xPoints = (Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue())-uniFont.getWidth(String.valueOf(this.pao.getPoints()))-10;
		int yPoints = 15;
		StatesUtils.applyBorder(uniFont, String.valueOf(this.pao.getPoints()), xPoints, yPoints, borderColor);
		uniFont.drawString(xPoints, yPoints, String.valueOf(this.pao.getPoints()), fontColor);

		uniFont.destroy();

	}
	
	/**
	 * Check if the game is to be paused
	 * @param in the Input object
	 * @return true if the game has to enter in the paused state
	 */
	protected abstract boolean isPaused(Input in);

	/**
	 * Check if the user wants to go down
	 * @param in the Input object
	 * @return true if the player wants to go down
	 */
	protected abstract boolean goDown(Input in);

	/**
	 * Check if the user wants to go up
	 * @param in the Input object
	 * @return true if the player wants to go up
	 */
	protected abstract boolean goUp(Input in);

	/**
	 * Check if the user wants to go right
	 * @param in the Input object
	 * @return true if the player wants to go right
	 */
	protected abstract boolean goRight(Input in);

	/**
	 * Check if the user wants to go left
	 * @param in the Input object
	 * @return true if the player wants to go left
	 */
	protected abstract boolean goLeft(Input in);

	/**
	 * Check if the user wants to attack
	 * @param in the Input object
	 * @return true if the player wants to attack
	 */
	protected abstract boolean attack(Input in);

	/**
	 * Check if the user wants to perform the special attack
	 * @param in the Input object
	 * @return true if the player wants to perform the special attack
	 */
	protected abstract boolean special(Input in);

	/**
	 * Enter in gameover state and eventually reset the game
	 * @param gs game
	 */
	protected abstract void reset(StateBasedGame gs);

	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) {
		if(isPaused(gc.getInput()) && !(mission.completed())){
			Pause.setOriginState(getID());
			gs.enterState(GameStates.PAUSE.getState());
		}

		if(dead) {
			reset(gs);
		}

		boolean block = false;

		if(player.getUltra().isActive()){
			block = player.getUltra().isBlocking();
			player.getUltra().iterationStep();
		}

		if(!dead && !mission.completed() && !block) {
		try {

			boolean pressed =false;
			if(goRight(gc.getInput())){
				player.faceRight();
				wallCollision.setKey(Directions.RIGHT);
				if(wallCollision.detectCollision(mapX, mapY, player)) {
					mapX += 1;
					key = Directions.RIGHT;
					pressed =true;
				}
			}
			else if(goLeft(gc.getInput())){
				player.faceLeft();
				wallCollision.setKey(Directions.LEFT);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapX -= 1;
					key = Directions.LEFT;
					pressed =true;
				}
			}
			else if(goDown(gc.getInput())){
				player.faceDown();
				wallCollision.setKey(Directions.DOWN);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapY += 1;
					key = Directions.DOWN;
					pressed =true;
				}
			}
			else if(goUp(gc.getInput())){
				player.faceUp();
				wallCollision.setKey(Directions.UP);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapY -= 1;
					key = Directions.UP;
					pressed =true;
				}
			}
			else if(attack(gc.getInput())) {
				if(key == Directions.UP) {
					player.attackUp();
					if(player.isReadyToAttack()) {
						player.faceStillUp();
					}
				}
				else if(key == Directions.DOWN) {
					player.attackDown();
					if(player.isReadyToAttack()) {
						player.faceStillDown();
					}
				}
				else if(key == Directions.LEFT) {
					player.attackLeft();
					if(player.isReadyToAttack()) {
						player.faceStillLeft();
					}
				}
				else if(key == Directions.RIGHT) {
					player.attackRight();
					if(player.isReadyToAttack()) {
						player.faceStillRight();
					}
				}
			}
			else if(player.isReadyToAttack() && !attack(gc.getInput())) {
				if(key == Directions.UP) {
					player.faceStillUp();
				}
				else if(key == Directions.DOWN) {
					player.faceStillDown();
				}
				else if(key == Directions.LEFT) {
					player.faceStillLeft();
				}
				else if(key == Directions.RIGHT) {
					player.faceStillRight();
				}

			}

			if (!doorEntered){
				if(doorCollision.detectCollision(mapX, mapY, player)) {
					if(doorCollision.getCollidedDoor() != -1 && pressed) {
						for(Edge e:graph.getEdges(this)) {
							if(e.getPortSource(vertex) == doorCollision.getCollidedDoor()) {
								e.opposite(vertex).getBlock().setCharacterSpawn(e.getPortDestination(vertex));
								gs.enterState(e.opposite(vertex).getId());
							}
						}
					}
				}
			}
			if(itemCollision.detectCollision(mapX, mapY, player)) {
				Item collidedItem = itemCollision.getCollidedItem();
				this.scoreManager.decrease(0);
				this.scoreManager.increase(collidedItem.getItemPoints());
				this.scoreManager.setState(States.PointsAccumulator);

				updateEnemies(collidedItem.getSpawns());

				player.accept(collidedItem);
				mission.check(collidedItem);
				item.remove(collidedItem);
			}

			if(trapCollision.detectCollision(mapX, mapY, player)) {
				this.scoreManager.decrease(0);
				for(Item i : trapCollision.getCollisions()) {
					this.scoreManager.increase(i.getItemPoints());
					this.scoreManager.setState(States.PointsAccumulator);

					updateEnemies(i.getSpawns());

					if(!i.isTrap()) {
						player.accept(i);;
					}
					mission.check(i);
					item.remove(i);
				}
			}

			if (enemyCollision.detectCollision(mapX, mapY, player)){
				player.damage(enemyCollision.getAttackDamage());
				scoreManager.decrease(enemyCollision.getAttackDamage());
				scoreManager.increase(0);
				scoreManager.setState(States.LifePointsAccumulator);
			}

			if (attackCollision.detectCollision(mapX, mapY, player)){
				for (Mob target : attackCollision.getEnemy()) {
					target.damage(player.getAttackDamage());
				}
			}

			Set<Enemy> toRemove = new HashSet<>();
			for(Enemy target : enemy) {
				if (target.getHp() <= 0) {
					toRemove.add(target);
					mission.check(target);
					this.scoreManager.decrease(0);

					// in increase() must be passed points associated to enemy kill
					this.scoreManager.increase(target.getMobPoints());
					this.scoreManager.setState(States.PointsAccumulator);
				}
			}
			enemy.removeAll(toRemove);

			// Life player check, there will be handled death
			if (player.getHp() <= 0) this.dead = true;

			//Activate ultra
			if (player.getUltra().isReady() && special(gc.getInput())){
				player.getUltra().activate(this);
			}

			// Player attack update
			player.reloadAttack();

			// Enemy updating
			for(Enemy e : enemy) {
				e.update();
				e.reloadAttack();
			}

			for(Item i:item) {
				i.setLocation((int)(i.getX())+(prevMapX-mapX)*map.getTileWidth(),(int)(i.getY())+(prevMapY-mapY)*map.getTileHeight());
			}

//			Sto camminando non prendo porte
			doorEntered = false;


		} catch (NullAnimationException e1) {
			e1.printStackTrace();
		}

		prevMapX = mapX;
		prevMapY = mapY;
		}
	}

	@Override
	public int getID() {
		return state;
	}

	/**
	 * Place the player in front of door identified as d
	 * @param d identifier of a door
	 */
	public void setCharacterSpawn(int d) {
//		Sto in una porta
		doorEntered = true;

		int x,y,width,height;
		x = Integer.parseInt(map.getMapProperty("charXDoor"+d,"0"));
		y = Integer.parseInt(map.getMapProperty("charYDoor"+d,"0"));
		width = Integer.parseInt(map.getMapProperty("charWidthDoor"+d,"0"));
		height = Integer.parseInt(map.getMapProperty("charHeightDoor"+d,"0"));
		player.setLocation(256,208);
		mapX = x - (int)player.getX()/map.getTileWidth();
		mapY = y - (int)player.getY()/map.getTileHeight();
		if(width > height){
			if(y < map.getHeight()/2){
				mapY += 1;
			}
			else{
				mapY -= 2;
			}
		}
		else{
			if(x < map.getWidth()/2){
				mapX += 1;
			}
			else{
				mapX -= 1;
			}
		}

	}

	public HitboxMaker getHitbox() {
		return hitbox;
	}

	public int getShiftY() {
		return mapY;
	}

	public int getShiftX() {
		return mapX;
	}

	public TiledMap getMap() {
		return map;
	}

	public String getMapName() {
		return mapName;
	}

	public Set<Enemy> getEnemy() {
		return enemy;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(state, mapName);
	}

	@Override
	public boolean equals(Object obj) {
		return this.getClass() == obj.getClass() && state == ((Block) obj).getID() && mapName == ((Block) obj).getMapName();
	}
}
