package Unit.src.managers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.tiled.TiledMap;

import elements.Item;
import elements.Mob;
import elements.Wall;
import managers.strategy.CollisionDetectionItem;
import managers.strategy.CollisionDetectionWall;
import managers.strategy.HitboxMaker;

public class CollisionDetectionItemTest {

	@Mock private HitboxMaker hitbox;
	@Mock private Mob player, armando;
	@Mock private Item w;
	
	@Before
	public void setUp() {
		this.hitbox = Mockito.mock(HitboxMaker.class);
		this.player = Mockito.mock(Mob.class);
		this.armando = Mockito.mock(Mob.class);
		this.w = Mockito.mock(Item.class);
		Mockito.when(this.hitbox.getMap()).thenReturn(Mockito.mock(TiledMap.class));
		List<Item> l = new ArrayList<>();
		l.add(w);
		Mockito.when(this.hitbox.getItems()).thenReturn(l);
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
	public void getItemIDTest() {
		CollisionDetectionItem cdw = Mockito.mock(CollisionDetectionItem.class);
		assertNull(cdw.getItemID());
	}
	
	@Test
	public void getCollidedItemTest() {
		CollisionDetectionItem cdw = Mockito.mock(CollisionDetectionItem.class);
		assertNull(cdw.getCollidedItem());
	}
	
	@Test
	public void detectCollisionTestTrue() {
		CollisionDetectionItem cdw = new CollisionDetectionItem(this.hitbox);
		assertEquals(true, cdw.detectCollision(0, 0, player));
	}
	
	@Test
	public void detectCollisionTestFalse() {
		CollisionDetectionItem cdw = new CollisionDetectionItem(this.hitbox);
		assertEquals(false, cdw.detectCollision(0, 0, armando));
	}

}
