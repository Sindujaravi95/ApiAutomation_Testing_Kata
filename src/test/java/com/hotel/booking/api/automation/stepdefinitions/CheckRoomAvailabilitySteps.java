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

public class CheckRoomAvailabilitySteps {

    private RequestSpecification request;
    private Response response;

    private String checkin;
    private String checkout;

    private static final String room_Endpoint = "/api/room";

    @Before(order = 1)
    public void setup() {
        request = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ConfigReader.get("token"))
                .cookie("token", ConfigReader.get("token"));
    }

    // ---------- GIVEN ----------

    @Given("user check the availability of rooms with checkin {string} and checkout {string} dates")
    public void user_checks_available_room__with_dates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;

        if (checkin != null && !checkin.isEmpty()) {
            request = request.queryParam("checkin", checkin);
        }

        if (checkout != null && !checkout.isEmpty()) {
            request = request.queryParam("checkout", checkout);
        }
    }

    // ---------- WHEN ----------

    @When("user search the availability of rooms")
    public void user_sends_get_request_to_fetch_available_rooms() {

        response = request
                .get(room_Endpoint);
    }

    @When("user checks the availability of room with {string}")
    public void user_hits_invalid_room_search_endpoint(String invalidEndpoint) {

        response = request
                .get(invalidEndpoint);
    }

    // ---------- THEN ----------

    @Then("user successfully received the available rooms with {int}")
    public void user_successfully_login_with_the_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }

    @Then("user should get the availability of room response with status {int}")
    public void user_get_the_response_with_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }
}

