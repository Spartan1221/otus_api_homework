package services;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DeleteStoreOrderApi extends BaseApi {

    private static final String BASE_PATH = "/store";
    private String path = "/order/{orderId}";
    private RequestSpecification spec;

    public DeleteStoreOrderApi() {
        this.spec = given()
                .baseUri(BASE_URL)
                .basePath(BASE_PATH)
                .contentType(ContentType.JSON);
    }

    public void delete(String orderId) {
        delete(spec, path.replace("{orderId}", orderId));
    }
}
