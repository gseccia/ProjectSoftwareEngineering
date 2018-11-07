Feature: character attacks 
	As a user,
	I want to attack the enemies
	In order to beat them
	Attack mode:
	-heavy
	-light
	
	Scenario: the attack used is light
		Given that the character has two types of attack
		When it practises a light attack
		Then the enemy suffers a low damage
	
	Scenario: the attack used is heavy
		Given that the character has two types of attack
		When it practises a heavy attack
		Then the enemy suffers a high damage
		
	Scenario: the light attack is quick
		Given the character performs a light attack
		Then the animation to complete it is quick

	Scenario: the heavy attack is slow
		Given the character performs a heavy attack
		Then the animation to complete it is slow