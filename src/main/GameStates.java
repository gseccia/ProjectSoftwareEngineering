package main;

public enum GameStates {
	MENU(0),
	SCORES(1),
	SETTINGS(2),
	GAMEOVER(3),
	SPLASHSCREEN(4),
	PAUSE(5),
	STARTING_POINT(6)
	;
	
	
	private int state;
	private GameStates(int number) { this.state = number; }
	public int getState() { return state; }
}
