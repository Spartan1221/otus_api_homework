package store.post;

import dto.StoreErrorResponse;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.PostStoreOrderApi;

import static org.assertj.core.api.Assertions.assertThat;

public class PostStoreOrderNegativeTests {

    @Test
    @DisplayName("Если вызвать запрос без тела, то в ответе вернется статус 400")
    public void createOrder400Test() {
        PostStoreOrderApi postStoreOrderApi = new PostStoreOrderApi();
        ValidatableResponse response = postStoreOrderApi.postError()
                .statusCode(400);

        StoreErrorResponse responseBody = response.extract().body().as(StoreErrorResponse.class);

        StoreErrorResponse expectedResponseBody = StoreErrorResponse.builder()
                .code(1)
                .type("error")
                .message("No data")
                .build();
        assertThat(responseBody)
                .as("тело не соответствует ожиданиям")
                .isEqualTo(expectedResponseBody);
    }
}
