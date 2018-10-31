Feature: moving character
	As a player,
	in order to complete the level,
	I want move my character and interact with the objects

	Scenario: I want to move my character
		Given that my character is on the map
		When I click an arrow key
		Then the character moves in the arrow direction

	Scenario: I want my character to interact with an object
		Given that my character is near an object
		When I click space
		Then the character to execute the action associated to that object
