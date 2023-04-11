package services;

import dto.StoreRequest;
import io.restassured.response.ValidatableResponse;

public class PostStoreOrderApi extends BaseApi {

    public ValidatableResponse post(StoreRequest storeRequest) {
        return post(storeRequest, "order");
    }

    public ValidatableResponse postError() {
        return postError("order");
    }
}
