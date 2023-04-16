package services;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GetStoreOrderIdApi extends BaseApi {

    private static final String BASE_PATH = "/store";
    private String path = "/order/{orderId}";
    private RequestSpecification spec;

    public GetStoreOrderIdApi() {
        this.spec = given()
                .baseUri(BASE_URL)
                .basePath(BASE_PATH)
                .contentType(ContentType.JSON);
    }
    public ValidatableResponse get(String orderId) {
        return getRequest(spec, path.replace("{orderId}", orderId));
    }

}
