package main.java;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import managers.Directions;
import managers.MapCollisionManager;
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

import elements.Mob;
import elements.NullAnimationException;

public class Block extends BasicGameState
{
	private TiledMap map;
	private Mob player;
	private Set<Mob> enemy;
	private int state;
	private int map_x, map_y, prev_map_x, prev_map_y;
	private MapCollisionManager mapCollision;
	private String mapName;
	private MapGraph graph;
	private Vertex vertex;
	
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
	public void initBlock(Mob player,Map<Block,Set<Mob>> population,MapGraph graph) throws SlickException
	{
		map = new TiledMap("resource/maps/Complete"+mapName+"/"+mapName+".tmx");
		this.vertex = graph.getVertex(this);
		this.graph=graph;
		mapCollision = new MapCollisionManager(map);
		enemy = population.get(this);
		this.player = player; 
	}
	

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) {
		int x,y;
		x = Integer.parseInt(map.getMapProperty("charXDoor1","0"));
		y = Integer.parseInt(map.getMapProperty("charYDoor1","0"));
		
		System.out.println("INIT");		
		// Enemies spawn from a set of a random spawn points
		for(Mob e : enemy)
		{
//			if (rand.nextInt(3) > 1) {
//				x = Integer.parseInt(map.getMapProperty("spawnX1","0"));
//				y = Integer.parseInt(map.getMapProperty("spawnY1","0"));
//			}
//			else {
//				x = Integer.parseInt(map.getMapProperty("spawnX3","0"));
//				y = Integer.parseInt(map.getMapProperty("spawnY3","0"));
//			}
//			e.setPosition(x*map.getTileWidth() -map_x*map.getTileWidth()/2, y*map.getTileHeight()-map_y*map.getTileWidth()/2);
			e.setPosition(160,80);
		}
		
		//player.setPosition(160,80);
		setCharacterSpawn(1);
		// Shift of the map
		//map_x = (int)player.getX()/map.getTileWidth();
		//map_y = (int)player.getY()/map.getTileHeight();
		
		// Player spawns in front of Door1
		//player.setPosition((x-map_x+1)*map.getTileWidth(),(y-map_y+1)*map.getTileHeight());
		
		prev_map_x = map_x;
		prev_map_y = map_y;
	 }

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) {
		g.scale(1.5f, 1.5f);
		map.render(0,0, map_x,map_y,map_x+50,map_y+50);
		//TESTING ZONE
		/*
		for(Rectangle b: mapCollision.getCollidingBlocks())
		{
			g.drawRect(b.getX()-map_x*map.getTileWidth(),b.getY()-map_y*map.getTileHeight(),b.getWidth(),b.getWidth());
		}*/
		for(Rectangle b: mapCollision.getDoors()) {
			g.setColor(Color.blue);
			g.drawRect(b.getX()-map_x*map.getTileWidth(),b.getY()-map_y*map.getTileHeight(),b.getWidth(),b.getHeight());
		}
		g.setColor(Color.white);

		//TESTING ZONE
		for(Mob e : enemy)
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
				if(mapCollision.wallCollision(map_x, map_y, player, Directions.RIGHT)){
					map_x += 1;
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.LEFT)){
				player.faceLeft();
				if(mapCollision.wallCollision(map_x, map_y, player, Directions.LEFT)){
					map_x -= 1;
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.DOWN)){
				player.faceDown();
				if(mapCollision.wallCollision(map_x, map_y, player, Directions.DOWN)){
					map_y += 1;
					pressed =true;
				}
			}
			else if(gc.getInput().isKeyDown(Directions.UP)){
				player.faceUp();
				if(mapCollision.wallCollision(map_x, map_y, player, Directions.UP)){
					map_y -= 1;
					pressed =true;
				}
				
			}
			else{
				player.faceStill();
			}
			int door = mapCollision.doorCollision(map_x, map_y, player);
			if(door != -1 && pressed) {
				System.out.println("COLLIDE with port "+door);
				for(Edge e:graph.getEdges(this)) {
					System.out.println("SOURCE  "+e.getPortSource(vertex));
					if(e.getPortSource(vertex)==door) {
						System.out.println("COLLIDE with port "+door+" to "+e.getPortDestination(vertex));
						e.opposite(vertex).getBlock().setCharacterSpawn(e.getPortDestination(vertex));
						gs.enterState(e.opposite(vertex).getId());
					}
				}
			}

		} catch (NullAnimationException e1) {
			e1.printStackTrace();
		}
		for(Mob e : enemy)
		{
			try {
				e.faceRight();
			} catch (NullAnimationException e1) {
				e1.printStackTrace();
			}
			e.moveX((prev_map_x-map_x)*map.getTileWidth());
			e.moveY((prev_map_y-map_y)*map.getTileHeight());

			int random_x = new Random().nextInt(2);
			int random_y = new Random().nextInt(2);

			if(mapCollision.wallCollision(map_x,map_y, e, Directions.RIGHT)){
				e.moveX(random_x);
				e.moveY(random_y);
			}

		}
		prev_map_x = map_x;
		prev_map_y = map_y;
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
		player.setPosition(256,208);
		map_x = x - (int)player.getX()/map.getTileWidth();
		map_y = y - (int)player.getY()/map.getTileHeight();
		if(width > height){
			if(y < map.getHeight()/2){
				map_y += 2;
			}
			else{
				map_y -= 2;
			}
		}
		else{
			if(x < map.getWidth()/2){
				map_x += 2;
			}
			else{
				map_x -= 2;
			}
		}
		
	}

}
