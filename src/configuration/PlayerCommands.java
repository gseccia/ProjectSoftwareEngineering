package configuration;

import managers.Directions;

public class PlayerCommands {
	
	private static PlayerCommands instance = null;
	
	int up;
	int down;
	int left;
	int right;
	int attack1;
	int attack2;
	
	public PlayerCommands() {
		this.up = Directions.UP;
		this.down = Directions.DOWN;
		this.left = Directions.LEFT;
		this.right = Directions.RIGHT;
		this.attack1 = Directions.ATTACK1_M;
		this.attack2 = Directions.ATTACK2_SPACE;
	}
	
	public static PlayerCommands getPlayerCommandsInstance() {
		if (instance == null) {
			instance = new PlayerCommands();
		}
		return instance;
	}
	
	public int getUp() {
		return this.up; 
	}
	
	public int getDown() {
		return this.down;
	}
	
	public int getLeft() {
		return this.left;
	}
	
	public int getRight() {
		return this.right;
	}
	
	public int getAttack1() {
		return this.attack1;
	}
	
	public int getAttack2() {
		return this.attack2;
	}
	
	public void setUp(boolean flag) {
		if(flag)
			this.up = Directions.UP;
		else
			this.up = Directions.UP_ARROW;
	}
	
	public void setDown(boolean flag) {
		if(flag)
			this.down = Directions.DOWN;
		else
			this.down = Directions.DOWN_ARROW;
	}
	
	public void setRight(boolean flag) {
		if(flag)
			this.right = Directions.RIGHT;
		else
			this.right = Directions.RIGHT_ARROW;
	}
	
	public void setLeft(boolean flag) {
		if(flag)
			this.left = Directions.LEFT;
		else
			this.left = Directions.LEFT_ARROW;
	}
	
	public void setAttack1(boolean flag) {
		if(flag)
			this.attack1 = Directions.ATTACK1_M;
		else
			this.attack1 = Directions.ATTACK1_Z;
	}
	
	public void setAttack2(boolean flag) {
		if(flag)
			this.attack2 = Directions.ATTACK2_SPACE;
		else
			this.attack2 = Directions.ATTACK2_X;
	}
}
