package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WorkContractFinalizeRequest {
 
    private String detail;
    private List<String> files;
    private String state;
}
