package main;

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

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import elements.Enemy;
import elements.Item;
import elements.Mob;
import elements.NullAnimationException;

public class Block extends BasicGameState
{
	private CollisionDetectionWall wallCollision;
	private CollisionDetectionDoor doorCollision;
	private CollisionDetectionItem itemCollision;
	private CollisionDetectionEnemyAttacksPlayer enemyCollision;
	private CollisionDetectionPlayerAttacksEnemy attackCollision;
	private TiledMap map;
	private Mob player;
	private Set<Enemy> enemy;
	private Set<Item> item;
	private int state;
	private int mapX, mapY, prevMapX, prevMapY;
	private boolean paused;
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
	
	
	public Block(int state,String mapName)
	{
		this.state=state;
		this.mapName = mapName;
	}
	
	/**
	 *  This function initializes the block
	 * @param player is the user character
	 * @param population map that contains information about enemies in the blocks
	 * @throws SlickException if the map is not loaded correctly
	 */
	public void initBlock(Mob player,Map<Block,Set<Enemy>> population,Map<Block,Set<Item>> items, 
			MapGraph graph, Mission missionGenerated, ScorePointsManager spm) throws SlickException
	{
		map = new TiledMap("resource/maps/Complete"+mapName+"/"+mapName+".tmx");
		this.vertex = graph.getVertex(this);
		this.graph=graph;
		enemy = population.get(this);
		item = items.get(this);
		mission = missionGenerated;
		
		this.player = player;
		
		hitbox = new HitboxMaker(map, new LinkedList<Mob>(enemy));
		hitbox.initiateHitbox();
		hitbox.setItems(new ArrayList<>(item));
		
		wallCollision = new CollisionDetectionWall(hitbox);
		doorCollision = new CollisionDetectionDoor(hitbox);
		itemCollision = new CollisionDetectionItem(hitbox);
		enemyCollision = new CollisionDetectionEnemyAttacksPlayer(hitbox);
		attackCollision = new CollisionDetectionPlayerAttacksEnemy(hitbox);

		// initialize scoremanager and observers
		this.scoreManager = spm;
		pao = new PointsAccumulatorObserver(this.scoreManager);
		lpao = new LifePointsAccumulatorObserver(this.scoreManager);
//				ScoreFileObserver sfo = new ScoreFileObserver(this.scoreManager);
		this.scoreManager.setNamePlayer("Armando");
	}
	

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) {
		setCharacterSpawn(1);
		int x, y, n;
		paused = false;
		dead = false;
		
		// Enemies spawn from a set of a random spawn points
		n = 1;
		for(Enemy e : enemy)
		{
			x = Integer.parseInt(map.getMapProperty("spawnX"+n,"-1"));
			y = Integer.parseInt(map.getMapProperty("spawnY"+n,"-1"));
			if(x==-1 || y==-1) {
				x = Integer.parseInt(map.getMapProperty("spawnX1","-1"));
				y = Integer.parseInt(map.getMapProperty("spawnY1","-1"));
				n = 1;
			}
			n++;
			e.init(x,y);
		}
		
		n = 1;
		for(Item i:item) {
			x = Integer.parseInt(map.getMapProperty("spawnX"+n,"-1"));
			y = Integer.parseInt(map.getMapProperty("spawnY"+n,"-1"));
			if(x==-1 || y==-1) {
				x = Integer.parseInt(map.getMapProperty("spawnX1","25"));
				y = Integer.parseInt(map.getMapProperty("spawnY1","25"));
				n = 1;
			}
			n++;
			i.setLocation((x)*map.getTileWidth(),y*map.getTileHeight());
		}
		
		prevMapX = 0;
		prevMapY = 0;
		
	 }

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) {
		g.scale(1.5f, 1.5f);
		map.render(0,0, mapX,mapY,mapX+50,mapY+50);
		//TESTING ZONE BEGIN
		/*
		for(Rectangle b: mapCollision.getCollidingBlocks())
		{
			g.drawRect(b.getX()-map_x*map.getTileWidth(),b.getY()-map_y*map.getTileHeight(),b.getWidth(),b.getWidth());
		}*/
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
			//g.draw(e.getVision());  //TESTING LINE
		}
		player.draw();
		
		for(Item i: item) 
		{
			i.draw();
		}
		
		if(paused) {
			g.setColor(Color.green);
			g.drawString(mission.toString(), 0, 0);
			g.drawString("PAUSE", player.getX(), player.getY());
		}
		if(dead) {
			g.setColor(Color.red);
			g.drawString("You died!", 
					(Long.valueOf(Math.round(gc.getWidth()*0.3)).intValue()),
					(Long.valueOf(Math.round(gc.getHeight()*0.3)).intValue())
					);
//			try {
//				TimeUnit.SECONDS.sleep(5);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
//			System.exit(0);
		}
		
		// Qua viene stampato il punteggio
		g.setColor(Color.green);
		g.drawString(String.valueOf(this.pao.getPoints()), (Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue())-5*18, 0);
		
		// Stampo i cuoricini <3
		lpao.renderHearts(g, (Long.valueOf(Math.round(gc.getWidth()/1.5)).intValue()));
		
//		int currentHealth = 30;
//		int maxHealth = 100;
//		int x = 400;
//		int y = 30;
//		
//		// Barra della vita
//		g.setColor(Color.pink);
//		g.fillRect(x, y, maxHealth, 15);
//		g.setColor(Color.green);
//		g.fillRect(x, y, currentHealth, 15);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) {
		if(gc.getInput().isKeyPressed(Input.KEY_P)) paused = !paused;
		
		if(!paused && !dead) {
		try {
			boolean pressed =false;
			if(gc.getInput().isKeyDown(Directions.RIGHT)){
				player.faceRight();
				wallCollision.setKey(Directions.RIGHT);
				if(wallCollision.detectCollision(mapX, mapY, player)) {
					mapX += 1;
					key = Directions.RIGHT;
					player.setCurrentDirection(Directions.RIGHT);
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.LEFT)){
				player.faceLeft();
				wallCollision.setKey(Directions.LEFT);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapX -= 1;
					key = Directions.LEFT;
					player.setCurrentDirection(Directions.LEFT);
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.DOWN)){
				player.faceDown();
				wallCollision.setKey(Directions.DOWN);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapY += 1;
					key = Directions.DOWN;
					player.setCurrentDirection(Directions.DOWN);
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.UP)){
				player.faceUp();
				wallCollision.setKey(Directions.UP);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapY -= 1;
					key = Directions.UP;
					player.setCurrentDirection(Directions.UP);
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.KEY_M)) {
				if(key == Directions.UP) {
					player.attackUp();
				}
				else if(key == Directions.DOWN) {
					player.attackDowm();
				}
				else if(key == Directions.LEFT) {
					player.attackLeft();
				}
				else if(key == Directions.RIGHT) {
					player.attackRight();
				}
			}
			else{
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
					//System.out.println("Collisione con la porta "+doorCollision.getCollidedDoor());
					for(Edge e:graph.getEdges(this)) {
						if(e.getPortSource(vertex) == doorCollision.getCollidedDoor()) {
							e.opposite(vertex).getBlock().setCharacterSpawn(e.getPortDestination(vertex));
							
							gs.enterState(e.opposite(vertex).getId());
						}
					}
				}
			}
			if(itemCollision.detectCollision(mapX, mapY, player)) {
				if (itemCollision.getItemID() != "") {
//					System.out.println("Stai prendendo una "+itemCollision.getItemID());
					this.scoreManager.decrease(0);
					this.scoreManager.increase(itemCollision.getCollidedItem().getItemPoints());
					this.scoreManager.setState(States.PointsAccumulator);
					mission.check(itemCollision.getCollidedItem());
					item.remove(itemCollision.getCollidedItem());
				}
			}
			if (enemyCollision.detectCollision(mapX, mapY, player)){
				player.damage(enemyCollision.getAttackDamage());
				System.out.println("Collisione");
//				Usare subject per modificare l'observer
//				Inserire in decrease() i punti di vita da togliere per la collisione con lo zombo
				scoreManager.decrease(enemyCollision.getAttackDamage());
				scoreManager.increase(0);
				scoreManager.setState(States.LifePointsAccumulator);
//				lpao.setHp(-enemyCollision.getAttackDamage());
			}
			if (attackCollision.detectCollision(mapX, mapY, player) && (gc.getInput().isKeyPressed(Directions.KEY_M))){
				System.out.println("Attacco del player");
				for (Mob target : attackCollision.getEnemy()) {
					target.damage(player.getAttackDamage());
					//System.out.println(target + " "+ target.getHp());
					if(target.getHp() <= 0){
						enemy.remove(target);
						mission.check((Enemy)target);
						this.scoreManager.decrease(0);
						// in increase() must be passed points associated to enemy kill
						this.scoreManager.increase(target.getMobPoints());
						this.scoreManager.setState(States.PointsAccumulator);
					}
				}
			}
			// Life player check, there will be handled death
			if (player.getHp() <= 0) this.dead = true;
			
			// Enemy updating
			for(Enemy e:enemy) {
				e.update();
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
}
