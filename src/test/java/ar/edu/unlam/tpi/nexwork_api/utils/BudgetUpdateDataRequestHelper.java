package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetUpdateDataRequestDto;

public class BudgetUpdateDataRequestHelper {
    public static BudgetUpdateDataRequestDto buildBudgetDataRequestDto(){
        return BudgetUpdateDataRequestDto.builder()
                .price(150000.0f)
                .daysCount(30)
                .workerCount(2)
                .detail("Updated work resume")
                .build();
    }
}
