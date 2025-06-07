package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;

import java.util.List;

public class TestUtils {


    public static final String BUDGET_ID = "ABC";
    public static final long APPLICANT_ID = 1L;
    public static final long SUPPLIER_ID = 1L;
    public static final String DATE = "2023-10-01";

    public static BudgetResponse buildBudgetsResponse() {
        return BudgetResponse.builder()
                .id(BUDGET_ID)
                .applicantId(APPLICANT_ID)
                .date(DATE)
                .build();
    }

   /* public static BudgetResponseDetail buildBudgetResponseDetail() {
        return BudgetResponseDetail.builder()
                .applicantId(APPLICANT_ID)
                .id(BUDGET_ID)
                .createdAt(DATE)
                .budgets(List.of())
                .build();
    }*/

    public static BudgetRequest buildBudgetRequest() {
        return BudgetRequest.builder()
                .applicantId(APPLICANT_ID)
                .workResume("WORK RESUME")
                .workDetail("WORK DETAIL")
                .files(List.of())
                .build();
    }
}
