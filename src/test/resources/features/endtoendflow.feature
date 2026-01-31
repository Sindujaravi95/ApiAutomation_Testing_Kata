@hotelRoomBookingEndToEndFlow
Feature: Test end to end flow for Booking, Room and Report APIs

  Scenario Outline: Hotel Room Booking End to End Flow

    Given user book the hotel room with below details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   | depositpaid   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> | <depositpaid> |
    When user submit the hotel room booking request
    Then user successfully received the response with status code 201
    And user should get the hotel room booking id
    When user search the booking details by room id
    Then user successfully received the response with status code 200
    When user update the hotel room with below details
      | newFirst | newLast | newEmail          | newPhone    | newCheckin | newCheckout | newDepositPaid |
      | Sindu    | Ravi    | updated@gmail.com | 87955879704 | 2025-01-01 | 2025-01-02  | true           |
    And user update the booking request with booking id
    Then user successfully received the response with status code 200
    When user cancel the hotel booking request with booking id
    Then user successfully received the response with status code 200
    Examples:
      | firstname | lastname | email                 | phone       | checkin    | checkout   | depositpaid |
      | Userend   | endtest  | user678.end@gmail.com | 46645895464 | 2025-12-29 | 2025-12-30 | false       |
