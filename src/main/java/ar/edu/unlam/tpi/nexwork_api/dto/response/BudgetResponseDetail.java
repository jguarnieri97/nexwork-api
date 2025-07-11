package ar.edu.unlam.tpi.nexwork_api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import ar.edu.unlam.tpi.nexwork_api.dto.BudgetData;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetDetail;

@Data
@Builder
public class BudgetResponseDetail {

    private String id;
    private String budgetNumber;
    private Boolean isRead;
    private AccountDetailResponse applicant;
    private String category;
    private String state;
    private String createdAt;
    private List<String> files;
    private BudgetDetail detail;
    private List<BudgetData> budgets;

}
