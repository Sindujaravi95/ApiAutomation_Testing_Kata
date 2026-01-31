@hotelRoomBookingApiSuite
Feature: Cancel Booking API

  @cancelBooking @valid
  Scenario: Cancel booking with valid booking id
    Given user books the hotel room with given details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | testing | testname | test25@gmail.com | 34943847898 | 2025-09-16 | 2025-09-17 |
    And user submit the room booking request
    And user should receive the room booking id
    When user cancel hotel booking request
    Then user successfully deleted the hotel room booking with status 200

  @cancelBooking @invalid
  Scenario: Cancel booking with invalid endpoint - 404
    Given user books the hotel room with given details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | testing | testing | test56@gmail.com | 34943847898 | 2025-10-17 | 2025-10-18 |
    And user submit the room booking request
    And user should receive the room booking id
    When user cancel hotel booking with "/api/delete"
    Then user should get the cancel booking response with 404


  @cancelBooking @invalid
  Scenario: Cancel booking with non existing booking id - 500
    When user cancel the booking request with invalid booking id
    Then user should get the cancel booking response with 500


  @cancelBooking @invalid
  Scenario: Cancel booking using unsupported method - 405
    Given user books the hotel room with given details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | test | test | test@gmail.com | 34943847898 | 2025-03-30 | 2025-03-31 |
    And user submit the room booking request
    And user should receive the room booking id
    When user cancel the room booking with invalid request
    Then user should get the cancel booking response with 405
