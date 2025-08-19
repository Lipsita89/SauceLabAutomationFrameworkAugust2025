Feature: Login Feature


  Scenario: Login with correct credentials
    Given user is on home page
    When  I have entered valid username "standard_user" and password "secret_sauce"
    When  I click on the login button
    Then  The title of the page should be "Swag Labs"

