package com.hotel.booking.api.automation.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    public static ExtentReports extent;

    public static ExtentReports getReport() {
        if (extent == null) {
            ExtentSparkReporter spark =
                    new ExtentSparkReporter("test-output/ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
