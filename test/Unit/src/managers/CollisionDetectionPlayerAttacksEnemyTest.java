package Unit.src.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.tiled.TiledMap;

import attacks.Attack;
import elements.Mob;
import managers.strategy.CollisionDetectionPlayerAttacksEnemy;
import managers.strategy.HitboxMaker;

public class CollisionDetectionPlayerAttacksEnemyTest {
	
	@Mock private HitboxMaker hitbox;
	@Mock private Mob player, armandomorto;
	@Mock private Attack att;
	@Mock private CollisionDetectionPlayerAttacksEnemy cdpae;
	
	@Before
	public void setUp() {
		this.hitbox = Mockito.mock(HitboxMaker.class);
		this.player = Mockito.mock(Mob.class);
		this.armandomorto = Mockito.mock(Mob.class);
		this.att = Mockito.mock(Attack.class);

		Mockito.when(this.player.isReadyToAttack()).thenReturn(false);
		
		Mockito.when(this.player.getX()).thenReturn(0f);
		Mockito.when(this.player.getY()).thenReturn(0f);
		Mockito.when(this.armandomorto.getX()).thenReturn(0f);
		Mockito.when(this.armandomorto.getY()).thenReturn(0f);
		
//		Mockito.when(this.armandomorto.getHp()).thenReturn(0);
		
		Mockito.when(this.player.getAttack()).thenReturn(att);
		Mockito.when(this.player.getAttack().intersects(armandomorto)).thenReturn(true);
		
//		mock hitbox
		Mockito.when(this.hitbox.getMap()).thenReturn(Mockito.mock(TiledMap.class));
		List<Mob> l = new ArrayList<>();
		l.add(armandomorto);
		Mockito.when(this.hitbox.getMobs()).thenReturn(l);
		this.cdpae = Mockito.mock(CollisionDetectionPlayerAttacksEnemy.class);
//		mock CollisionDetectionPlayerAttacksEnemy
		Mockito.when(this.cdpae.getMobs()).thenReturn(l);
//		Mockito.when(this.cdpae.getMobs().get(0).getHp()).thenReturn(0);
	}
	
	@Test
	public void CollisionDetectionPlayerAttacksEnemyConstructorTest() {
		assertNotNull(new CollisionDetectionPlayerAttacksEnemy(hitbox));
	}
	@Test
	public void getEnemyTest() {
		assertEquals(1, cdpae.getEnemy().size());
	}
	@Test
	public void detectCollisionTestTrue() {
		Mockito.when(this.hitbox.getMobs().get(0).getHp()).thenReturn(50);
		assertEquals(true, cdpae.detectCollision(0, 0, player));
	}
	
	@Test
	public void detectCollisionTestFalse() {
		Mockito.when(this.hitbox.getMobs().get(0).getHp()).thenReturn(0);
		assertEquals(false, cdpae.detectCollision(0, 0, player));
	}
}
