package main.gamestates;

public enum GameStates {
	MENU(0),
	SCORES(1),
	SETTINGS(2),
	GAMEOVER(3),
	SPLASHSCREEN(4),
	PAUSE(5),
	CHAR_SELECTION(6),
	SKILL_SELECTION(7),
	STARTING_POINT(8)
	;
	
	
	private int state;
	private GameStates(int number) { this.state = number; }
	public int getState() { return state; }
}
