package ar.edu.unlam.tpi.nexwork_api.dto.response;

import lombok.Builder;
import lombok.Data;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetData;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetDetail;

import java.util.List;

@Data
@Builder
public class BudgetDetailFromBudgetsClient {
    private String id;
    private String budgetNumber;
    private Boolean isRead;
    private Long applicantId;
    private String applicantName;
    private String category;
    private String state;
    private String createdAt;
    private List<String> files;
    private BudgetDetail detail;
    private List<BudgetData> budgets;
}
