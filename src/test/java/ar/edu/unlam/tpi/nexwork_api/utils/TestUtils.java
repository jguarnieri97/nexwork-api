package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;

public class TestUtils {


    public static final long BUDGET_ID = 1L;
    public static final long APPLICANT_ID = 1L;
    public static final String DATE = "2023-10-01";

    public static BudgetResponse buildBudgetsResponse() {
        return BudgetResponse.builder()
                .id(BUDGET_ID)
                .applicantId(APPLICANT_ID)
                .date(DATE)
                .build();
    }

}
