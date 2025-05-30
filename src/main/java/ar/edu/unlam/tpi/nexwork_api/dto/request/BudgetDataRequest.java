package ar.edu.unlam.tpi.nexwork_api.dto.request;

import lombok.Data;

@Data
public class BudgetDataRequest {
    private Long supplierId;
    private String supplierName;
}
