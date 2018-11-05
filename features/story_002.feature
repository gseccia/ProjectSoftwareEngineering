Feature: changing character
	As a user,
	I want to choose the character to play with before the game session starts.

	Scenario: I want to change character
		Given that I am on the main menu
		When I click on the "change character" button
		Then I should see a list of characters
		And I should see a description for each character

	Scenario: I want to confirm the character choice
		Given that I choose a character from the list
		When I confirm the character selection
		Then the character is changed
		And I go back to the main menu
