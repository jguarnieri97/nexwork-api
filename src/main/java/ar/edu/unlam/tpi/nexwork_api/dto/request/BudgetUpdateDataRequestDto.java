package ar.edu.unlam.tpi.nexwork_api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetUpdateDataRequestDto {
    private Float price;
    private Integer daysCount;
    private Integer workerCount;
    private String detail;
}