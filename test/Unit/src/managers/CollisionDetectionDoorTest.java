package Unit.src.managers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.tiled.TiledMap;

import elements.Mob;
import elements.Wall;
import managers.strategy.CollisionDetectionDoor;
import managers.strategy.HitboxMaker;

public class CollisionDetectionDoorTest {

	@Mock private HitboxMaker hitbox;
	@Mock private Mob player, armando;
	@Mock private Wall w;
	
	@Before
	public void setUp() {
		this.hitbox = Mockito.mock(HitboxMaker.class);
		this.player = Mockito.mock(Mob.class);
		this.armando = Mockito.mock(Mob.class);
		this.w = Mockito.mock(Wall.class);
		Mockito.when(this.hitbox.getMap()).thenReturn(Mockito.mock(TiledMap.class));
		List<Wall> l = new ArrayList<>();
		l.add(w);
		Mockito.when(this.hitbox.getDoors()).thenReturn(l);
		Mockito.when(this.w.intersects(player)).thenReturn(true);
		Mockito.when(this.w.intersects(armando)).thenReturn(false);
		Mockito.when(this.player.getX()).thenReturn(0f);
		Mockito.when(this.player.getY()).thenReturn(0f);
		Mockito.when(this.armando.getX()).thenReturn(0f);
		Mockito.when(this.armando.getY()).thenReturn(0f);
		
	}
	
	@Test
	public void CollisionDetectionDoorConstructorTest() {
		assertNotNull(new CollisionDetectionDoor(hitbox));
	}
	
	@Test
	public void getCollidedDoorTest() {
		CollisionDetectionDoor cdd = Mockito.mock(CollisionDetectionDoor.class);
		Mockito.when(cdd.getCollidedDoor()).thenReturn(1);
		assertEquals(1, cdd.getCollidedDoor());
	}
	
	@Test
	public void detectCollisionTestTrue() {
		CollisionDetectionDoor cdd = new CollisionDetectionDoor(this.hitbox);
		assertEquals(true, cdd.detectCollision(0, 0, player));
	}
	
	@Test
	public void detectCollisionTestFalse() {
		CollisionDetectionDoor cdd = new CollisionDetectionDoor(this.hitbox);
		assertEquals(false, cdd.detectCollision(0, 0, armando));
	}
	

}
