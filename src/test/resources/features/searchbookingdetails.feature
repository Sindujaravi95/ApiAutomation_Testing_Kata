@hotelRoomBookingApiSuite
Feature: Get booking details by room id

  @bookingDetailsByRoomId @valid
  Scenario Outline: Search booking details with valid roomid
    Given user search booking details with room id "<roomId>"
    When user search the booking details
    Then user successfully received the booking details with <statusCode>

    Examples:
      | roomId | statusCode |
      | 1       | 200        |


  @bookingDetailsByRoomId @invalid
  Scenario Outline: Search booking details with invalid room id
    Given user search booking details with room id "<roomId>"
    When user search the booking details
    Then user should get the booking details response with <statusCode> and "<errorMessage>"

    Examples:
      | roomId |  statusCode | errorMessage |
      |        |  400        | Room ID is required|


  @bookingDetailsByRoomId @invalid
  Scenario: Search booking details with invalid endpoint
    Given user search booking details with room id "<roomId>"
    When user search the booking details with "/api/search"
    Then user should get the booking details response with 404
