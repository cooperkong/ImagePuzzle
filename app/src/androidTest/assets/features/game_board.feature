Feature: Functionality of the game board such as click and swipe

  Scenario Outline: Click on the available tile will move the tile
    Given I open the puzzle app
    When I click on the available tile <index>
    Then the tile will move into position
    Examples:
      | index |
      | 11    |
      | 14    |

  Scenario: Drag on the available tile will move vertically
    Given I open the puzzle app
    When I drag on the available tile set
    Then the available tiles will move vertically


  Scenario: Drag on the available tile will move horizontally
    Given I open the puzzle app
    When I drag on the available tile set
    Then the available tiles will move horizontally


  Scenario Outline: Click on the available tile and rotate device
    Given I open the puzzle app
    When I click on the available tile <index>
    And I rotate the device
    Then the tile order will remain the same
    Examples:
      | index |
      | 11    |
      | 14    |


  Scenario Outline: Click on the available tile and rotate device
    Given I open the puzzle app
    When I click on the available tile <index>
    And I rotate the device
    And I rotate the device
    Then the tile order will remain the same
    Examples:
      | index |
      | 11    |
      | 14    |


  Scenario: Click on the empty space
    Given I open the puzzle app
    When I click on the empty
    Then the tile order will remain the same