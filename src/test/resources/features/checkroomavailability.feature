@hotelRoomBookingApiSuite
Feature: Get available rooms by checkin and checkout dates

  @checkRoomAvailabilityByDates @valid
  Scenario Outline: Check available rooms with valid dates
    Given user check the availability of rooms with checkin "<checkin>" and checkout "<checkout>" dates
    When user search the availability of rooms
    Then user successfully received the available rooms with <statusCode>
    Examples:
      | checkin    | checkout   | statusCode |
      | 2025-07-17 | 2025-07-18 | 200        |

  @checkRoomAvailabilityByDates
  Scenario: Check available rooms with invalid endpoint
    Given user check the availability of rooms with checkin "2025-07-17" and checkout "2025-07-18" dates
    When user checks the availability of room with "api/rooms"
    Then user should get the availability of room response with status 404