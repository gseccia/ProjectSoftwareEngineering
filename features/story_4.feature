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
		Then the map is generated
		And the character is put in it

	Scenario: I complete a level
		Given that I am playing a level
		When I complete all the objectives
		Then a different map is generated
		And the character is put in it
