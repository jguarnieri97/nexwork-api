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

    public static BudgetResponseDetail toBudgetResponseDetail(
            BudgetDetailFromBudgetsClient raw,
            List<AccountDetailResponse> applicants) {
        return BudgetResponseDetail.builder()
                .id(raw.getId())
                .budgetNumber(raw.getBudgetNumber())
                .isRead(raw.getIsRead())
                .applicant(applicants.get(0)) // porque solo hay 1
                .category(raw.getCategory())
                .state(raw.getState())
                .createdAt(raw.getCreatedAt())
                .files(raw.getFiles())
                .detail(raw.getDetail())
                .budgets(raw.getBudgets())
                .build();
    }

    public static AccountDetailRequest toAccountRequest(Long contract, String supplierAccount) {
        return AccountDetailRequest.builder()
                .userId(contract)
                .type(supplierAccount)
                .build();
    }

    public static WorkContractDetailResponseDto toWorkContractDetailResponseDto(
            WorkContractDetailResponse contract,
            AccountDetailResponse supplier,
            AccountDetailResponse applicant,
            List<AccountDetailResponse> workers) {

        return WorkContractDetailResponseDto.builder()
                .id(contract.getId())
                .codeNumber(contract.getCodeNumber())
                .price(contract.getPrice())
                .dateFrom(contract.getDateFrom())
                .dateTo(contract.getDateTo())
                .state(contract.getState())
                .detail(contract.getDetail())
                .supplier(supplier)
                .applicant(applicant)
                .workers(workers)
                .build();
    }

}
