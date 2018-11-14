package managers;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public interface CollisionManagerInterface {
	// Il CollisionManager presa una mappa in input deve gestire i seguenti casi di collisione sui layer:
	//		- muro
	// 		- ostacoli
	//		- porte
	//
	// Convenzione usata per i layer:
	// 		- Walli 	i=0...n
	// 		- Obstaclei i=0...n
	// 		- Doori		i=0...n
	
	public void checkCollision(TiledMap m, String mapName, int charX, int charY, GameContainer gc);
	
}
