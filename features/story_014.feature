Feature: map blocks
    As a user,
    I want each block to represent a different scenario
    In order to have a multi-environmental map
    - In each block there are some areas where the user cannot walk

    Scenario: block doors
        Given that I am in a block
        Then I want to see from 1 to 3 doors

    Scenario: classroom block
        Given that I am in a classroom block
        Then I want to see some features that make me recognize it as a classroom

    Scenario: parking block
        Given that I am in a parking block
        Then I want to see some features that make me recognize it as a parking

    Scenario: dining hall block
        Given that I am in a dining hall block
        Then I want to see some features that make me recognize it as a dining hall

    Scenario: library block
        Given that I am in a library block
        Then I want to see some features that make me recognize it as a library

    Scenario: hallway block
        Given that I am in a hallway block
        Then I want to see some features that make me recognize it as a hallway

    Scenario: hall block
        Given that I am in a hall block
        Then I want to see some features that make me recognize it as a hall

    Scenario: square block
        Given that I am in a square block
        Then I want to see some features that make me recognize it as a square

    Scenario: lab block
        Given that I am in a lab block
        Then I want to see some features that make me recognize it as a lab

    Scenario: toilet block
        Given that I am in a toilet block
        Then I want to see some features that make me recognize it as a toilet