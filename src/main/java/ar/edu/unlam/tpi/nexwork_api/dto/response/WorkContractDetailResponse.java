package ar.edu.unlam.tpi.nexwork_api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WorkContractDetailResponse {
    private Long id;
    private String codeNumber;
    private Double price;
    private String dateFrom;
    private String dateTo;
    private String state;
    private String detail;
    private Long supplierId;
    private Long applicantId;
    private String supplierName;
    private String applicantName;
    private List<Long> workers;
    private List<String> files;
    private List<TaskDto> tasks;
}
