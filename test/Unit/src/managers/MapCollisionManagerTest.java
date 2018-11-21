package Unit.src.managers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import configuration.MobConfiguration;
import configuration.NoSuchElementInConfigurationException;
import elements.Mob;
import elements.NullAnimationException;
import managers.Directions;
import managers.MapCollisionManager;

public class MapCollisionManagerTest {
	
	@Mock private TiledMap map;
	@Mock private MobConfiguration mobconf;
	
	private Mob player;
	private String id ="test";
	
	private MapCollisionManager mapcollision;
	
	@Before
	public void setUp() throws Exception {
		
		this.mobconf = Mockito.mock(MobConfiguration.class);
		try {
            Mockito.when(this.mobconf.getAttack(this.id)).thenReturn(100);
            Mockito.when(this.mobconf.getHeight(this.id)).thenReturn(42);
            Mockito.when(this.mobconf.getWidth(this.id)).thenReturn(17);
            Mockito.when(this.mobconf.getHp(this.id)).thenReturn(100);
            Mockito.when(this.mobconf.getFaceDown(this.id)).thenReturn(new Animation());
            Mockito.when(this.mobconf.getFaceStill(this.id)).thenReturn(new Animation());
            Mockito.when(this.mobconf.getFaceUp(this.id)).thenReturn(new Animation());
            Mockito.when(this.mobconf.getFaceLeft(this.id)).thenReturn(new Animation());
            Mockito.when(this.mobconf.getFaceRight(this.id)).thenReturn(new Animation());
            this.player = Mob.generate(this.mobconf,this.id);
        } catch (NoSuchElementInConfigurationException | SlickException | NullAnimationException e) {
            e.printStackTrace();
        }
		
		this.map = Mockito.mock(TiledMap.class);
		
		Mockito.when(this.map.getTileWidth()).thenReturn(16);
		Mockito.when(this.map.getTileHeight()).thenReturn(16);
		Mockito.when(this.map.getMapProperty("charWallX1","1")).thenReturn("1");
		Mockito.when(this.map.getMapProperty("charWallY1","1")).thenReturn("1");
		Mockito.when(this.map.getLayerIndex("Mask")).thenReturn(0);
		Mockito.when(this.map.getTileId(0,0,0)).thenReturn(1);		
		Mockito.when(this.map.getWidth()).thenReturn(10);
		Mockito.when(this.map.getHeight()).thenReturn(10);
		Mockito.when(this.map.getMapProperty("charXDoor1", "-1")).thenReturn("10");
		Mockito.when(this.map.getMapProperty("charYDoor1", "-1")).thenReturn("10");
		Mockito.when(this.map.getMapProperty("charWidthDoor1", "1")).thenReturn("1");
		Mockito.when(this.map.getMapProperty("charHeightDoor1", "1")).thenReturn("1");
		Mockito.when(this.map.getMapProperty("charXDoor2", "-1")).thenReturn("-1");
		Mockito.when(this.map.getMapProperty("charYDoor2", "-1")).thenReturn("-1");
		Mockito.when(this.map.getMapProperty("charWidthDoor2", "1")).thenReturn("1");
		Mockito.when(this.map.getMapProperty("charHeightDoor2", "1")).thenReturn("1");
		
		
		mapcollision = new MapCollisionManager(map);
	}

	@Test
	public void wallCollisionTest() {
		// Collide
		player.setLocation(-16,0);
		assertEquals(mapcollision.wallCollision(0, 0, player, Directions.RIGHT),false);
		player.setLocation(16,0);
		assertEquals(mapcollision.wallCollision(0, 0, player, Directions.LEFT),false);
		player.setLocation(0,16);
		assertEquals(mapcollision.wallCollision(0, 0, player, Directions.UP),false);
		player.setLocation(0,-16);
		assertEquals(mapcollision.wallCollision(0, 0, player, Directions.DOWN),false);
		
		// Not Collide
		player.setLocation(32,32);
		assertEquals(mapcollision.wallCollision(0, 0, player, Directions.RIGHT),true);
		player.setLocation(32,0);
		assertEquals(mapcollision.wallCollision(0, 0, player, Directions.LEFT),true);
		player.setLocation(0,32);
		assertEquals(mapcollision.wallCollision(0, 0, player, Directions.UP),true);
		player.setLocation(0,32);
		assertEquals(mapcollision.wallCollision(0, 0, player, Directions.DOWN),true);
	}
	
	@Test
	public void getDoorsTest() {
		List<Rectangle> lista = new ArrayList<>();
		lista.add(new Rectangle(160,160,16,16));
		List<Rectangle> doors=mapcollision.getDoors();
		for(int i=0;i<lista.size();i++) {
			assertEquals((int)doors.get(i).getX(),(int)lista.get(i).getX());
			assertEquals((int)doors.get(i).getY(),(int)lista.get(i).getY());
			assertEquals((int)doors.get(i).getWidth(),(int)lista.get(i).getWidth());
			assertEquals((int)doors.get(i).getHeight(),(int)lista.get(i).getHeight());
		}		
	}
	
	@Test
	public void doorNotCollidingTest() {
		player.setLocation(10,10);
		assertEquals(mapcollision.doorCollision(0, 0, player),-1);
	}
	
	@Test
	public void doorCollisionTest() {
		player.setLocation(160,160);
		assertEquals(mapcollision.doorCollision(0, 0, player),1);
	}
	
	

}