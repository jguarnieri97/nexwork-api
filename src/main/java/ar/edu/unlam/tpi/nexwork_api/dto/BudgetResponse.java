package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetResponse {

    private Long id;
    private Long applicantId;
    private String date;

}
