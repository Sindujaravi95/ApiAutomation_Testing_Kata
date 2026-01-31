@hotelRoomBookingApiSuite
Feature: Create Booking API

#  Background: Authentication Token
#    Given user login with given credentials "admin" and "password"
#    When user submit the login request
#    Then user successfully login with the status 200
#    And user should receive the authentication token

@createBooking @valid
Scenario Outline: Create room booking with valid details
Given user book the hotel room with following details
| firstname   | lastname   | email   | phone   | checkin   | checkout   |
| <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
When user submit the booking request
Then user successfully book the room with status <statusCode>
And user should receive the booking id

Examples:
| firstname | lastname | email               | phone        | checkin    | checkout   | statusCode |
| Sinduja   | Ravi     | test.test@gmail.com | 23525678345  | 2026-04-15 | 2026-04-16 | 201        |

@createBooking @invalid
Scenario Outline: Create booking with invalid input
Given user book the hotel room with following details
| firstname   | lastname   | email   | phone   | checkin   | checkout   |
| <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
When user submit the booking request
Then user should get the booking response with <statusCode> and "<errorMessage>"

Examples:
| firstname | lastname | email               | phone        | checkin    | checkout   | statusCode | errorMessage                          |
|           | last     | test@gmail.com      | 879558797034 | 2026-01-28 | 2026-01-29 | 400        | Firstname should not be blank        |
| user      |          | test@gmail.com      | 879558797034 | 2026-01-28 | 2026-01-29 | 400        | Lastname should not be blank         |
| se        | last     | test@gmail.com      | 879558797034 | 2026-01-28 | 2026-01-29 | 400        | size must be between 3 and 18        |
| user      | a        | test@gmail.com      | 879558797034 | 2026-01-28 | 2026-01-29 | 400        | size must be between 3 and 30        |
|           |          | test@gmail.com      | 879558797034 | 2026-01-28 | 2026-01-29 | 400        | Firstname should not be blank        |
| Sinduja   | Ravi     | test                | 879558797034 | 2026-01-28 | 2026-01-29 | 400        | must be a well-formed email address  |
| Sinduja   | Ravi     | test@gmail.com      | 8795587970   | 2026-01-28 | 2026-01-29 | 400        | size must be between 11 and 21       |
| Sinduja   | Ravi     | user.last@gmail.com | 879558797034 |            | 2026-01-29 | 400        | must not be null                     |
| Sinduja   | Ravi     | user.last@gmail.com | 879558797034 | 2026-01-28 |            | 400        | must not be null                     |
| Sinduja   | Ravi     | test@gmail.com      | 879558797034 |            |            | 400        | must not be null                     |
| Sinduja   | Ravi     | test@gmail.com      | 879558797034 | 2026-01-32 |  2026-02-02 | 400       | must not be null                     |
| Sinduja   | Ravi     | test@gmail.com      | 879558797034 | 2026-02-28 |  2026-02-29 | 400       | must not be null                     |

@createBooking @invalid
Scenario: Create booking with invalid endpoint
When user submit the booking request "/api/bookings"
Then user should get the booking response with status code 404

@createBooking @invalid
Scenario: Create booking with unsupported HTTP method
When user submit the invalid booking request
Then user should get the booking response with status code 405

@createBooking @invalid
Scenario Outline: Create duplicate booking with same details
Given user book the hotel room with following details
| firstname   | lastname   | email   | phone   | checkin   | checkout   |
| <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
When user submit the booking request
And user book the hotel room with same details again
Then user should get the booking response with status code 409

Examples:
| firstname | lastname | email               | phone        | checkin    | checkout   |
| Sinduja   | Ravi     | test.test@gmail.com | 23525678345  | 2026-01-28 | 2026-01-29 |
