package com.hotel.booking.api.automation.stepdefinitions;



import com.hotel.booking.api.automation.utils.ConfigReader;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import static com.hotel.booking.api.automation.report.ExtentReportManager.logResultTable;
import static io.restassured.RestAssured.given;

public class SearchBookingReportSteps {

    private RequestSpecification request;
    private Response response;

    private static final String report_Endpoint = "/api/report";

    @Before(order=1)
    public void setup() {
        request = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + ConfigReader.get("token"))
                .cookie("token", ConfigReader.get("token"));
    }



    @When("user search the booking report")
    public void user_hits_endpoint() {
        response = request
                .get(report_Endpoint);

    }

    // ---------- WHEN ----------


    @When("user search the booking report with {string}")
    public void user_search_with_invalid_report_endpoint(String invalidEndpoint) {

        response = request
                .get(invalidEndpoint);
    }

    @When("user search the booking report with invalid request")
    public void user_search_invalid_request_to_fetch_report() {
        response = request
                .post(report_Endpoint);
    }

    // ---------- THEN ---------
    @Then("user successfully received the report with status {int}")
    public void user_successfully_login_with_the_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }

    @Then("user should get the booking report response with status {int}")
    public void user_get_the_response_with_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }
}


