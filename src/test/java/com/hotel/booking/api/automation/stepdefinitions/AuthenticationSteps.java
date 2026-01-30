package com.hotel.booking.api.automation.stepdefinitions;

import com.hotel.booking.api.automation.utils.ConfigReader;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static com.hotel.booking.api.automation.report.ExtentReportManager.logResultTable;
import static io.restassured.RestAssured.given;

public class AuthenticationSteps {

    private RequestSpecification request;
    private Response response;
    public String token = "";


    @Given("user login with given credentials {string} and {string}")
    public RequestSpecification user_login_with_given_credentials(String username, String password) {
        Map<String, Object> requestBody = new HashMap<>();

        if (username != null && !username.isEmpty()) {
            requestBody.put("username", username);
        }
        if (password != null && !password.isEmpty()) {
            requestBody.put("password", password);
        }

        request = given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestBody);
        return request;
    }


    @When("user submit the login request {string}")
    public void user_submit_the_login_request(String authEndpoint) {
        try {
            response = request
                    .post(authEndpoint);
        } catch (NullPointerException e) {
            response = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .post(authEndpoint);
        }
    }

    @When("user submit the GET login request {string}")
    public void user_submit_get_login_request(String authEndpoint) {
        response = request
                .get(authEndpoint);
    }

    @When("user login multiple times with invalid credentials {string} and {string}")
    public void user_attempts_login_multiple_times_with_invalid_credentials(String username,String password) {
        for (int i = 0; i < 5; i++) {
            request=user_login_with_given_credentials(username,password);
            response = request
                    .when()
                    .post("/api/auth/login");
        }
    }

    @Then("user should get authentication token")
    public void response_should_contain_authentication_token() {
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Authentication token is missing");
    }

    @Then("user successfully login with the status {int}")
    public void user_successfully_login_with_the_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        if (expectedStatusCode == actualStatusCode) {
            token = response.jsonPath().get("token");
            ConfigReader.set("token", token);
            System.out.println("Token : " + token);

        } else {
            System.out.print("Login not successful");
            Assert.assertEquals(actualStatusCode, expectedStatusCode);
        }
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }

    @Then("user should get the response with {int} and {string}")
    public void response_status_should_be(int expectedStatusCode, String expectedErrorMessage) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode,expectedStatusCode);
        String actualErrorMessage = "";
        if (actualStatusCode == 200) {
            token = response.jsonPath().get("token");
            ConfigReader.set("token", token);
            System.out.println("Token : " + token);
            actualErrorMessage = "";
            expectedErrorMessage = "";
        } else {
            try {
                actualErrorMessage = response.jsonPath().getString("error");
            } catch (Exception e) {
                actualErrorMessage = "";
            }
        }
        logResultTable(
                expectedStatusCode,
                actualStatusCode,
                expectedErrorMessage,
                actualErrorMessage
        );
    }

    @Then("user should get the response with {int}")
    public void user_get_the_response_with_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = "";
        String expectedErrorMessage = "";
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        logResultTable(expectedStatusCode, actualStatusCode, expectedErrorMessage, actualErrorMessage);
    }




}
