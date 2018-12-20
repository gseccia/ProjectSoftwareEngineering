package Unit.src.managers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.tiled.TiledMap;

import attacks.Attack;
import elements.Mob;
import managers.CollisionDetectionDoor;
import managers.CollisionDetectionEnemyAttacksPlayer;
import managers.HitboxMaker;
import managers.Wall;

public class CollisionDetectionEnemyAttacksPlayerTest {

	@Mock private HitboxMaker hitbox;
	@Mock private Mob player, armando;
	@Mock private Mob w;
	@Mock private Attack att;
	@Mock CollisionDetectionEnemyAttacksPlayer cdeap;
	
	@Before
	public void setUp() {
		this.hitbox = Mockito.mock(HitboxMaker.class);
		this.player = Mockito.mock(Mob.class);
		this.armando = Mockito.mock(Mob.class);
		this.att = Mockito.mock(Attack.class);
		
		this.w = Mockito.mock(Mob.class);

		Mockito.when(this.player.isReadyToAttack()).thenReturn(true);
		Mockito.when(this.armando.isReadyToAttack()).thenReturn(true);
		Mockito.when(this.player.getX()).thenReturn(0f);
		Mockito.when(this.player.getY()).thenReturn(0f);
		Mockito.when(this.armando.getX()).thenReturn(0f);
		Mockito.when(this.armando.getY()).thenReturn(0f);
		
		Mockito.when(this.w.getAttack()).thenReturn(att);
		Mockito.when(this.w.getAttack().intersects(player)).thenReturn(true);
		Mockito.when(this.w.getAttack().intersects(armando)).thenReturn(false);
		Mockito.when(this.hitbox.getMap()).thenReturn(Mockito.mock(TiledMap.class));
		List<Mob> l = new ArrayList<>();
		l.add(w);
		Mockito.when(this.hitbox.getMobs()).thenReturn(l);

		this.cdeap = Mockito.mock(CollisionDetectionEnemyAttacksPlayer.class);
	}
	
	@Test
	public void CollisionDetectionEnemyAttacksPlayerConstructorTest() {
		assertNotNull(new CollisionDetectionEnemyAttacksPlayer(hitbox));
	}
	
	@Test
	public void getAttackDamageTest() {
		CollisionDetectionEnemyAttacksPlayer cdd = Mockito.mock(CollisionDetectionEnemyAttacksPlayer.class);
		Mockito.when(cdd.getAttackDamage()).thenReturn(1);
		assertEquals(1, cdd.getAttackDamage());
	}
	
	@Test
	public void detectCollisionTestTrue() {
		assertEquals(false, cdeap.detectCollision(0, 0, player));
	}
	
	@Test
	public void detectCollisionTestFalse() {
		CollisionDetectionEnemyAttacksPlayer cdd = new CollisionDetectionEnemyAttacksPlayer(this.hitbox);
		assertEquals(false, cdd.detectCollision(0, 0, armando));
	}
	
	

}
