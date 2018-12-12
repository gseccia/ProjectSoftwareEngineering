package main;

public enum GameStates {
	MENU(0),
	PLAY(1),
	GAMEOVER(4),
	SPLASHSCREEN(5)
//	SCORES(2),
//	SETTINGS(3),
	;
	
	
	private int state;
	private GameStates(int number) { this.state = number; }
	public int getState() { return state; }
}
