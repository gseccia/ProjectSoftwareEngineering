Feature: moving the character
  As a user,
  I want to move my character in the four directions

  Scenario: I want to move my character
      Given that my character is on the map
      When I click an arrow key
      Then the character moves in the arrow direction