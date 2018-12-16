package blocks;

import java.util.*;

import managers.*;
import map.Edge;
import map.MapGraph;
import map.Vertex;
import missions.Mission;
import managers.observers.scoreboard.LifePointsAccumulatorObserver;
import managers.observers.scoreboard.PointsAccumulatorObserver;
import managers.observers.scoreboard.ScorePointsManager;
import managers.observers.scoreboard.States;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import elements.Enemy;
import elements.Item;
import elements.Mob;
import elements.Player;
import main.ResourceManager;
import main.gamestates.GameStates;
import main.gamestates.Pause;
import elements.NullAnimationException;

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
	private int key = Directions.DOWN;
	private ScorePointsManager scoreManager;
	private PointsAccumulatorObserver pao;
	private LifePointsAccumulatorObserver lpao;
	private ResourceManager rs;
	private MusicManager mm;
	private boolean levelMusicMustBeStarted;
	private boolean completedMusicMustBeStarted;
	
	protected Block(int state,String mapName)
	{
		this.state = state;
		this.mapName = mapName;
		this.rs = ResourceManager.getInstance();
		this.mm = MusicManager.getInstance(this.rs);
	}
	
	public void resetCompletedLevelMusic() {
//		System.out.println("Started music manager");
//		levelMusicMustBeStarted = true;
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
		pao = new PointsAccumulatorObserver(this.scoreManager);
		lpao = new LifePointsAccumulatorObserver(this.scoreManager);
//				ScoreFileObserver sfo = new ScoreFileObserver(this.scoreManager);
		this.scoreManager.setNamePlayer("Armando");
		
//		Initialize Resource Manager
		resetCompletedLevelMusic();
	}
	

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) {
		setCharacterSpawn(1);
		int x, y;
		dead = false;
		
		// Enemies spawn from a set of a random spawn points
		setEnemiesSpawn(enemy);

		Random r = new Random();
		boolean[][] occupied = hitbox.getOccupiedTiles();
		Wall tempWall = new Wall(0, 0, map.getTileWidth(), map.getTileHeight());
		for(Item i : item) {
			y = r.nextInt(map.getHeight());
			x = r.nextInt(map.getWidth());
			tempWall.setX(x);
			tempWall.setY(y);
			while(occupied[x][y]) {
				y = r.nextInt(map.getHeight());
				x = r.nextInt(map.getWidth());
				tempWall.setX(x);
				tempWall.setY(y);
				for(Item j : item) {
					if(j.intersects(tempWall)) {
						tempWall.setX(x+r.nextInt(10));
						tempWall.setY(y+r.nextInt(10));
					}
//					if(j.getX() == tempWall.getMinX() && j.getY() == tempWall.getMinY()) {
//						tempWall.setX(x+r.nextInt(10));
//						tempWall.setY(y+r.nextInt(10));
//					}
				}
			}
			i.setLocation((x)*map.getTileWidth(),y*map.getTileHeight());
		}
		
		prevMapX = 0;
		prevMapY = 0;
		initFont();
	}


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
//		if (levelMusicMustBeStarted) {
//			if (arg1.getCurrentStateID()==this.getID()) {
//				rs.setState(1);
//				System.out.println("starting block");
//				levelMusicMustBeStarted = false;
//			}
//		}
		
		g.scale(1.5f, 1.5f);
		map.render(0,0, mapX,mapY,mapX+50,mapY+50);
		//TESTING ZONE BEGIN
		
		for(Rectangle b: getHitbox().getWalls())
		{
			g.drawRect(b.getX()-mapX*map.getTileWidth(),b.getY()-mapY*map.getTileHeight(),b.getWidth(),b.getWidth());
		}
		List<Wall> doors = doorCollision.getDoors();
		for(Rectangle b: doors) {
			g.setColor(Color.blue);
			g.drawRect(b.getX()-mapX*map.getTileWidth(),b.getY()-mapY*map.getTileHeight(),b.getWidth(),b.getHeight());
		}
		g.setColor(Color.white);
//		for(Rectangle b: mapCollision.getDoors()) {
//			g.setColor(Color.blue);
//			g.drawRect(b.getX()-map_x*map.getTileWidth(),b.getY()-map_y*map.getTileHeight(),b.getWidth(),b.getHeight());
//		}
//		g.setColor(Color.white);
		//TESTING ZONE END

		for(Enemy e : enemy)
		{
			e.draw();
			//System.out.println(e.getID() + " " + e.getX() + " "+e.getY());
			//g.draw(e.getVision());  //TESTING LINE
		}
		player.draw();
		g.draw(player);

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

		if(dead) {
			arg1.enterState(GameStates.GAMEOVER.getState());
		}


		if(mission.completed()){
			g.setColor(Color.white);
            try {
            	int width = ((Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue()) - uniFont.getWidth("LEVEL COMPLETED!"))/2;
            	int height = ((Long.valueOf(Math.round(gc.getHeight()/1.5)).intValue()))/2;
                g.fillRect(0, 0, gc.getWidth(), gc.getHeight(), new Image(System.getProperty("user.dir") + "/resource/textures/transitions/background.png"), 0, 0);
                
                applyBorder("LEVEL COMPLETED!", width, height-15, new Color(105, 2, 2));
                uniFont.drawString(width, height-15, "LEVEL COMPLETED!", new Color(201, 2, 2));
                
//                g.drawImage(new Image(System.getProperty("user.dir") + "/resource/textures/transitions/toBeCont.png"), player.getX()-85, player.getY()-25);
                width = ((Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue()) - uniFont.getWidth("Press Enter to continue"))/2;
                
                applyBorder("Press Enter to continue", width, height+15, new Color(105, 2, 2));
				uniFont.drawString(width, height+15, "Press Enter to continue", new Color(201, 2, 2));
				
//				bgMusic.stop();
//                if(!endLevel.playing()) endLevel.loop(1.0f, SoundStore.get().getMusicVolume() * 0.3f);
            	if(completedMusicMustBeStarted) {
            		this.rs.setState(2);
            		completedMusicMustBeStarted = false;
            	}
				
            } catch (SlickException e) {
                e.printStackTrace();
            }
            if(gc.getInput().isKeyDown(Input.KEY_ENTER)){
//            	if(endLevel.playing()) endLevel.stop();
            	this.rs.setState(1);
            	levelMusicMustBeStarted = true;
            	generateNextLevel(gc, arg1);
			}

        }

		g.setColor(Color.green);
		g.drawString(String.valueOf(this.pao.getPoints()), (Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue())-5*18, 0);

		lpao.renderHearts(g, (Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue()));
		g.drawString(String.valueOf(player.getHp()), (Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue())-18*7, 30);
		
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

	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) {
		if(isPaused(gc.getInput()) && !(mission.completed())){
			Pause.setOriginState(getID());
			gs.enterState(GameStates.PAUSE.getState());
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
					player.setCurrentDirection(Directions.RIGHT);
					pressed =true;
				}
			}
			else if(goLeft(gc.getInput())){
				player.faceLeft();
				wallCollision.setKey(Directions.LEFT);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapX -= 1;
					key = Directions.LEFT;
					player.setCurrentDirection(Directions.LEFT);
					pressed =true;
				}
			}
			else if(goDown(gc.getInput())){
				player.faceDown();
				wallCollision.setKey(Directions.DOWN);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapY += 1;
					key = Directions.DOWN;
					player.setCurrentDirection(Directions.DOWN);
					pressed =true;
				}
			}
			else if(goUp(gc.getInput())){
				player.faceUp();
				wallCollision.setKey(Directions.UP);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapY -= 1;
					key = Directions.UP;
					player.setCurrentDirection(Directions.UP);
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
			if(itemCollision.detectCollision(mapX, mapY, player)) {
				Item collidedItem = itemCollision.getCollidedItem();
				this.scoreManager.decrease(0);
				this.scoreManager.increase(collidedItem.getItemPoints());

				if (itemCollision.getItemID() == "heart") {
					this.scoreManager.setState(States.LifePointsAccumulator);
				}
				else {
					this.scoreManager.setState(States.PointsAccumulator);
				}

				for(Enemy e : collidedItem.getSpawns()){
					e.setPlayer(player);
					e.setMap(this);
				}
				enemy.addAll(collidedItem.getSpawns());
				setEnemiesSpawn(collidedItem.getSpawns());
				hitbox.updateMobs(new LinkedList<>(collidedItem.getSpawns()));

				collidedItem.accept(player);
				mission.check(collidedItem);
				item.remove(collidedItem);
			}

			if(trapCollision.detectCollision(mapX, mapY, player)) {
				this.scoreManager.decrease(0);
				for(Item i : trapCollision.getCollisions()) {
					this.scoreManager.increase(i.getItemPoints());

					if (i.getID() == "heart") {
						this.scoreManager.setState(States.LifePointsAccumulator);
					} else {
						this.scoreManager.setState(States.PointsAccumulator);
					}

					for(Enemy e : i.getSpawns()){
						e.setPlayer(player);
						e.setMap(this);
					}
					enemy.addAll(i.getSpawns());
					setEnemiesSpawn(i.getSpawns());
					hitbox.updateMobs(new LinkedList<>(i.getSpawns()));

					if(!i.isTrap()) {
						i.accept(player);
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
//				lpao.setHp(-enemyCollision.getAttackDamage());
			}
			if (attackCollision.detectCollision(mapX, mapY, player)  /*&& (gc.getInput().isKeyPressed(Directions.KEY_M))*/){
				for (Mob target : attackCollision.getEnemy()) {
					target.damage(player.getAttackDamage());
					//System.out.println(target + " "+ target.getHp());
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
//				this.rs.setState(-1);
				player.getUltra().activate(this);
			}

			// Player attack update
			player.reloadAttack();
			
			// Enemy updating
			for(Enemy e:enemy) {
				e.update();
				e.reloadAttack();
			}
			
			for(Item i:item) {
				i.setLocation((int)(i.getX())+(prevMapX-mapX)*map.getTileWidth(),(int)(i.getY())+(prevMapY-mapY)*map.getTileHeight());
			}


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
	
	public void setCharacterSpawn(int d) {
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
				mapY -= 1;
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

	//Fonts
	java.awt.Font UIFont1;
	org.newdawn.slick.UnicodeFont uniFont;
	@SuppressWarnings("unchecked")
	public void initFont() {
		try{
			UIFont1 = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
					org.newdawn.slick.util.ResourceLoader.getResourceAsStream(
							System.getProperty("user.dir") + "/resource/font/joystix_monospace.ttf"
							));
			UIFont1 = UIFont1.deriveFont(java.awt.Font.ITALIC, 20.f); //You can change "PLAIN" to "BOLD" or "ITALIC"... and 30.f is the size of your font

			uniFont = new org.newdawn.slick.UnicodeFont(UIFont1);
			uniFont.addAsciiGlyphs();
			uniFont.getEffects().add(new ColorEffect(java.awt.Color.white)); //You can change your color here, but you can also change it in the render{ ... }
			uniFont.addAsciiGlyphs();
			uniFont.loadGlyphs();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void applyBorder(String s, int x, int y, Color c) {    	
		uniFont.drawString(ShiftWest(x, 2), ShiftNorth(y, 2), s, c);
		uniFont.drawString(ShiftWest(x, 2), ShiftSouth(y, 2), s, c);
		uniFont.drawString(ShiftEast(x, 2), ShiftNorth(y, 2), s, c);
		uniFont.drawString(ShiftEast(x, 2), ShiftSouth(y, 2), s, c);
	}

	private int ShiftNorth(int p, int distance) {
		return (p - distance);
	}
	private int ShiftSouth(int p, int distance) {
		return (p + distance);
	}
	private int ShiftEast(int p, int distance) {
		return (p + distance);
	}
	private int ShiftWest(int p, int distance) {
		return (p - distance);
	}
}
