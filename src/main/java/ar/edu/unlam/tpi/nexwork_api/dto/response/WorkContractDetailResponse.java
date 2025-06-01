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
    private List<AccountDetailResponse> suppliers;
    private List<AccountDetailResponse> applicants;
    private List<Long> workers;
    private List<String> files;
}