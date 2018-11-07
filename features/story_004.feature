Feature: generating the map
	As a user,
	When the game starts,
	I want the map to be generated and the objects placed
	The map generation includes:
	- Putting together some blocks in a way that each block is reachable
	- A list of objectives is defined
	- The missions to complete the objectives are put randomly on the map
	- Other random objects can be placed on the map

	Scenario: the game starts for the first time
		Given that I am on the main menu
		When I click on the "play" button
		Then the game session starts 
		And the map is generated
		And the character is put in it

	Scenario: I complete a level
		Given that I am playing a game session
		When I complete all the objectives of a level
		Then a different map is generated
		And the character is put in it

	Scenario: I walk from a block to another
		Given that I am in a block
		When I reach a door
		Then I want to change the current block
