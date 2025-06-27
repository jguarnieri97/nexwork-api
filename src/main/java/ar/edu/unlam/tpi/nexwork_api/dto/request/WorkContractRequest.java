package ar.edu.unlam.tpi.nexwork_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkContractRequest {
    @NotNull(message = "ID cannot be null")
    private Long id;
    
    @NotNull(message = "Account type cannot be null")
    @NotBlank(message = "Account type cannot be blank")
    private String accountType;
    
    private Boolean limit;
}
