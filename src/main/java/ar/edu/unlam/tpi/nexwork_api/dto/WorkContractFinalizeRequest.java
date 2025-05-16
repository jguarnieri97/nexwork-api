package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkContractFinalizeRequest {
    private String detail;
    private List<String> files;

    // Este campo se setea automáticamente en el controller o service
    private String state;
}
