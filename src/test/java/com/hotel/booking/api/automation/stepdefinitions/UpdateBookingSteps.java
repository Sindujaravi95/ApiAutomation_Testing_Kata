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

public class UpdateBookingSteps {

    private RequestSpecification request;
    private Response response;

    Map<String, Object> createPayload = new HashMap<>();
    Map<String, Object> updatePayload = new HashMap<>();

    private int bookingId;
    private int actualStatusCode;
    String actualErrorMessage;
    String booking_Endpoint = "/api/booking";

    // ---------- SETUP ----------

    @Before(order = 1)
    public void setup() {
        request = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ConfigReader.get("token"))
                .cookie("token", ConfigReader.get("token"));
    }

    // ---------- CREATE BOOKING (PRE-CONDITION) ----------

    @Given("user books the hotel room with following details")
    public Map<String, Object> user_book_the_hotel_room(DataTable dataTable) {

        for (Map<String, String> data : dataTable.asMaps(String.class, String.class)) {

            Map<String, String> bookingDates = new HashMap<>();
            bookingDates.put("checkin", data.get("checkin"));
            bookingDates.put("checkout", data.get("checkout"));

            createPayload.put("roomid", CommonUtils.generateRandomRoomId());
            createPayload.put("firstname", data.get("firstname"));
            createPayload.put("lastname", data.get("lastname"));
            createPayload.put("depositpaid", false);
            createPayload.put("bookingdates", bookingDates);
            createPayload.put("email", data.get("email"));
            createPayload.put("phone", data.get("phone"));
        }
        return createPayload;
    }

    @Given("user submit the hotel booking request")
    public void send_put_request() {
        response = request
                .body(createPayload)
                .when()
                .post(booking_Endpoint);
    }

    @And("user should receive the hotel room booking id")
    public void booking_id_is_created_successfully() {
        response.then().log().all();
        bookingId = response.jsonPath().getInt("bookingid");
        Assert.assertTrue(bookingId > 0, "Booking ID was not generated");
    }

    // ---------- UPDATE BOOKING PAYLOAD ----------

    @When("user update the hotel room with following details")
    public void user_updates_booking_with_new_details(DataTable dataTable) {

        for (Map<String, String> data : dataTable.asMaps(String.class, String.class)) {

            Map<String, String> bookingDates = new HashMap<>();
            bookingDates.put("checkin", data.get("checkin"));
            bookingDates.put("checkout", data.get("checkout"));

            updatePayload.put("roomid", CommonUtils.generateRandomRoomId());
            updatePayload.put("firstname", data.get("firstname"));
            updatePayload.put("lastname", data.get("lastname"));
            updatePayload.put("depositpaid", false);
            updatePayload.put("bookingdates", bookingDates);
            updatePayload.put("email", data.get("email"));
            updatePayload.put("phone", data.get("phone"));
        }
    }

    @When("user update the booking request")
    public void user_hits_put_booking_endpoint() {
        response = request
                .body(updatePayload)
                .when()
                .put(booking_Endpoint + "/" + bookingId);
    }

    @When("user update the booking request {string}")
    public void user_hits_put_booking_invalid_endpoint(String invalidEndpoint) {
        response = request
                .body(updatePayload)
                .when()
                .put(invalidEndpoint + bookingId);
    }

    @When("user update the booking request with invalid booking id")
    public void user_hits_put_booking_endpoint_with_invalid_id() {
        response = request
                .body(updatePayload)
                .when()
                .put("/api/booking/99999");
    }

    @When("user update the invalid booking request")
    public void user_hits_post_request_instead_of_put() {
        response = request
                .body(updatePayload)
                .when()
                .post("/api/booking/" + bookingId);
    }

    @Then("user successfully update the room details with status {int}")
    public void user_successfully_login_with_the_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }

    @Then("user should get the update booking response with {int} and {string}")
    public void response_status_should_be(int expectedStatusCode, String expectedErrorMessage) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        try {
            actualErrorMessage = response.jsonPath().getString("errors[0]");
        } catch (Exception e) {
            actualErrorMessage = "";
        }
        logResultTable(
                expectedStatusCode,
                actualStatusCode,
                expectedErrorMessage,
                actualErrorMessage
        );
    }

    @Then("user should get the update booking response with {int}")
    public void user_get_the_response_with_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }
}

