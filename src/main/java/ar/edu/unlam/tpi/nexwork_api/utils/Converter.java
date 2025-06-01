package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.response.AccountDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.exceptions.ConverterException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@UtilityClass
public class Converter {

    public String convertToString(Object object) {
        try {
            var mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error al convertir el objeto a String: {}", e.getMessage());
            throw new ConverterException(e.getMessage());
        }
    }

    public static BudgetDetailResponse toBudgetDetailResponse(BudgetResponseDetail budgetResponseDetail, List<AccountDetailResponse> accountDetailResponse) {
        return BudgetDetailResponse.builder()
                .id(budgetResponseDetail.getId())
                .budgetNumber(budgetResponseDetail.getBudgetNumber())
                .isRead(budgetResponseDetail.getIsRead())
                .applicants(accountDetailResponse)
                .createdAt(budgetResponseDetail.getCreatedAt())
                .category(budgetResponseDetail.getCategory())
                .state(budgetResponseDetail.getState())
                .files(budgetResponseDetail.getFiles())
                .detail(budgetResponseDetail.getDetail())
                .budgets(budgetResponseDetail.getBudgets())
                .build();
    }

}
