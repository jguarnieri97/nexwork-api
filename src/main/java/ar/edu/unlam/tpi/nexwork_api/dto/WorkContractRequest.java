package ar.edu.unlam.tpi.nexwork_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkContractRequest {
    @NotNull
    private Long id;
    
    @NotNull
    private String accountType;
    
    private Boolean limit;
}
