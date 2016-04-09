Feature: Functionality of the game board such as click and swipe

  Scenario: Click on the available tile will move the tile
    Given I open the puzzle app
    When I click on the available tile
    Then the tile will move into position


  Scenario: Drag on the available tile will move vertically
    Given I open the puzzle app
    When I drag on the available tile set
    Then the available tiles will move vertically


  Scenario: Drag on the available tile will move horizontally
    Given I open the puzzle app
    When I drag on the available tile set
    Then the available tiles will move horizontally


  Scenario: Click on the available tile and rotate device
    Given I open the puzzle app
    When I click on the available tile
    And I rotate the device
    Then the tile order will remain the same


  Scenario: Click on the available tile and rotate device
    Given I open the puzzle app
    When I click on the available tile
    And I rotate the device
    And I rotate the device
    Then the tile order will remain the same


  Scenario: Click on the empty space
    Given I open the puzzle app
    When I click on the empty
    Then the tile order will remain the same