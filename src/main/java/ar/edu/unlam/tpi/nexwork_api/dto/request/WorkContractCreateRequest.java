package ar.edu.unlam.tpi.nexwork_api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data 
@Builder
public class WorkContractCreateRequest {

    @NotNull(message = "Price cannot be null")
    private Double price;

    @NotNull(message = "dateFrom cannot be null")
    private String dateFrom;

    @NotNull(message = "dateTo cannot be null")
    private String dateTo;

    private String detail;

    @NotNull(message = "Supplier ID cannot be null")
    private Long supplierId;

    @NotNull(message = "Applicant ID cannot be null")
    private Long applicantId;

    @NotNull(message = "Workers list cannot be null")
    @Size(min = 1, message = "At least one worker must be assigned")
    private List<Long> workers;

    @NotNull(message = "Budget ID cannot be null")
    private String budgetId;
}