Feature: character attacks 
	As a user,
	I want to attack the enemies
	In order to beat them
	
	Scenario: characters have multiple attacks
		Given that I selected any character
		When I make an attack
		Then I can make heavy attacks or light attacks
	
	Scenario: the attack used is light
		Given that the character has two types of attack
		When it practises a light attack
		Then the enemy suffers a low damage
	
	Scenario: the attack used is heavy
		Given that the character has two types of attack
		When it practises a heavy attack
		Then the enemy suffers a high damage
		
	Scenario: the attack risk depends on the attack type
		Given that the character has two types of attack
		When it practises an attack
		Then if the attack is light it is less risky, if heavy riskier
