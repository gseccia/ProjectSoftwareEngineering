Feature: changing character
	As a user,
	I want to choose the character to play with.

	Scenario: I want to change character
		Given that I am on the main menu
		When I click on the "change character" button
		Then I should see a list of characters
		And I should see a description for each character
