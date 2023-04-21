package store.post;

import dto.StoreErrorResponse;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.GetStoreOrderIdApi;

import static org.assertj.core.api.Assertions.assertThat;

public class GetStoreOrderNegativeTests {

    @Test
    @DisplayName("Если вызвать запрос с id не существующим в бд, то в ответе вернется статус 404 и корректное тело ")
    public void notFoundIdTest() {
        GetStoreOrderIdApi getStoreOrderIdApi = new GetStoreOrderIdApi();
        ValidatableResponse response = getStoreOrderIdApi.get("11")
                .statusCode(404);

        StoreErrorResponse responseBody = response.extract().body().as(StoreErrorResponse.class);

        StoreErrorResponse expectedResponseBody = StoreErrorResponse.builder()
                .code(1)
                .type("error")
                .message("Order not found")
                .build();
        assertThat(responseBody)
                .as("тело не соответствует ожиданиям")
                .isEqualTo(expectedResponseBody);
    }
}
