package com.hotel.booking.api.automation.stepdefinitions;


import com.hotel.booking.api.automation.utils.ConfigReader;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import static com.hotel.booking.api.automation.report.ExtentReportManager.logResultTable;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class SearchBookingDetailsWithRoomIdSteps {

    private RequestSpecification request;
    private Response response;
    private static final String booking_Endpoint = "/api/booking";
    String actualErrorMessage = "";

    @Before(order = 1)
    public void setup() {
        request = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ConfigReader.get("token"))
                .cookie("token", ConfigReader.get("token"));
    }

    // ---------- GIVEN ----------

    @Given("user search booking details with room id {string}")
    public void user_prepares_get_booking_request_with_roomid(String roomId) {
        request = request
                .queryParam("roomid", roomId);
    }
    // ---------- WHEN ----------

    @When("user search the booking details")
    public void user_search_booking_details() {

        response = request
                .get(booking_Endpoint);
    }

    @When("user search the booking details with {string}")
    public void user_search_booking_details_invalid_endpoint(String invalidEndpoint) {
        response = request
                .get(invalidEndpoint);
    }

    @When("user search with the invalid booking details request")
    public void user_search_booking_details_invalid_request() {

        response = request
                .when()
                .body("")
                .post(booking_Endpoint);
    }

    @Then("user successfully received the booking details with {int}")
    public void user_successfully_login_with_the_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }

    @Then("user should get the booking details response with {int} and {string}")
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

    @Then("user should get the booking details response with {int}")
    public void user_get_the_response_with_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }
}

