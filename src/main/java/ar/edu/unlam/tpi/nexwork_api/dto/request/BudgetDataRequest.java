package ar.edu.unlam.tpi.nexwork_api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetDataRequest {
    private Long supplierId;
    private String supplierName;
}
