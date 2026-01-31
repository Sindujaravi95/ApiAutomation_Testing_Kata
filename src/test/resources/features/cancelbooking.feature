@hotelRoomBookingApiSuite
Feature: Cancel Booking API

  @cancelBooking @valid
  Scenario: Cancel booking with valid booking id
    Given user books the hotel room with given details
      | firstname | lastname | email            | phone       | checkin    | checkout   | depositpaid |
      | testing   | testname | test25@gmail.com | 34943847898 | 2025-09-20 | 2025-09-21 | false       |
    And user submit the room booking request
    And user should receive the room booking id
    When user cancel hotel booking request
    Then user successfully deleted the hotel room booking with status 200

  @cancelBooking @invalid
  Scenario: Cancel booking with invalid endpoint - 404
    Given user books the hotel room with given details
      | firstname | lastname | email            | phone       | checkin    | checkout   | depositpaid |
      | testing   | testing  | test56@gmail.com | 34943847898 | 2025-10-21 | 2025-10-22 | false       |
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
      | firstname | lastname | email          | phone       | checkin    | checkout   | depositpaid |
      | test      | test     | test@gmail.com | 34943847898 | 2025-04-03 | 2025-04-04 | false       |
    And user submit the room booking request
    And user should receive the room booking id
    When user cancel the room booking with invalid request
    Then user should get the cancel booking response with 405
