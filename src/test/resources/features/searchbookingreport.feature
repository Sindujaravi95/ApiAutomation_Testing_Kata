@hotelRoomBookingApiSuite
Feature: Get Booking Report API

  @bookingReport
  Scenario: Search hotel room booking report with valid endpoint
    When user search the booking report
    Then user successfully received the report with status 200


  @bookingReport
  Scenario: Search booking report with invalid endpoint
    When user search the booking report with "/api/reports"
    Then user should get the booking report response with status 404

  @bookingReport
  Scenario: Search booking report with wrong HTTP method
    When user search the booking report with invalid request
    Then user should get the booking report response with status 405
