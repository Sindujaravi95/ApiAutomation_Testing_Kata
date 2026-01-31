@hotelRoomBookingApiSuite
Feature: Get room details by room id API

  @searchRoomById @valid
  Scenario Outline: Search room details with valid room id
    When user search the room details with <roomId>
    Then user successfully received the room details with <statusCode>

    Examples:
      | roomId | statusCode |
      | 1      | 200        |

  @searchRoomById @invalid
  Scenario: Search room details with invalid endpoint
    When user search the room details with "/api/roomDetails/" and room id 1
    Then user should get the room details response with status 404
