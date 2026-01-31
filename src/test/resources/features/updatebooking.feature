@hotelRoomBookingApiSuite
Feature: Update Booking API

  @updateBooking @valid
  Scenario Outline: Update booking with valid details
    Given user books the hotel room with following details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    And user submit the hotel booking request
    And user should receive the hotel room booking id
    When user update the hotel room with following details
      | firstname    | lastname    | email        | phone        | checkin      | checkout      |
      | <newFirst>   | <newLast>   | <newEmail>   | <newPhone>   | <newCheckin> | <newCheckout> |
    And user update the booking request
    Then user successfully update the room details with status <statusCode>

    Examples:
      | firstname | lastname | email          | phone        | checkin    | checkout   | newFirst | newLast | newEmail          | newPhone     | newCheckin | newCheckout | statusCode |
      | Sinduja   | Ravi     | test@gmail.com | 87955879703  | 2026-05-05 | 2026-05-06 | Sindu    | Ravi    | updated@gmail.com | 87955879704  | 2026-05-07 | 2026-05-08 | 200        |

  @updateBooking @invalid @test
  Scenario Outline: Update booking with invalid input
    Given user books the hotel room with following details
      | firstname | lastname | email          | phone        | checkin    | checkout   |
      | Sinduja   | Ravi     | test943@gmail.com | 87955879703  | 2026-04-07 | 2026-04-08 |
    And user submit the hotel booking request
    And user should receive the hotel room booking id
    When user update the hotel room with following details
      | firstname | lastname | email   | phone   | checkin   | checkout   |
      | <fname>   | <lname>  | <email> | <phone> | <checkin> | <checkout> |
    And user update the booking request
    Then user should get the update booking response with <statusCode> and "<errorMessage>"

    Examples:
      | fname | lname | email              | phone        | checkin    | checkout   | statusCode | errorMessage                          |
      |       | Ravi  | test@gmail.com     | 87955879703  | 2026-02-03 | 2026-02-04 | 400        | Firstname should not be blank        |
      | Sindu |       | test@gmail.com     | 87955879703  | 2026-02-03 | 2026-02-04 | 400        | Lastname should not be blank         |
      | Si    | Ravi  | test@gmail.com     | 87955879703  | 2026-02-03 | 2026-02-04 | 400        | size must be between 3 and 18        |
      | testing | last  | test             | 87955879703  | 2026-02-03 | 2026-02-04 | 400        | must be a well-formed email address  |
      | Sindu | Ravi  | test@gmail.com     | 87955        | 2026-02-03 | 2026-02-04 | 400        | size must be between 11 and 21       |
      | Sindu | Ravi  | test@gmail.com     | 87955879703  |            | 2026-02-04 | 400        | must not be null                     |
      | user  | last  | test@gmail.com     | 87958879703  | 2026-02-03 |            | 400        | must not be null                     |
      | Sinduja   | Ravi     | test@gmail.com      | 879558797034 | 2026-03-32 |  2026-04-02 | 400       | must not be null                     |
      | Sinduja   | Ravi     | test@gmail.com      | 879558797034 | 2026-04-30 |  2026-04-31 | 400       | must not be null                     |
  @updateBooking @invalid
  Scenario Outline: Update booking with invalid endpoint details
    Given user books the hotel room with following details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    And user submit the hotel booking request
    And user should receive the hotel room booking id
    When user update the hotel room with following details
      | firstname    | lastname    | email        | phone        | checkin      | checkout      |
      | <newFirst>   | <newLast>   | <newEmail>   | <newPhone>   | <newCheckin> | <newCheckout> |
    And user update the booking request "/api/bookings"
    Then user should get the update booking response with <statusCode>

    Examples:
      | firstname | lastname | email          | phone        | checkin    | checkout   | newFirst | newLast | newEmail          | newPhone     | newCheckin | newCheckout | statusCode |
      | Sinduja   | Ravi     | test@gmail.com | 87955879703  | 2026-05-05 | 2026-05-06 | Sindu    | Ravi    | updated@gmail.com | 87955879704  | 2026-05-07 | 2026-05-08 | 404        |


  @updateBooking @invalid
  Scenario Outline: Update booking with non-existing booking id
    Given user update the hotel room with following details
      | firstname    | lastname    | email        | phone        | checkin      | checkout      |
      | <firstname>   | <lastname>   | <email>   | <phone>   | <checkin> | <checkout> |
    And user update the booking request with invalid booking id
    Then user should get the update booking response with 404
    Examples:
      | firstname | lastname | email          | phone        | checkin    | checkout   |
      | Sinduja   | Ravi     | test@gmail.com | 87955879703  | 2026-05-05 | 2026-05-06 |

  @updateBooking @invalid
  Scenario Outline: Update booking using unsupported HTTP method
    Given user books the hotel room with following details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    And user submit the hotel booking request
    And user should receive the hotel room booking id
    When user update the hotel room with following details
      | firstname    | lastname    | email        | phone        | checkin      | checkout      |
      | <newFirst>   | <newLast>   | <newEmail>   | <newPhone>   | <newCheckin> | <newCheckout> |
    And user update the invalid booking request
    Then user should get the update booking response with <statusCode>

    Examples:
      | firstname | lastname | email          | phone        | checkin    | checkout   | newFirst | newLast | newEmail          | newPhone     | newCheckin | newCheckout | statusCode |
      | Sinduja   | Ravi     | test@gmail.com | 87955879703  | 2026-05-05 | 2026-05-06 | Sindu    | Ravi    | updated@gmail.com | 87955879704  | 2026-05-07 | 2026-05-08 | 405        |
