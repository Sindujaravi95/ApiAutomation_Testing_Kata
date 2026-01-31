@hotelRoomBookingEndToEndFlow
Feature: Test end to end flow for Booking, Room and Report APIs

#  Background:
#    Given user hits endpoint "api/auth/login"
#    When user creates auth token with username "admin" and password "password"
#    Then user should get the response code 200


  Scenario Outline: Hotel Room Booking End to End Flow

    # ---------- CREATE BOOKING ----------
    Given user book the hotel room with below details
      | firstname   | lastname   | email   | phone   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <checkin> | <checkout> |
    When user submit the hotel room booking request
    Then user successfully received the response with status code 201
    And user should get the hotel room booking id

    # ---------- GET BOOKING BY ROOM ID ----------
    When user search the booking details by room id
    Then user successfully received the response with status code 200

    # ---------- GET ROOM BY ROOM ID ----------
    When user search the room details by room id
    Then user successfully received the response with status code 200

    # ---------- GET ROOMS BY DATE ----------
    When user checks the available rooms with checkin and checkout dates
    Then user successfully received the response with status code 200

    # ---------- GET ALL ROOMS ----------
    When user search for all the list of rooms
    Then user successfully received the response with status code 200

    # ---------- GET REPORT ----------
    When user search for the room booking report
    Then user successfully received the response with status code 200

    # ---------- UPDATE BOOKING ----------
    When user update the hotel room with below details
      | firstname | lastname | email              | phone        | checkin    | checkout   |
      | Userend      | twoend      | update.user@gmail.com | 46546321354  | 2025-11-03 | 2025-11-04 |
    And user update the booking request with booking id
    Then user successfully received the response with status code 200

    # ---------- DELETE BOOKING ----------
    When user cancel the hotel booking request with booking id
    Then user successfully received the response with status code 200


    Examples:
      | firstname | lastname | email               | phone        | checkin    | checkout   |
      | Userend      | endtest     | user.end@gmail.com | 46645895464  | 2025-12-07 | 2025-12-08 |
