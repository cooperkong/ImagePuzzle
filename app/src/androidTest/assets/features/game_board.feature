Feature: Functionality of the game board such as click and swipe
  Tile Index Matrix
  |0|1|2|3|
  |4|5|6|7|
  |8|9|10|11|
  |12|13|14|15->empty tile|

  Scenario Outline: Click on the various locations
    Given I open the puzzle app
    When I click on the available tile <index>
    And I click on the available tile <index2>
    And I click on the available tile <index3>
    Then empty tile will move to <x> <y>
    Examples:
      | index |index2|index3| x | y |
      | 11    |10    |  8    | 2 | 0 |

  Scenario Outline: Click on the available tile and rotate device
    Given I open the puzzle app
    When I click on the available tile <index>
    And I rotate the device
    Then empty tile will move to <x> <y>
    Examples:
      | index | x | y |
      | 11    | 2 | 3 |
      | 14    | 3 | 2 |


  Scenario Outline: Click on the available tile and rotate device
    Given I open the puzzle app
    When I click on the available tile <index>
    And I rotate the device
    Then empty tile will move to <x> <y>
    And I rotate the device
    Then empty tile will move to <x> <y>
    Examples:
      | index | x | y |
      | 11    | 2 | 3 |
      | 14    | 3 | 2 |


  Scenario Outline: Click on the available tile will move the tile
    Given I open the puzzle app
    When I click on the available tile <index>
    Then empty tile will move to <x> <y>
    Examples:
      | index | x | y |
      | 7    | 1 | 3 |
      | 11    | 2 | 3 |
      | 13    | 3 | 1 |
      | 14    | 3 | 2 |

  Scenario Outline: Drag on the available tile will move horizontally
    Given I open the puzzle app
    When I swipe right on the available tile set <index>
    Then empty tile will move to <x> <y>
    Examples:
      | index | x   | y   |
      |  12   | 3   | 0  |

  Scenario Outline: Drag on the available tile will move vertically
    Given I open the puzzle app
    When I swipe down on the available tile set <index>
    Then empty tile will move to <x> <y>
    Examples:
      | index | x    |  y |
      | 3     | 0    | 3 |
