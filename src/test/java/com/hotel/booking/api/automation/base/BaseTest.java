package com.hotel.booking.api.automation.base;


import com.hotel.booking.api.automation.utils.ConfigReader;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
    public static RequestSpecification request;

    @Before
    public void setup() {
        RestAssured.baseURI = ConfigReader.get("base.url");
        request = RestAssured.given().contentType(ContentType.JSON);
    }
}