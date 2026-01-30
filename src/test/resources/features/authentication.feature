@roomBookingApiSuite
Feature: Auth Login API

  @login @valid
  Scenario Outline: User login with valid credentials
    Given user login with given credentials "<username>" and "<password>"
    When user submit the login request "/api/auth/login"
    Then user successfully login with the status <statusCode>
    And user should get authentication token

    Examples:
      | username | password | statusCode |
      | admin    | password | 200        |

  @login @invalid
  Scenario Outline: Login with invalid credentials
    Given user login with given credentials "<username>" and "<password>"
    When user submit the login request "/api/auth/login"
    Then user should get the response with <statusCode> and "<errorMessage>"
    Examples:
      | username | password | statusCode | errorMessage |
      | wrong    | wrong    | 401        | Invalid credentials |
      | admin    | wrong    | 401        | Invalid credentials |
      | wrong    | password | 401        | Invalid credentials |
      | admin    |          | 401        | Invalid credentials |
      |          | password | 401        | Invalid credentials |
      |          |          | 401        | Invalid credentials |

    @login @invalid
  Scenario: Login with invalid endpoint
    Given user login with given credentials "<username>" and "<password>"
    When user submit the login request "/api/login"
    Then user should get the response with 404


  @login @invalid
  Scenario Outline: Login with wrong HTTP method
    Given user login with given credentials "<username>" and "<password>"
    When user submit the GET login request "/api/auth/login"
    Then user should get the response with <statusCode>
    Examples:
      | username | password | statusCode |
      | admin    | password | 405        |

#  @login @invalid
#  Scenario Outline: Login fails with multiple invalid attempts
#    Given user login multiple times with invalid credentials "<username>" and "<password>"
#    When user submit the login request "/api/auth/login"
#    Then user should get the response with <statusCode>
#
#    Examples:
#      | username | password | statusCode |
#      | wrong    | wrong    | 429        |

