Feature: Adding the Highest selected price item to the cart

  Background:
    Given I have logged in with valid credentials

  Scenario:Select the highest price item
    Given I am on the HomePage
    When  I Select the highest price item
    And   I add the selected highest price item to the cart
    Then  the item is added in the cart "Sauce Labs Fleece Jacket"
