package ar.edu.unlam.tpi.nexwork_api.dto.response;

import ar.edu.unlam.tpi.nexwork_api.dto.BudgetData;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetDetail;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetDetailResponse {

    private String id;
    private String budgetNumber;
    private Boolean isRead;
    private List<AccountDetailResponse> applicant;
    private String createdAt;
    private String category;
    private String state;
    private List<String> files;
    private BudgetDetail detail;
    private List<BudgetData> budgets;

}