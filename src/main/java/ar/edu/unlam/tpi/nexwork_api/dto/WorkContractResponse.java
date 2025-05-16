package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Data;

@Data
public class WorkContractResponse {
    private Long id;
    private Double price;
    private String dateFrom;
    private String dateTo;
    private String state;
    private String detail;
    private Long supplierId;
    private Long applicantId;
}
