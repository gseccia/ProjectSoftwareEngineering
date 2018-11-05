Feature: the game session finishes
	As a user,
	When the timer of a level expires or the player has no more lives or I quit the game session
	I want the game session end
	
	Scenario: the time to complete a level expires
		Given that there is a fixed time interval to complete a level
		When the time expires
		Then the game session ends

	Scenario: the player loses all his lives
		Given that the player has a limited number of lives
		When it loses all of them
		Then the game session ends

	Scenario: I quit the game session
		Given that I am playing
		When I click the "quit" button
		Then the game session ends
