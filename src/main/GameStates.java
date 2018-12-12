package main;

public enum GameStates {
	MENU(200),
	PLAY(1),
//	SCORES(202),
//	SETTINGS(203),
	GAMEOVER(204),
	SPLASHSCREEN(205)
	;
	
	
	private int state;
	private GameStates(int number) { this.state = number; }
	public int getState() { return state; }
}
