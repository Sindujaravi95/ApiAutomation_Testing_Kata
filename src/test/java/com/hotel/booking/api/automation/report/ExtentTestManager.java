package com.hotel.booking.api.automation.report;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    /**
     * Set ExtentTest instance for current thread
     */
    public static void setTest(ExtentTest test) {

        extentTest.set(test);
    }

    /**
     * Get ExtentTest instance for current thread
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    /**
     * Remove ExtentTest instance after test execution
     */
    public static void removeTest() {

        extentTest.remove();
    }
}
