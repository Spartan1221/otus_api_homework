package store.post;

import dto.StoreErrorResponse;
import dto.StoreResponse;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.GetStoreOrderIdApi;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class GetStoreOrderTests {

    @Test
    @DisplayName("Если вызвать запрос с id существующим в бд, то в ответе вернется статус 200 и корректное тело ")
    public void getOrderSuccessTest() {
        GetStoreOrderIdApi getStoreOrderIdApi = new GetStoreOrderIdApi();
        ValidatableResponse response = getStoreOrderIdApi.get("10")
                .statusCode(200);

        StoreResponse responseBody = response.extract().body().as(StoreResponse.class);

        StoreResponse expectedBody = StoreResponse.builder()
                .id(1)
                .petId(1)
                .quantity(1)
                .status("Reserved")
                .complete(true)
                .build();
        assertThat(responseBody)
                .as("тело не соответствует ожиданиям")
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("shipDate")
                        .build())
                .isEqualTo(expectedBody);
        assertThat(responseBody)
                .satisfies(storeResponse -> {
                    assertThat(storeResponse.getShipDate())
                            .as("shipDate не должен быть пустым")
                            .isNotNull();
                    assertThat(LocalDateTime.parse(storeResponse.getShipDate().replaceAll("\\+", "")))
                            .isCloseTo(LocalDateTime.now(), within(3, SECONDS));
                });
    }

    @Test
    @DisplayName("Если вызвать запрос с id не существующим в бд, то в ответе вернется статус 404 и корректное тело ")
    public void notFoundIdTest() {
        GetStoreOrderIdApi getStoreOrderIdApi = new GetStoreOrderIdApi();
        ValidatableResponse response = getStoreOrderIdApi.get("10")
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
