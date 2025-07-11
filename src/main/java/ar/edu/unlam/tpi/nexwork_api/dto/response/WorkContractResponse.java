package ar.edu.unlam.tpi.nexwork_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkContractResponse {
    private Long id;
    private String codeNumber;
    private Double price;
    private String dateFrom;
    private String dateTo;
    private String state;
    private String detail;
    private Long supplierId;
    private String supplierName;
    private Long applicantId;
    private String applicantName;
}
