Feature: interacting with objects
	As a player,
	in order to complete the level,
	I want to interact with the objects

	Scenario: I want my character to interact with an object
		Given that my character is near an object
		When I click space
		Then the character to execute the action associated to that object
