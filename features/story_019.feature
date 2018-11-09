Feature: moving from a block to another
    As the game manager,
    I want the character to correctly interact with walls and doors of the blocks

    Scenario: I walk from a block to another
        Given that I am in a block
        When I reach a door
        Then I want to change the current block
