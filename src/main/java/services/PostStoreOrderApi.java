package services;

import dto.StoreRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PostStoreOrderApi extends BaseApi {

    private static final String BASE_PATH = "/store";
    private RequestSpecification spec;

    public PostStoreOrderApi() {
        this.spec = given()
                .baseUri(BASE_URL)
                .basePath(BASE_PATH)
                .contentType(ContentType.JSON);
    }

    public ValidatableResponse post(StoreRequest storeRequest) {
        return post(spec, storeRequest, "order");
    }

    public ValidatableResponse postError() {
        return postError(spec, "order");
    }
}
