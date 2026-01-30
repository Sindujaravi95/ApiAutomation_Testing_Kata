package com.hotel.booking.api.automation.hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.hotel.booking.api.automation.report.ExtentReportManager;
import com.hotel.booking.api.automation.report.ExtentTestManager;
import com.hotel.booking.api.automation.utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class Hooks {

    static ExtentReports extent = ExtentReportManager.getReport();
    public static ExtentTest test;

    @Before(order=0)
    public void beforeScenario(Scenario scenario) {
        RestAssured.baseURI = ConfigReader.get("base.url");
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 30000)
                        .setParam("http.socket.timeout", 30000));

        Map<String, String> loginPayload = new HashMap<>();
        loginPayload.put("username", ConfigReader.get("username"));
        loginPayload.put("password", ConfigReader.get("password"));

        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/api/auth/login");

        if(response.getStatusCode()==200){
            String token=response.jsonPath().get("token");
            ConfigReader.set("token",token);
            System.out.println("Token : "+token);
        }

        test = extent.createTest(scenario.getName());
        System.out.println("Starting Scenario: " + scenario.getName());
        ExtentTestManager.setTest(test);


    }


    @After
    public void afterScenario(Scenario scenario) throws InterruptedException {
        if (scenario.isFailed()) {
            test.fail("Scenario Failed");
        } else {
            test.pass("Scenario Passed");
        }
        ExtentTestManager.removeTest();
        ExtentReportManager.flushReports();
        Thread.sleep(5000);
    }
}
