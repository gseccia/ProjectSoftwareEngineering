package managers;

import org.newdawn.slick.*;

import elements.Mob;
import elements.NullAnimationException;

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
	
	public void checkCollision(String mapName,int shift_x,int shift_y, Mob player, GameContainer gc) throws NullAnimationException;
	
}
