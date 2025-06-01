package ar.edu.unlam.tpi.nexwork_api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetResponse {

    private String id;
    private String budgetNumber;
    private Boolean isRead;
    private Long applicantId;
    private String applicantName;
    private String category;
    private String state;
    private String date;

}
