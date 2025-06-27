package ar.edu.unlam.tpi.nexwork_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
public class BudgetUpdateDataRequestDto {
    @NotNull(message = "Price cannot be null")
    private Float price;

    @NotNull(message = "Days count cannot be null")
    @Range(min = 1, message = "Days count must be at least 1")
    private Integer daysCount;

    @NotNull(message = "Worker count cannot be null")
    @Range(min = 1, message = "Worker count must be at least 1")
    private Integer workerCount;

    private String detail;
}