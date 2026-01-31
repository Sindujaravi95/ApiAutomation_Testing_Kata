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

public class CancelBookingSteps {

    private RequestSpecification request;
    private Response response;

    Map<String, Object> requestBody = new HashMap<>();
    private int bookingId;
    int actualStatusCode;
    String booking_Endpoint = "/api/booking";
    String actualErrorMessage;

    // ---------- SETUP ----------

    @Before(order = 1)
    public void setup() {
        request = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ConfigReader.get("token"))
                .cookie("token", ConfigReader.get("token"));
    }

    // ---------- CREATE BOOKING (PRE-CONDITION) ----------

    @Given("user books the hotel room with given details")
    public void user_creates_booking(DataTable dataTable) {

        for (Map<String, String> data : dataTable.asMaps(String.class, String.class)) {

            Map<String, String> bookingDates = new HashMap<>();
            bookingDates.put("checkin", data.get("checkin"));
            bookingDates.put("checkout", data.get("checkout"));

            requestBody.put("roomid", CommonUtils.generateRandomRoomId());
            requestBody.put("firstname", data.get("firstname"));
            requestBody.put("lastname", data.get("lastname"));
            requestBody.put("depositpaid", data.get("depositpaid"));
            requestBody.put("bookingdates", bookingDates);
            requestBody.put("email", data.get("email"));
            requestBody.put("phone", data.get("phone"));
        }
    }

    @Given("user submit the room booking request")
    public void send_put_request() {
        response = request
                .body(requestBody)
                .when()
                .post(booking_Endpoint);
    }

    @And("user should receive the room booking id")
    public void booking_id_is_created_successfully() {
        response.then().log().all();
        bookingId = response.jsonPath().getInt("bookingid");
        Assert.assertTrue(bookingId > 0, "Booking ID not generated");
    }

    // ---------- WHEN ----------

    @When("user cancel hotel booking request")
    public void user_hits_delete_booking_endpoint() {
        response = request
                .when()
                .delete(booking_Endpoint + "/" + bookingId);
    }

    @When("user cancel hotel booking with {string}")
    public void user_hits_invalid_delete_booking_endpoint(String invalidEndpoint) {
        response = request
                .when()
                .delete(invalidEndpoint + bookingId);
    }

    @When("user cancel the booking request with invalid booking id")
    public void user_hits_delete_with_invalid_booking_id() {
        response = request
                .when()
                .delete(booking_Endpoint + "/" + 123);
    }

    @When("user cancel the room booking with invalid request")
    public void user_hits_post_instead_of_delete() {
        response = request
                .when()
                .post(booking_Endpoint + "/" + bookingId);
    }

    // ---------- THEN ----------

    @Then("user successfully deleted the hotel room booking with status {int}")
    public void response_status_code_should_be(int expectedStatusCode) {

        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }

    @Then("user should get the cancel booking response with {int}")
    public void user_get_the_response_with_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }
}

