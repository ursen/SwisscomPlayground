Feature: Selenium Test Page

#  Tests that go for the whole page

#  This does a simple test if the input fields work
  Scenario: Fill in the test form
    Given I am on the selenium test page using Chrome
    When I fill in the test form with valid details
    Then I should see my input on the page

# This checks if the button does delete all input
  Scenario: Fill in the test form and delete the input via button
    Given I am on the selenium test page using Chrome
    When I fill in the test form with valid details
    And I submit my data with the do nothing button
    Then I should not see my input on the page

# More fine grained tests that go for individual parts of the page

#  This checks if the radio buttons cannot be activated at the same time
  Scenario: Red and blue must not be selected at the same time
    Given I am on the selenium test page using Chrome
    When I select the red check box
    And I select the blue check box
    Then The blue check box is selected and the red checkbox is not selected

#  This checks if the multi select list correctly selects and deselects individual items
  Scenario: Select three continents and deselect one again
    Given I am on the selenium test page using Chrome
    When I select Europe
    And I select Asia
    And I select North America
    And I deselect Asia
    Then Europe must be selected
    Then North America must be selected
    And Asia must not be selected

  #  This checks if the multi select list correctly selects and deselects individual items
  #  but this time via clicks instead of direct selection.
  #  A user will click, a user will not select
  Scenario: Click three continents and click one again
    Given I am on the selenium test page using Chrome
    When I click Europe
    And I click Asia
    And I click North America
    And I click Asia
    Then Europe must be selected
    Then North America must be selected
    And Asia must not be selected