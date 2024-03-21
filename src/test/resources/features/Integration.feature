Feature: Integration

Background:
    Given I am on the sign in page and decide not to sign in
    Then The main page should be opened

Scenario: Successful login
    Given I am on the main in page
    When I enter my destination and select the first item
    And I set the date range
    And I set the room details
    And I search for properties and select the second option
    And I fill in my personal details
    And I proceed to the next step
    And I enter invalid card details
    Then The error message should be "Card number is invalid"
