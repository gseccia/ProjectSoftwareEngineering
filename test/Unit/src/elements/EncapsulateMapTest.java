package Unit.src.elements;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.newdawn.slick.tiled.TiledMap;

import elements.EncapsulateMap;
import managers.Wall;

public class EncapsulateMapTest {

	@Mock TiledMap map;
	@Mock List<Wall> doors;
	@Mock Wall door;
	
	private EncapsulateMap encMap;
	
	
	@Before
	public void setUp() throws Exception {
		map = Mockito.mock(TiledMap.class);
		door = Mockito.mock(Wall.class);
		doors = (List<Wall>) Arrays.asList(door);
		
		Mockito.when(door.getX()).thenReturn(0f);
		Mockito.when(door.getY()).thenReturn(0f);
		Mockito.when(map.getLayerIndex("Mask")).thenReturn(0);
		Mockito.when(map.getTileWidth()).thenReturn(10);
		Mockito.when(map.getTileHeight()).thenReturn(10);
		Mockito.when(map.getWidth()).thenReturn(10);
		Mockito.when(map.getHeight()).thenReturn(10);
		
		Mockito.when(map.getTileId(5, 5, 0)).thenReturn(3);
		Mockito.when(map.getTileId(0, 5, 0)).thenReturn(0);
		
		
		
		encMap = new EncapsulateMap(map,doors);
	}
	
	// Test blocked tile
	
	@Test
	public void blockedTileTest() {
		assertTrue(encMap.blocked(null, 5, 5));
	}
	
	@Test
	public void blockedFreeTileTest() {
		assertFalse(encMap.blocked(null, 0, 5));
	}
	
	@Test
	public void blockedDoorTest() {
		assertFalse(encMap.blocked(null, 0, 0));
	}


}
