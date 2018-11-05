Feature: main menu
	As a gamer,
	in order to use this game,
	I need to interface with a main menu.
	Main menu is composed by:
	- the game title
	- the play button
	- the change character button
	- the scoreboard button
	- the exit button

	Scenario: I open the game
		When the game is opened
		Then the main menu is displayed

	Scenario: the game session finishes
		Given that I am playing
		When the game session finished
		Then the main menu is displayed

	
