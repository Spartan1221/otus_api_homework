package services;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseApi {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String BASE_PATH = "/store";

    protected RequestSpecification spec;

    public BaseApi() {
        this.spec = given()
                .baseUri(BASE_URL)
                .basePath(BASE_PATH)
                .contentType(ContentType.JSON);
    }

    public ValidatableResponse post(Object body, String path) {
        return given(this.spec)
                .log().all()
                .body(body)
                .when()
                .post(path)
                .then()
                .log().all();
    }

    public ValidatableResponse postError(String path) {
        return given(this.spec)
                .log().all()
                .when()
                .post(path)
                .then()
                .log().all();
    }



    public ValidatableResponse getRequest(String path) {
        return given(this.spec)
                .log().all()
                .when()
                .get(path)
                .then()
                .log().all();
    }
}
