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
import managers.CollisionDetectionEnemyAttacksPlayer;
import managers.CollisionDetectionPlayerAttacksEnemy;
import managers.HitboxMaker;

public class CollisionDetectionPlayerAttacksEnemyTest {
	
	@Mock private HitboxMaker hitbox;
	@Mock private Mob player, mob, tmp;
	@Mock private Attack att;
	@Mock private CollisionDetectionPlayerAttacksEnemy cdpae;
	
	@Before
	public void setUp() {
		this.hitbox = Mockito.mock(HitboxMaker.class);
		this.player = Mockito.mock(Mob.class);
		this.mob =  Mockito.mock(Mob.class);
		this.tmp = Mockito.mock(Mob.class);
		this.att = Mockito.mock(Attack.class);
		this.cdpae = Mockito.mock(CollisionDetectionPlayerAttacksEnemy.class);
		
		Mockito.when(this.hitbox.getMap()).thenReturn(Mockito.mock(TiledMap.class));
		List<Mob> mobs = new ArrayList<>();
		mobs.add(tmp);
		Mockito.when(this.hitbox.getMobs()).thenReturn(mobs);
		
		List<Mob> enemy = new LinkedList<>();
		Mockito.when(this.hitbox.getMobs().get(0).getHp() <= 0).thenReturn(true);
		Mockito.when(this.hitbox.getMobs().get(0).getHp() > 0).thenReturn(false);
		mobs.remove(0);
		
		this.hitbox.getMobs().add(player);
		this.hitbox.getMobs().add(mob);
		Mockito.when(this.tmp.getAttack().intersects(this.hitbox.getMobs().get(0))).thenReturn(true);
		Mockito.when(this.tmp.getAttack().intersects(this.hitbox.getMobs().get(1))).thenReturn(false);
		Mockito.when(this.tmp.isReadyToAttack()).thenReturn(true);
		Mockito.when(this.tmp.isReadyToAttack()).thenReturn(true);
		enemy.add(mobs.get(0));
		
		Mockito.when(this.player.getX()).thenReturn(0f);
		Mockito.when(this.player.getY()).thenReturn(0f);
		Mockito.when(this.mob.getX()).thenReturn(0f);
		Mockito.when(this.mob.getY()).thenReturn(0f);
	}
	
	@Test
	public void CollisionDetectionPlayerAttacksEnemyConstructorTest() {
		assertNotNull(new CollisionDetectionPlayerAttacksEnemy(hitbox));
	}
	@Test
	public void getEnemyTest() {
		CollisionDetectionPlayerAttacksEnemy cdpae = Mockito.mock(CollisionDetectionPlayerAttacksEnemy.class);
		List<Mob> tmp = new LinkedList<>();
		Mockito.when(cdpae.getEnemy()).thenReturn(tmp);
		assertEquals(1, cdpae.getEnemy());
	}
	
	@Test
	public void detectCollisionTestTrue() {
		assertEquals(false, cdpae.detectCollision(0, 0, player));
	}
	
	@Test
	public void detectCollisionTestFalse() {
		CollisionDetectionEnemyAttacksPlayer cdd = new CollisionDetectionEnemyAttacksPlayer(this.hitbox);
		assertEquals(false, cdpae.detectCollision(0, 0, mob));
	}
}
