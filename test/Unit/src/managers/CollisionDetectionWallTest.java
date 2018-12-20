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
import managers.CollisionDetectionStrategy;
import managers.CollisionDetectionWall;
import managers.HitboxMaker;
import managers.Wall;

public class CollisionDetectionWallTest {

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
		Mockito.when(this.hitbox.getWalls()).thenReturn(l);
		Mockito.when(this.w.intersects(player)).thenReturn(true);
		Mockito.when(this.w.intersects(armando)).thenReturn(false);
		Mockito.when(this.player.getX()).thenReturn(0f);
		Mockito.when(this.player.getY()).thenReturn(0f);
		Mockito.when(this.armando.getX()).thenReturn(0f);
		Mockito.when(this.armando.getY()).thenReturn(0f);
		
	}
	
	@Test
	public void CollisionDetectionWallConstructorTest() {
		assertNotNull(new CollisionDetectionWall(hitbox));
	}
	
	@Test
	public void setKeyTest() {
		CollisionDetectionWall cdw = Mockito.mock(CollisionDetectionWall.class);
		assertEquals(0, cdw.getKey());
	}
	
	@Test
	public void getKeyTest() {
		CollisionDetectionWall cdw = Mockito.mock(CollisionDetectionWall.class);
		Mockito.when(cdw.getKey()).thenReturn(1);
		assertEquals(1, cdw.getKey());
	}
	
	@Test
	public void detectCollisionTestTrue() {
		CollisionDetectionWall cdw = new CollisionDetectionWall(this.hitbox);
		assertEquals(false, cdw.detectCollision(0, 0, player));
	}
	
	@Test
	public void detectCollisionTestFalse() {
		CollisionDetectionWall cdw = new CollisionDetectionWall(this.hitbox);
		assertEquals(true, cdw.detectCollision(0, 0, armando));
	}
}
