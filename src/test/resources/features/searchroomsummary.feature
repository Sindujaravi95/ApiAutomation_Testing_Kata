@hotelRoomBookingApiSuite
Feature: Get room summary by room id

  @roomSummary @valid
  Scenario Outline: Search room summary with valid roomid
    Given user search room summary with room id "<roomId>"
    When user search the room summary
    Then user successfully received the room summary with <statusCode>

    Examples:
      | roomId | statusCode |
      | 1       | 200        |


  @roomSummary @invalid
  Scenario Outline: Search room summary with invalid room id
    Given user search room summary with room id "<roomId>"
    When user search the room summary
    Then user should get the room summary response with <statusCode> and "<errorMessage>"

    Examples:
      | roomId |  statusCode | errorMessage |
      |        |  400        | Room ID is required|


  @roomSummary @invalid
  Scenario: Search room summary with invalid endpoint
    Given user search room summary with room id "<roomId>"
    When user search the room summary with "/api/search"
    Then user should get the room summary response with 404
