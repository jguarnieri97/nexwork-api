package ar.edu.unlam.tpi.nexwork_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data 
@Builder
public class WorkContractCreateRequest {

    @NotNull
    private Double price;

    @NotNull
    private String dateFrom;

    @NotNull
    private String dateTo;

    @NotNull
    private String detail;

    @NotNull
    private Long supplierId;

    @NotNull
    private Long applicantId;

    @NotNull
    @Size(min = 1)
    private List<Long> workers;
}