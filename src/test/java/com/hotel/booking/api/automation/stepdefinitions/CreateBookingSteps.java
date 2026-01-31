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

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.hotel.booking.api.automation.report.ExtentReportManager.logResultTable;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreateBookingSteps {

    RequestSpecification request;
    Response response;
    Map<String, Object> requestBody = new HashMap<String, Object>();
    String bookingId = "";
    String booking_Endpoint = "/api/booking";
    String actualErrorMessage = "";

    @Before(order = 1)
    public void setup() {
        request = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ConfigReader.get("token"))
                .cookie("token", ConfigReader.get("token"));
    }

    // ---------- GIVEN ----------

    @Given("user book the hotel room with following details")
    public Map<String, Object> user_book_the_hotel_room(DataTable dataTable) {

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
        return requestBody;
    }

    @Given("user submit the invalid booking request")
    public void send_put_request() {
        response = request
                .body(requestBody)
                .when()
                .put(booking_Endpoint);
    }

    @When("user book the hotel room with same details again")
    public void send_multiple_requests() {
        for (int i = 0; i < 10; i++) {
            response = request
                    .body(requestBody)
                    .when()
                    .post("/api/booking");
        }
    }

    @When("user submit the booking request")
    public void send_post_request() {
        response = request
                .body(requestBody)
                .when()
                .post(booking_Endpoint);
    }

    @When("user submit the booking request {string}")
    public void send_post_request(String invalid_Booking_Endpoint) {
        response = request
                .body(requestBody)
                .when()
                .post(invalid_Booking_Endpoint);
    }

    @Then("user successfully book the room with status {int}")
    public void user_successfully_login_with_the_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        if (expectedStatusCode == actualStatusCode) {
            bookingId = response.jsonPath().getString("bookingId");
        } else {
            System.out.print("Login not successful");
            Assert.assertEquals(actualStatusCode, expectedStatusCode);
        }
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }

    @Then("user should receive the booking id")
    public void response_should_contain_authentication_token() {
        response.then().body("bookingid", notNullValue());
    }

    @Then("user should get the booking response with {int} and {string}")
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

    @Then("user should get the booking response with status code {int}")
    public void user_get_the_response_with_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }
}