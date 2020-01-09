package com.jsonplaceholder.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.jsonplaceholder.tests.common.CommonMethods;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

/**
 * Created by @Boki on Jan, 2020
 * Class is abstract to tell Junit to ignore this class as a Test(not to run it)
 */
public class BaseApiTest {

    protected ExtentReports extentReports;
    protected ExtentTest extentTest;

    @BeforeClass
    public void setup() {
//		Needed not to check SSL cert
        RestAssured.useRelaxedHTTPSValidation();
//		GSON over Jackson
        RestAssured.given().config(RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.GSON)));

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        extentReports = CommonMethods.getExtentReportInstance();

//        extentTest = extentReports.createTest("JSONPlaceholder API automated tests");
    }

    @AfterMethod
    public void afterMethod(ITestResult result){
        if (result.getStatus() == ITestResult.SUCCESS){
//          The same as result.getName()
//          result.getMethod().getMethodName();
            extentTest.log(Status.PASS, result.getName());
        } if (result.getStatus() == ITestResult.FAILURE){
            extentTest.log(Status.FAIL, result.getName());
            extentTest.log(Status.FAIL, result.getThrowable());
        }
    }

    @AfterClass
    public void tearDown(){
        extentReports.flush();
    }

}
