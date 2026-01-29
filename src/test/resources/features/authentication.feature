@roomBookingApiSuite
Feature: Auth Login API

  @login @valid
  Scenario Outline: User login with valid credentials
    Given user login with valid credentials "<username>" and "<password>"
    When user submit the login request "/api/auth/login"
    Then user successfully login with the status <statusCode>

    Examples:
      | username | password | statusCode |
      | admin    | password | 200        |