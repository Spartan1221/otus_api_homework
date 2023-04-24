package store.post;

import dto.StoreErrorResponse;
import dto.StoreRequest;
import dto.StoreResponse;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.DeleteStoreOrderApi;
import services.GetStoreOrderIdApi;
import services.PostStoreOrderApi;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class GetStoreOrderTests {

    private static final Integer ID = 10;

    @AfterAll
    public static void afterAll(){
        DeleteStoreOrderApi deleteStoreOrderApi = new DeleteStoreOrderApi();
        deleteStoreOrderApi.delete(ID.toString());
    }

    @Test
    @DisplayName("Если вызвать запрос с id существующим в бд, то в ответе вернется статус 200 и корректное тело ")
    public void getOrderSuccessTest() {
        PostStoreOrderApi postStoreOrderApi = new PostStoreOrderApi();
        String shipDate = LocalDateTime.now().plusDays(10).toString();
        StoreRequest storeRequest = StoreRequest.builder()
                .id(ID)
                .petId(1)
                .quantity(1)
                .shipDate(shipDate)
                .status("Reserved")
                .complete(true)
                .build();
        postStoreOrderApi.post(storeRequest)
                .statusCode(200);

        GetStoreOrderIdApi getStoreOrderIdApi = new GetStoreOrderIdApi();
        ValidatableResponse response = getStoreOrderIdApi.get(ID.toString())
                .statusCode(200);

        StoreResponse responseBody = response.extract().body().as(StoreResponse.class);

        StoreResponse expectedBody = StoreResponse.builder()
                .id(ID)
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
                });
    }
}
