# ApiAutomation_Testing_Kata
API Automation Testing Demo for Kata

Overview

This project contains an automated API test suite for a Room Booking application. The tests validate the behaviour of multiple REST APIs under ""positive and negative scenarios"", ensuring correct status codes, authorization handling, and error responses.

The framework is built using ""Java, Cucumber, RestAssured and TestNg", following BDD principles for readability and maintainability.

APIs Covered

The following APIs are automated and tested:

Get List of Available Rooms
Endpoint: GET /api/room

What is tested:

Fetch rooms with valid check-in and check-out dates
Invalid date combinations
Missing query parameters
Authorization handling (valid / invalid / missing)
Invalid endpoint
Wrong HTTP method
Status codes validated:

200 OK
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
405 Method Not Allowed
Get Rooms Report
Endpoint: GET /api/report

What is tested:

Successful report retrieval
Authorization validation
Invalid endpoint
Wrong HTTP method
Status codes validated:

200 OK
401 Unauthorized
403 Forbidden
404 Not Found
405 Method Not Allowed
Get Booking Details by Room ID
Endpoint: GET /api/booking?roomid={id}

What is tested:

Fetch booking details for valid room ID
Invalid and missing room ID
Authorization handling
Invalid endpoint
Wrong HTTP method
Status codes validated:

200 OK
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
405 Method Not Allowed
Update Booking
Endpoint: PUT /api/booking/{id}

What is tested:

Update booking with valid payload
Invalid payload structure
Missing booking ID
Non-existing booking ID
Authorization failures
Wrong HTTP method
Server error scenarios
Status codes validated:

200 OK
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
405 Method Not Allowed
500 Internal Server Error
Delete Booking
Endpoint: DELETE /api/booking/{id}

What is tested:

Delete booking with valid booking ID
Missing booking ID
Non-existing booking ID
Authorization failures
Invalid endpoint
Wrong HTTP method
Status codes validated:

200 OK

400 Bad Request

401 Unauthorized

403 Forbidden

404 Not Found

405 Method Not Allowed

Create Booking
Endpoint: `POST /api/booking

What is tested:

Create booking with valid booking ID
Missing room ID
Authorization failures
Invalid endpoint
Wrong HTTP method
Status codes validated:

200 OK
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
405 Method Not Allowed
Room Summary Details
Endpoint: `GET /api/booking?roomid={{roomid}}

What is tested:

Room Summary details with valid room ID
Missing room ID
Invalid room id
Authorization failures
Invalid endpoint
Wrong HTTP method
Status codes validated:

200 OK

400 Bad Request

401 Unauthorized

403 Forbidden

404 Not Found

`405 Method Not Allowed

Room Details using room id
Endpoint: `GET /api/booking?roomid={{roomid}}

What is tested:

Get room details valid room ID
Missing room ID
Invalid room id
Authorization failures
Invalid endpoint
Wrong HTTP method
Status codes validated:

200 OK

400 Bad Request

401 Unauthorized

403 Forbidden

404 Not Found

`405 Method Not Allowed

Check Availability of Room
Endpoint: `GET /api/room?checkin={{checkindate}}&checkout={{checkout}}

What is tested:

Get availability of room details by giving the date
Missing checkin or checkout date
Invalid date
Authorization failures
Invalid endpoint
Wrong HTTP method
Status codes validated:

200 OK

400 Bad Request

401 Unauthorized

403 Forbidden

404 Not Found

`405 Method Not Allowed

Auth admin
Endpoint: `POST /api/auth/login

What is tested:

Generating token for authentication
Missing username or password
Invalid username or password
Authorization failures
Invalid endpoint
Wrong HTTP method
Status codes validated:

200 OK
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
`405 Method Not Allowed
Technologies Used

Technology	Purpose
Java	Programming language
RestAssured	API automation
Cucumber (BDD)	Feature files & step definitions
TestNG	Assertions & test execution
Maven	Build & dependency management
JSON	Request & response payloads
Versions Used

Tool	Version
Java	11+
RestAssured	5.4.0
Cucumber	7.15.0
TestNG	7.9.0
Maven	3.8+
Configuration

Authorization token is managed using ConfigReader
Update token value in config.properties
baseUrl=https://example.com
token=your_auth_token_here
How to Execute Tests

Using Maven

mvn clean test
Run from IDE

Right-click on TestRunner.java

Select Run

Key Highlights

Covers both ""positive and negative scenarios"" BDD-style readable feature files Reusable step definitions Clean authorization handling Easy to extend for new APIs

Author: Sinduja Ravi
