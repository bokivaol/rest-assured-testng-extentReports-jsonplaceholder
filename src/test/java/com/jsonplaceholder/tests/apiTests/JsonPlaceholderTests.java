package com.jsonplaceholder.tests.apiTests;

import com.aventstack.extentreports.Status;
import com.jsonplaceholder.tests.BaseApiTest;
import com.jsonplaceholder.tests.models.PutUpdatePostRequestAndResponseModel;
import com.jsonplaceholder.tests.steps.*;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by @Boki on Jan, 2020
 */
public class JsonPlaceholderTests extends BaseApiTest {

    @BeforeMethod
    public void beforeMethod(){
        extentTest = extentReports.createTest("JSONPlaceholder API automated tests");
    }

    @Test
    public void T1_get_callAll100BlogPosts_all100BlogPostsAreReceivedInResponse() {
        SoftAssertions softAssertions = new SoftAssertions();
        GetPostsSteps getPostsSteps = new GetPostsSteps();

//        Run GET request
        getPostsSteps.getPostsRequest();

        extentTest.log(Status.INFO, "GET request is sent and all 100 blog posts are received and stored successfully.");

//        Assertions starting point
        softAssertions.assertThat(getPostsSteps.getResponse().getStatusCode()).as("Status code is 200.")
                .isEqualTo(200);
        softAssertions.assertThat(getPostsSteps.countAllPosts()).as("Number of blog posts").isEqualTo(100);

        softAssertions.assertAll();
    }

    @Test
    public void T1_get_callAll10BlogPostsWrittenByUserId5_all10BlogPostsWrittenByUserId5AreReceivedInResponse() {
        int userId = 5;
        SoftAssertions softAssertions = new SoftAssertions();
        GetPostsByUserIdSteps getPostsByUserIdSteps = new GetPostsByUserIdSteps();

//        Run GET request
        getPostsByUserIdSteps.getPostsByUserId(userId);
        extentTest.log(Status.INFO, "GET request is sent and all blog posts written by UserId=5 are received and stored successfully.");

//        Assertions starting point
        softAssertions.assertThat(getPostsByUserIdSteps.getResponse().getStatusCode()).as("Status code is 200.")
                .isEqualTo(200);
//        Assertion that returned number of blog posts written by UserId=5 is 10
        softAssertions.assertThat(getPostsByUserIdSteps.countAllPosts()).as("Number of blog posts written by user whose userID").isEqualTo(10);

        softAssertions.assertAll();
    }

    @Test
    public void T3_put_idInPostsBodyPayloadIsNumberAsStringNotAsInt_blogPostIsSuccessfullyUpdated() {
        SoftAssertions softAssertions = new SoftAssertions();
        PutUpdatePostSteps putUpdatePostSteps = new PutUpdatePostSteps();

//        Values of the payloadPostId and resourcePostId must be the same, but the data type is different.
        String bodyPayloadPostId = "1";
        int resourcePostId = 1;
        int userId = 1;
        String title = "Little";
        String body = "Wing";

        PutUpdatePostRequestAndResponseModel putUpdatePostRequestPayload = new PutUpdatePostRequestAndResponseModel();
        putUpdatePostRequestPayload.setBody(body);
        putUpdatePostRequestPayload.setTitle(title);
        putUpdatePostRequestPayload.setId(bodyPayloadPostId);
        putUpdatePostRequestPayload.setUserId(userId);
        extentTest.log(Status.INFO, "Request body payload is created.");

//        Run PUT request
        putUpdatePostSteps.callUpdatePostByPostId(resourcePostId, putUpdatePostRequestPayload);
        extentTest.log(Status.INFO, "PUT request is sent. Blog post is successfully updated. Response is received and stored successfully.");

//        Serialized response
        PutUpdatePostRequestAndResponseModel putUpdatePostResponse = putUpdatePostSteps.SerializePutUpdateResponse();

//        Assertions starting point
        softAssertions.assertThat(putUpdatePostSteps.getResponse().getStatusCode()).as("Status code is 200.")
                .isEqualTo(200);
        softAssertions.assertThat(putUpdatePostResponse.getId()).as("Post id")
                .isEqualTo(bodyPayloadPostId);
        softAssertions.assertThat(putUpdatePostResponse.getUserId()).as("User id")
                .isEqualTo(userId);
        softAssertions.assertThat(putUpdatePostResponse.getBody()).as("Body")
                .isEqualTo(body);
        softAssertions.assertThat(putUpdatePostResponse.getTitle()).as("Title")
                .isEqualTo(title);

        softAssertions.assertAll();
    }

    @Test
    public void T4_get_zeroAsBlogPostIdInResourceUri_statusCodeIs404AndEmptyJsonIsReceivedInResponse() {
        SoftAssertions softAssertions = new SoftAssertions();
        GetOnePostSteps getOnePostSteps = new GetOnePostSteps();

        int resourcePostId = 0;

//        Run GET request
        getOnePostSteps.getPostByPostId(resourcePostId);
        extentTest.log(Status.INFO, "GET request with 0 as blogPostId in resource URI is sent. Response is received and stored successfully.");

        Response response = getOnePostSteps.getResponse();

//        Assertions starting point
        softAssertions.assertThat(response.getStatusCode()).as("Status code is 404.")
                .isEqualTo(404);
        softAssertions.assertThat(response.getBody().asString()).as("Body")
                .isEqualTo("{}");

        softAssertions.assertAll();
    }

    @Test
    public void T5_delete_deleteAtOnceAllBlogPostsUnderPostsCollectionResource_receivedAnErrorAndNothingIsDeleted() {
        SoftAssertions softAssertions = new SoftAssertions();
        DeleteAllPostsAtOnceSteps deleteAllPostsAtOnceSteps = new DeleteAllPostsAtOnceSteps();

//        Run DELETE request
        deleteAllPostsAtOnceSteps.deleteAllPostsRequest();
        extentTest.log(Status.INFO, "Delete request is sent. Response is received and stored.");

        Response response = deleteAllPostsAtOnceSteps.getResponse();

//        Assertions starting point
        softAssertions.assertThat(response.getStatusCode()).as("Status code is 404.")
                .isEqualTo(404);
        softAssertions.assertThat(response.getBody().asString()).as("Body")
                .isEqualTo("{}");

        softAssertions.assertAll();
    }

    @Test
    public void T5_put_updateNonexistentResource_resourceNotFound() {
        SoftAssertions softAssertions = new SoftAssertions();
        PutUpdatePostSteps putUpdatePostSteps = new PutUpdatePostSteps();

//        Values of the payloadPostId and resourcePostId must be the same, but the data type is different.
        String bodyPayloadPostId = "1";
        String stringAsResourcePostId = "a";
        int userId = 1;
        String title = "Little";
        String body = "Wing";

        PutUpdatePostRequestAndResponseModel putUpdatePostRequestPayload = new PutUpdatePostRequestAndResponseModel();
        putUpdatePostRequestPayload.setBody(body);
        putUpdatePostRequestPayload.setTitle(title);
        putUpdatePostRequestPayload.setId(bodyPayloadPostId);
        putUpdatePostRequestPayload.setUserId(userId);
        extentTest.log(Status.INFO, "Request body payload is created.");

//        Run PUT request
        putUpdatePostSteps.callUpdateNonexistentResource(stringAsResourcePostId, putUpdatePostRequestPayload);
        if (putUpdatePostSteps.getResponse().getStatusCode() != 404){
            extentTest.log(Status.FAIL, putUpdatePostSteps.getResponse().asString());
        } else {
            extentTest.log(Status.INFO, "PUT request is sent. Blog post is successfully updated. Response is received and stored successfully.");
        }

//        Assertions starting point
        softAssertions.assertThat(putUpdatePostSteps.getResponse().getStatusCode()).as("Status code is 404.")
                .isEqualTo(404);

        softAssertions.assertAll();
    }
}
