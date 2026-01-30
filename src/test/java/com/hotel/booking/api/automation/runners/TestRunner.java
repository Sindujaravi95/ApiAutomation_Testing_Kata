package com.hotel.booking.api.automation.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/authentication.feature",
        glue = {
                "com.hotel.booking.api.automation.stepdefinitions",
                "com.hotel.booking.api.automation.hooks"
        },
       // tags="@test",
        plugin = {
                "pretty",
                "html:target/cucumber.html"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {
}




