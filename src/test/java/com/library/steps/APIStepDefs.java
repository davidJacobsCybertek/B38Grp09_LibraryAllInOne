package com.library.steps;

import com.library.utility.LibraryAPI_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.List;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class APIStepDefs {
    //Good luck!
    RequestSpecification givenPart = RestAssured.given().log().uri();
    Response response;
    ValidatableResponse thenPart;
    JsonPath jp;

    String expectedID;

   // US01
    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String role) {

        givenPart.header("x-library-token", LibraryAPI_Util.getToken(role));
        givenPart.log().all();
    }

    @And("Accept header is {string}")
    public void acceptHeaderIs(String acceptHeader) {
        givenPart.accept(acceptHeader);
    }

    @When("I send GET request to {string} endpoint")
    public void iSendGETRequestToEndpoint(String endpoint) {
        response = givenPart.when().get(endpoint);
        thenPart = response.then();
        jp = response.jsonPath();
    }

    @Then("status code should be {int}")
    public void statusCodeShouldBe(int expectedStatusCode) {

    //op 1
        assertEquals(expectedStatusCode, response.statusCode());
    //op 2
        thenPart.statusCode(expectedStatusCode);
    }

    @And("Response Content type is {string}")
    public void responseContentTypeIs(String expectedContentType) {

     // op 1
        thenPart.contentType(expectedContentType);
     // op 2
        assertEquals(expectedContentType, response.contentType());
    }

    @And("Each {string} field should not be null")
    public void eachFieldShouldNotBeNull(String path) {

     // op 1
        List<String> allData = jp.getList(path);
        for (String eachData : allData) {
            assertNotNull(eachData);
     // op 2
            thenPart.body(path, everyItem(Matchers.notNullValue()));
        }
    }

    //US02
    @Given("Path param {string} is {string}")
    public void path_param_is(String pathParamKey, String pathParamValue) {
        givenPart.pathParam(pathParamKey, pathParamValue);
        expectedID= pathParamValue;

    }
    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String pathParamKey) {
        Assert.assertEquals(expectedID, response.jsonPath().getString(pathParamKey));

    }
    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> paths) {
        for (String path : paths) {
            thenPart.body(path, notNullValue());
        }
    }





}
