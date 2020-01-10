package com.jsonplaceholder.tests.steps;

import com.jsonplaceholder.tests.models.PutUpdatePostRequestAndResponseModel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

/**
 * Created by @Boki on Jan, 2020
 */
public class PutUpdatePostSteps {
    private Response response;

    public void callUpdatePostByPostId(int resourcePostId, PutUpdatePostRequestAndResponseModel bodyPayload){
        response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .body(bodyPayload)
                .put("/posts" + "/" + resourcePostId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public void callUpdateNonexistentResource(String resourcePostId, PutUpdatePostRequestAndResponseModel bodyPayload){
        response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .body(bodyPayload)
                .put("/posts" + "/" + resourcePostId)
                .then()
//                .statusCode(HttpStatus.SC_NOT_FOUND)
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public PutUpdatePostRequestAndResponseModel SerializePutUpdateResponse(){
        PutUpdatePostRequestAndResponseModel putRespModel = response
                .then()
                .extract()
                .as(PutUpdatePostRequestAndResponseModel.class);
        return putRespModel;
    }

    public Response getResponse() {
        return response;
    }
}
