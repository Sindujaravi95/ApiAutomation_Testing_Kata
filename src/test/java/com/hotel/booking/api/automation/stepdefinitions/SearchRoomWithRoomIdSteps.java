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

public class SearchRoomWithRoomIdSteps {

    private RequestSpecification request;
    private Response response;
    private static final String room_Endpoint = "/api/room";

    @Before(order = 1)
    public void setup() {
        request = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ConfigReader.get("token"))
                .cookie("token", ConfigReader.get("token"));
    }

    @When("user search the room details with {int}")
    public void user_sends_get_request_to_fetch_room_details(int roomId) {
        response = request
                .get(room_Endpoint + "/" + roomId);
    }

    @When("user search the room details with {string} and room id {int}")
    public void user_hits_invalid_room_endpoint(String invalidEndpoint, int roomId) {
        response = request
                .get(invalidEndpoint + roomId);
    }

    // ---------- THEN ----------

    @Then("user successfully received the room details with {int}")
    public void user_successfully_login_with_the_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }

    @Then("user should get the room details response with status {int}")
    public void user_get_the_response_with_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }
}

