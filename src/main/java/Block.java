package main.java;

import java.util.*;

import managers.*;
import map.Edge;
import map.MapGraph;
import map.Vertex;
import missions.Mission;

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
	private CollisionDetectionMobAttacksPlayer mobsCollision;
	private TiledMap map;
	private Mob player;
	private Set<Enemy> enemy;
	private Set<Item> item;
	private int state;
	private int mapX, mapY, prevMapX, prevMapY;
	private boolean paused;
	private HitboxMaker hitbox;
	private String mapName;
	private MapGraph graph;
	private Vertex vertex;
	private Mission mission;
	private int key = Directions.DOWN;
	
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
	public void initBlock(Mob player,Map<Block,Set<Enemy>> population,Map<Block,Set<Item>> items,MapGraph graph, Mission missionGenerated) throws SlickException
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
		mobsCollision = new CollisionDetectionMobAttacksPlayer(hitbox);
	}
	

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) {
		setCharacterSpawn(1);
		int x, y, n;
		paused = false;
		
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
	}

	@Override
	public void update(GameContainer gc, StateBasedGame gs, int delta) {
		if(gc.getInput().isKeyDown(Input.KEY_P)) paused = !paused;
		
		if(!paused) {
		try {
			boolean pressed =false;
			if(gc.getInput().isKeyDown(Directions.RIGHT)){
				player.faceRight();
				wallCollision.setKey(Directions.RIGHT);
				if(wallCollision.detectCollision(mapX, mapY, player)) {
					mapX += 1;
					key = Directions.RIGHT;
					player.setDirection(Directions.RIGHT);
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.LEFT)){
				player.faceLeft();
				wallCollision.setKey(Directions.LEFT);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapX -= 1;
					key = Directions.LEFT;
					player.setDirection(Directions.LEFT);
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.DOWN)){
				player.faceDown();
				wallCollision.setKey(Directions.DOWN);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapY += 1;
					key = Directions.DOWN;
					player.setDirection(Directions.DOWN);
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.UP)){
				player.faceUp();
				wallCollision.setKey(Directions.UP);
				if(wallCollision.detectCollision(mapX, mapY, player)){
					mapY -= 1;
					key = Directions.UP;
					player.setDirection(Directions.UP);
					pressed =true;
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
				System.out.println("Stai prendendo una "+itemCollision.getItem());
				//item.remove(itemCollision.getItem());
			}
			if (mobsCollision.detectCollision(mapX, mapY, player)){
				player.damage(mobsCollision.getAttackDamage());
			}
			
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
