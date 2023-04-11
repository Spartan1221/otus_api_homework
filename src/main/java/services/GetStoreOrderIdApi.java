package services;

import io.restassured.response.ValidatableResponse;

public class GetStoreOrderIdApi extends BaseApi{

    private String path = "/order/{orderId}";

    public ValidatableResponse get(String orderId) {
        return getRequest(path.replace("{orderId}", orderId));
    }

}
