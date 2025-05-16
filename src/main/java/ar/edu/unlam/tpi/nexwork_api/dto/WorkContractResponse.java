package ar.edu.unlam.tpi.nexwork_api.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkContractResponse {
    private Long id;
    private Double price;
    private String dateFrom;
    private String dateTo;
    private String state;
    private String detail;
    private Long supplierId;
    private Long applicantId;
    private List<Long> workers;
    private List<String> files;
}
