package ar.edu.unlam.tpi.nexwork_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetRejectedRequest {

    @NotNull
    private Long supplierId;
}
