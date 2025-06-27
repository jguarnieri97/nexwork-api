package ar.edu.unlam.tpi.nexwork_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetDataRequest {
    @NotNull(message = "Supplier ID cannot be null")
    private Long supplierId;

    private String supplierName;
}
