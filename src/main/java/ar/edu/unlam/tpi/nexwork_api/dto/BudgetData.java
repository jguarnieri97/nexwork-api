package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetData {
    private Long supplierId;
    private String supplierName;
    private Double price;
    private Integer daysCount;
    private Integer workerCount;
    private String detail;
    private String state;
    private Boolean hired;
}
