package com.jsonplaceholder.tests.common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.restassured.response.Response;

import java.util.List;

/**
 * Created by @Boki on Jan, 2020
 */
public class CommonMethods {

    public static  int countJsonObjectsInResponse(Response response){
        List<String> jresponse = response.jsonPath().getList("id");
        int numberOfPosts = jresponse.size();
        return numberOfPosts;
    }

    public static ExtentReports getExtentReportInstance(){

        ExtentHtmlReporter htmlReport = new ExtentHtmlReporter("build/reports/extentReports/ExtentReport.html");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReport);

        return extentReports;
    }
}
