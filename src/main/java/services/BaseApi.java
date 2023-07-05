package services;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseApi {

    public static final String BASE_URL = System.getProperty("baseUrl", "https://petstore.swagger.io/v2");


    @Step("Вызов сервиса")
    public ValidatableResponse post(RequestSpecification spec, Object body, String path) {
        return given(spec)
                .log().all()
                .body(body)
                .when()
                .post(path)
                .then()
                .log().all();
    }

    @Step("Вызов сервиса")
    public ValidatableResponse delete(RequestSpecification spec, String path) {
        return given(spec)
                .log().all()
                .when()
                .delete(path)
                .then()
                .log().all();
    }

    @Step("Вызов сервиса")
    public ValidatableResponse postError(RequestSpecification spec, String path) {
        return given(spec)
                .log().all()
                .when()
                .post(path)
                .then()
                .log().all();
    }

    @Step("Вызов сервиса")
    public ValidatableResponse getRequest(RequestSpecification spec, String path) {
        return given(spec)
                .log().all()
                .when()
                .get(path)
                .then()
                .log().all();
    }
}
