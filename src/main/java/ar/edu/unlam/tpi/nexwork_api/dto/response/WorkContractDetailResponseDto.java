package ar.edu.unlam.tpi.nexwork_api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WorkContractDetailResponseDto {
    private Long id;
    private String codeNumber;
    private Double price;
    private String dateFrom;
    private String dateTo;
    private String state;
    private String detail;
    private AccountDetailResponse supplier;
    private AccountDetailResponse applicant;
    private List<AccountDetailResponse> workers;
    private List<String> files;
}