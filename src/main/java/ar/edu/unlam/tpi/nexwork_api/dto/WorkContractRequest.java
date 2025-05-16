package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkContractRequest {
    private Long id;
    private String accountType;
    private Boolean limit;
}
