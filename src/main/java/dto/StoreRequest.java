package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequest {

    private Integer id;
    private Integer petId;
    private Integer quantity;
    private String shipDate;
    private String status;
    private Boolean complete;

}
