package main.java;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import managers.CollisionDetectionDoor;
import managers.CollisionDetectionItem;
import managers.CollisionDetectionWall;
import managers.Directions;
import managers.HitboxMaker;
import managers.MapCollisionManager;
import managers.Wall;
import map.Edge;
import map.MapGraph;
import map.Vertex;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import elements.Enemy;
import elements.Mob;
import elements.NullAnimationException;

public class Block extends BasicGameState
{
	private CollisionDetectionWall wallCollision;
	private CollisionDetectionDoor doorCollision;
	private CollisionDetectionItem itemCollision;
	private TiledMap map;
	private Mob player;
	private Set<Enemy> enemy;
	private int state;
	private int mapX, mapY, prevMapX, prevMapY;
	private MapCollisionManager mapCollision;
	private String mapName;
	private MapGraph graph;
	private Vertex vertex;
	private Thread[] enemy_ai;
	
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
	public void initBlock(Mob player,Map<Block,Set<Enemy>> population,MapGraph graph) throws SlickException
	{
		map = new TiledMap("resource/maps/Complete"+mapName+"/"+mapName+".tmx");
		this.vertex = graph.getVertex(this);
		this.graph=graph;
		//mapCollision = new MapCollisionManager(map);
		enemy = population.get(this);
		this.player = player;
		
		enemy_ai = new Thread[enemy.size()];
		int i = 0;
		for(Enemy e: enemy) {
			enemy_ai[i] = new Thread(e);
			i++;
		}
		
		HitboxMaker hitbox = new HitboxMaker(map);
		hitbox.initiateHitbox();
		
		wallCollision = new CollisionDetectionWall(hitbox);
		doorCollision = new CollisionDetectionDoor(hitbox);
		itemCollision = new CollisionDetectionItem(hitbox);
	}
	

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) {
		setCharacterSpawn(1);
		int x, y;

		// Enemies spawn from a set of a random spawn points
		for(Enemy e : enemy)
		{
			x = Integer.parseInt(map.getMapProperty("spawnX1","0"));
			y = Integer.parseInt(map.getMapProperty("spawnY1","0"));
			e.init(x,y);
		}
		
		for(Thread t:enemy_ai) {
			t.start();
		}
		
		prevMapX = mapX;
		prevMapY = mapY;
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
		}
		player.draw();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) {
		try {
			boolean pressed =false;
			if(gc.getInput().isKeyDown(Directions.RIGHT)){
				player.faceRight();
				wallCollision.setKey(Directions.RIGHT);
				if(wallCollision.detectCollision(mapX, mapY, player)) {
					mapX += 1;
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.LEFT)){
				player.faceLeft();
				wallCollision.setKey(Directions.LEFT);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapX -= 1;
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.DOWN)){
				player.faceDown();
				wallCollision.setKey(Directions.DOWN);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapY += 1;
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.UP)){
				player.faceUp();
				wallCollision.setKey(Directions.UP);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapY -= 1;
					pressed =true;
				}
			}
			else{
				player.faceStill();
			}
			if(doorCollision.detectCollision(mapX, mapY, player)) {
				if(doorCollision.getCollidedDoor() != -1 && pressed) {
					System.out.println("Collisione con la porta "+doorCollision.getCollidedDoor());
					for(Edge e:graph.getEdges(this)) {
						if(e.getPortSource(vertex) == doorCollision.getCollidedDoor()) {
							e.opposite(vertex).getBlock().setCharacterSpawn(e.getPortDestination(vertex));
							
							for(Thread t:enemy_ai) {
								t.suspend();
							}
							for(Thread t: e.opposite(vertex).getBlock().getEnemies()) {
								t.resume();
							}
							
							gs.enterState(e.opposite(vertex).getId());
						}
					}
				}
			}
//			int door = mapCollision.doorCollision(map_x, map_y, player);
//			if(door != -1 && pressed) {
//				for(Edge e:graph.getEdges(this)) {
//					if(e.getPortSource(vertex)==door) {
//						e.opposite(vertex).getBlock().setCharacterSpawn(e.getPortDestination(vertex));
//						
//						for(Thread t:enemy_ai) {
//							t.suspend();
//						}
//						for(Thread t: e.opposite(vertex).getBlock().getEnemies()) {
//							t.resume();
//						}
//						
//						gs.enterState(e.opposite(vertex).getId());
//					}
//				}
//			}

		} catch (NullAnimationException e1) {
			e1.printStackTrace();
		}
		
		prevMapX = mapX;
		prevMapY = mapY;
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
	
	public MapCollisionManager getCollisionManager() {
		return mapCollision;
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
	
	public Thread[] getEnemies() {
		return enemy_ai;
	}
}
