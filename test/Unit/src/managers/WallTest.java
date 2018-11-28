package Unit.src.managers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Rectangle;

import managers.Wall;

public class WallTest {

	@Test
	public void intersectsTest() {
		Wall w = new Wall(10,10,10,10);
		Rectangle r=new Rectangle(10,10,10,10);
		assertTrue(w.intersects(r));
		r=new Rectangle(21,21,10,10);
		assertFalse(w.intersects(r));
		r=new Rectangle(0,10,10,10);
		assertFalse(w.intersects(r));
		r=new Rectangle(10,0,10,10);
		assertFalse(w.intersects(r));
		r=new Rectangle(20,10,10,10);
		assertFalse(w.intersects(r));
		r=new Rectangle(10,20,10,10);
		assertFalse(w.intersects(r));
	}

}
