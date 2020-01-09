package com.jsonplaceholder.tests.steps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

/**
 * Created by @Boki on Jan, 2020
 */
public class GetOnePostSteps {

    private Response response;

    public void getPostByPostId(int resourcePostId){
        response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/posts" + "/" + resourcePostId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public Response getResponse() {
        return response;
    }
}
