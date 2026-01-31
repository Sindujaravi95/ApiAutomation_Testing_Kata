package com.hotel.booking.api.automation.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import static com.hotel.booking.api.automation.hooks.Hooks.test;

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

    public static void logResultTable(
            int expectedStatusCode,
            int actualStatusCode,
            String expectedErrorMsg,
            String actualErrorMsg
    ) {

        boolean statusCodeMatch = expectedStatusCode == actualStatusCode;
        boolean errorMatch =
                expectedErrorMsg == null ||
                        expectedErrorMsg.equalsIgnoreCase(actualErrorMsg);

        boolean scenarioPass = statusCodeMatch && errorMatch;
        String scenarioStatus = scenarioPass
                ? "<span style='color:green;font-weight:bold;'>PASS</span>"
                : "<span style='color:red;font-weight:bold;'>FAIL</span>";

        StringBuilder table = new StringBuilder();

        // 🔽 PASS → collapsed | FAIL → expanded
        table.append(scenarioPass ? "<details>" : "<details open>");
        table.append("<summary><b>Scenario Validation</b></summary>");

        table.append("<table border='1' width='100%' style='border-collapse:collapse;'>");
        table.append("<tr style='background-color:#f2f2f2;'>")
                .append("<th>Field</th>")
                .append("<th>Expected</th>")
                .append("<th>Actual</th>")
                .append("<th>Scenario Status</th>")
                .append("</tr>");

        /* ---------------- Status Code Row (always) ---------------- */
        table.append("<tr>")
                .append("<td>Status Code</td>")
                .append("<td>").append(expectedStatusCode).append("</td>")
                .append("<td>").append(actualStatusCode).append("</td>")
                .append("<td rowspan='")
                .append(scenarioPass ? "1" : "2")
                .append("'><b>")
                .append(scenarioStatus)
                .append("</b></td>")
                .append("</tr>");

        /* ---------------- Error Message Row (FAIL only) ---------------- */
        if (!scenarioPass || !expectedErrorMsg.equalsIgnoreCase("")) {
            table.append("<tr>")
                    .append("<td>Error Message</td>")
                    .append("<td>")
                    .append(expectedErrorMsg == null ? "N/A" : expectedErrorMsg)
                    .append("</td>")
                    .append("<td>")
                    .append(actualErrorMsg == null ? "N/A" : actualErrorMsg)
                    .append("</td>")
                    .append("</tr>");
        }

        table.append("</table>");
        table.append("</details>");
        test.info(table.toString());
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
