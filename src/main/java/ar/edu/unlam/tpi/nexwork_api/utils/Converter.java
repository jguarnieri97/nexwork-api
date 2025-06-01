package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.*;
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
                .applicant(accountDetailResponse)
                .createdAt(budgetResponseDetail.getCreatedAt())
                .category(budgetResponseDetail.getCategory())
                .state(budgetResponseDetail.getState())
                .files(budgetResponseDetail.getFiles())
                .detail(budgetResponseDetail.getDetail())
                .budgets(budgetResponseDetail.getBudgets())
                .build();
    }

    public static AccountDetailRequest toAccountRequest(Long contract, String supplierAccount) {
        return AccountDetailRequest.builder()
                .userId(contract)
                .type(supplierAccount)
                .build();
    }

    public static WorkContractDetailResponse toWorkContractDetailResponse(
            WorkContractResponse contract,
            List<AccountDetailResponse> suppliers,
            List<AccountDetailResponse> applicants) {

        return WorkContractDetailResponse.builder()
                .id(contract.getId())
                .codeNumber(contract.getCodeNumber())
                .price(contract.getPrice())
                .dateFrom(contract.getDateFrom())
                .dateTo(contract.getDateTo())
                .state(contract.getState())
                .detail(contract.getDetail())
                .suppliers(suppliers)
                .applicants(applicants)
                .workers(contract.getWorkers())
                .files(contract.getFiles())
                .build();
    }

}
