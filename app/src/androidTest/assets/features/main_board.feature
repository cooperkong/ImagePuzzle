Feature: Main game board display

  Scenario: User can open app and see the main game board
    Given I am on home screen
    When I open puzzle app
    Then I see the main game board

  Scenario: The game board is composed with 4x4 sliders
    Given I am on home screen
    When I open puzzle app
    Then I see the 4x4 game board

  Scenario: Rotate device will not crash the app
    Given I am on home screen
    When I open puzzle app
    And I rotate the device
    Then I see the 4x4 game board