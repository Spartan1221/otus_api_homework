package store.post;

import dto.StoreErrorResponse;
import dto.StoreRequest;
import dto.StoreResponse;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.PostStoreOrderApi;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class PostStoreOrderTests {

    @Test
    @DisplayName("Если вызвать запрос с заполненным телом, то в ответе вернется статус 200 и корректное тело ")
    public void createOrderSuccessTest() {
        PostStoreOrderApi postStoreOrderApi = new PostStoreOrderApi();
        String shipDate = LocalDateTime.now().plusDays(10).toString();
        StoreRequest storeRequest = StoreRequest.builder()
                .id(1)
                .petId(1)
                .quantity(1)
                .shipDate(shipDate)
                .status("Reserved")
                .complete(true)
                .build();

        ValidatableResponse response = postStoreOrderApi.post(storeRequest)
                .statusCode(200);
        StoreResponse responseBody = response.extract().body().as(StoreResponse.class);

        StoreResponse expectedBody = StoreResponse.builder()
                .id(1)
                .petId(1)
                .quantity(1)
                .shipDate(shipDate)
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
                            .isCloseTo(shipDate, within(3, SECONDS));
                });
    }
}
