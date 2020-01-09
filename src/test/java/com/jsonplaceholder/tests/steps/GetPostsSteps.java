package com.jsonplaceholder.tests.steps;

import com.jsonplaceholder.tests.common.CommonMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

/**
 * Created by @Boki on Jan, 2020
 */
public class GetPostsSteps {

    private Response response;

    public void getPostsRequest(){
        response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/posts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public int countAllPosts(){
        int numberOfPosts = CommonMethods.countJsonObjectsInResponse(response);
        return numberOfPosts;
    }

    public Response getResponse() {
        return response;
    }
}
