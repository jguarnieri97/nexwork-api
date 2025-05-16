package ar.edu.unlam.tpi.nexwork_api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BudgetResponseDetail {

    private String id;
    private Long applicantId;
    private String createdAt;
    private List<String> files;
    private BudgetDetail detail;
    private List<BudgetData> budgets;

}
