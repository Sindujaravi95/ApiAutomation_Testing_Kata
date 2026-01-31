package com.hotel.booking.api.automation.stepdefinitions;


import com.hotel.booking.api.automation.utils.CommonUtils;
import com.hotel.booking.api.automation.utils.ConfigReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static com.hotel.booking.api.automation.report.ExtentReportManager.logResultTable;
import static io.restassured.RestAssured.given;

public class EndToEndFlowSteps {

    private RequestSpecification request;
    private Response response;

    Map<String, Object> createPayload = new HashMap<>();
    Map<String, Object> updatePayload = new HashMap<>();

    private int bookingId;
    private final int roomId = 1;
    String booking_Endpoint = "/api/booking";
    String room_Endpoint = "/api/room";

    // ---------------- SETUP ----------------

    @Before(order = 1)
    public void setup() {
        request = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ConfigReader.get("token"))
                .cookie("token", ConfigReader.get("token"));
    }

    // ---------------- CREATE BOOKING ----------------

    @Given("user book the hotel room with below details")
    public void create_booking(DataTable dataTable) {

        Map<String, String> data = dataTable.asMaps().get(0);

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", data.get("checkin"));
        bookingDates.put("checkout", data.get("checkout"));

        createPayload.put("roomid", CommonUtils.generateRandomRoomId());
        createPayload.put("firstname", (data.get("firstname")));
        createPayload.put("lastname", (data.get("lastname")));
        createPayload.put("depositpaid", data.get("depositpaid"));
        createPayload.put("bookingdates", bookingDates);
        createPayload.put("email", data.get("email"));
        createPayload.put("phone", data.get("phone"));

        request = request
                .body(createPayload);
    }

    @When("user submit the hotel room booking request")
    public void submit_booking_request() {
        response = request
                .when()
                .post(booking_Endpoint);
    }

    @Then("user should get the hotel room booking id")
    public void verify_response_code() {
        response.then().log().all();
        bookingId = response.jsonPath().getInt("bookingid");
        Assert.assertTrue(bookingId > 0, "Booking ID was not generated");
    }


    // ---------------- GET BOOKING ----------------

    @When("user search the booking details by room id")
    public void get_booking_by_roomid() {
        response = request
                .queryParam("roomid", roomId)
                .get(room_Endpoint);
    }

    // ---------------- GET ROOM BY ID ----------------

    @When("user search the room details by room id")
    public void get_room_by_id() {
        response = request.get(room_Endpoint + "/" + roomId);
    }

    // ---------------- GET ROOMS BY DATE ----------------

    @When("user checks the available rooms with checkin and checkout dates")
    public void get_rooms_by_dates() {
        response = request
                .queryParam("checkin", "2025-07-17")
                .queryParam("checkout", "2025-07-18")
                .get(room_Endpoint);
    }

    // ---------------- GET ALL ROOMS ----------------

    @When("user search for all the list of rooms")
    public void get_all_rooms() {
        response = request
                .get(room_Endpoint);
    }

    // ---------------- GET REPORT ----------------

    @When("user search for the room booking report")
    public void get_report() {
        response = request
                .get(booking_Endpoint);
    }

    // ---------------- UPDATE BOOKING ----------------

    @When("user update the hotel room with below details")
    public void update_booking(DataTable dataTable) {

        Map<String, String> data = dataTable.asMaps().get(0);

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", data.get("newCheckin"));
        bookingDates.put("checkout", data.get("newCheckout"));

        updatePayload.put("roomid", CommonUtils.generateRandomRoomId());
        updatePayload.put("firstname", data.get("newFirst"));
        updatePayload.put("lastname", data.get("newLast"));
        updatePayload.put("depositpaid", data.get("depositpaid"));
        updatePayload.put("bookingdates", bookingDates);
        updatePayload.put("email", data.get("newEmail"));
        updatePayload.put("phone", data.get("newPhone"));
    }

    @When("user update the booking request with booking id")
    public void update_booking_request() {
        String updateEndPoint = booking_Endpoint + "/" + bookingId;
        response = request
                .body(updatePayload)
                .when()
                .put(updateEndPoint);
    }

    // ---------------- DELETE BOOKING ----------------

    @When("user cancel the hotel booking request with booking id")
    public void delete_booking() {
        response = request
                .when()
                .delete(booking_Endpoint + "/" + bookingId);
    }

    @Then("user successfully received the response with status code {int}")
    public void response_status_should_be(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);

    }
}

