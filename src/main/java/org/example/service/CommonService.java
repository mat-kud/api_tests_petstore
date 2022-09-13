package org.example.service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.example.log.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class CommonService {
    private static final String BASE_URI = "https://petstore.swagger.io/v2/";

    private final Function<String, String> prepareUri = uri -> String.format("%s%s", BASE_URI, uri);

    protected RequestSpecification requestSpecification;

    public CommonService() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        this.requestSpecification = RestAssured.given();//.auth().oauth2("8d3edc50fd5dbb75c78aa0e6b003827314f21f4aa8f03facd79465c96ce44c55");
        setCommonParams();
    }

    protected void setCommonParams() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("api_key", "special-key");
        requestSpecification.headers(headers);
    }

    protected Response getRequest(String uri) {
        Log.info("Sending Get request to the URI " + uri);
        Response response =  requestSpecification.expect().when().get(prepareUri.apply(uri));
        Log.info("Response status code is " + response.statusCode());
        Log.info("Response body is " + response.asPrettyString());
        return response;
    }

    protected Response postRequest(String uri, Object body) {
        Log.info("Sending Post request to the URI " + uri);
        Response response = requestSpecification.body(body).expect().when().post(prepareUri.apply(uri));
        Log.info("Response status code is " + response.statusCode());
        Log.info("Response body is " + response.asPrettyString());
        return response;
    }

    protected void deleteRequest(String uri) {
        Log.info("Sending Delete request to the URI " + uri);
        Response response = requestSpecification.expect().when().delete(prepareUri.apply(uri));
        Log.info("Response status code is " + response.statusCode());
        Log.info("Response body is " + response.asPrettyString());
    }
}
