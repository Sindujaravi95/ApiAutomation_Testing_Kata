@hotelRoomBookingApiSuite
Feature: List Of room API

  @searchListOfRooms @valid
  Scenario: Search list of rooms with valid request
    When user search for the list of rooms
    Then user successfully received the list of rooms with status 200

  @searchListOfRooms @invalid
  Scenario: Search list of rooms with invalid endpoint
    When user search for the list of rooms with "/api/roomsList"
    Then user should get the list of rooms response with status 404