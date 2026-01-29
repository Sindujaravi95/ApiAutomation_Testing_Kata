package com.hotel.booking.api.automation.stepdefinitions;

import com.hotel.booking.api.automation.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthenticationSteps {

    private RequestSpecification request;
    private Response response;
    public String token="";

    @Given("user hits the login endpoint {string}")
    public void user_hits_the_login_endpoint(String authEndpoint) {
        response = request.post(authEndpoint);
    }
    @Given("user login with valid credentials {string} and {string}")
    public void user_login_with_valid_credentials_and(String username, String password) {
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
    }
    @When("user submit the login request {string}")
    public void user_submit_the_login_request(String authEndpoint) {
        response = request
                .post(authEndpoint);
    }
    @Then("user successfully login with the status {int}")
    public void user_successfully_login_with_the_status(int expectedStatusCode) {
        response.then().log().all();
        int actualStatusCode=response.getStatusCode();
        if(expectedStatusCode==actualStatusCode) {
            token = response.jsonPath().get("token");
            ConfigReader.set("token", token);
            System.out.println("Token : " + token);

        }else{
            System.out.print("Login not successful");
            Assert.assertEquals(actualStatusCode,expectedStatusCode);
        }
    }

}
