Feature: lives loss
	As a user,
	When an enemy hits me or I interact with some objects
	I lose a part of the HP

	Scenario: recognition of life level
		Given that the game has been opened
		When the user is playing
		Then the character must provide an hp icon
		And the user can recognise how much life it is losing or gaining
		
	Scenario: HP loss / gained quantities
		Given that the game has been opened and an hp icon is provided
		When the user is playing
		Then he must be able to discern hp quantities his character is losing or gaining
		And he can react properly
		
	Scenario: HP fully loss
		Given that the game has been opened
		When the user is playing
		Then he must recognise if all HP of his character has been lost
		
	Scenario: HP fully recovered
		Given that the game has been opened
		When the user is playing
		Then he must recognise if all HP of his character has been recovered
