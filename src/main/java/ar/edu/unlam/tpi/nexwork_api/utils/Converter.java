package ar.edu.unlam.tpi.nexwork_api.utils;

import ar.edu.unlam.tpi.nexwork_api.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.EmailCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.NotificationCreateRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.*;
import ar.edu.unlam.tpi.nexwork_api.exceptions.ConverterException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .files(contract.getFiles())
                .tasks(contract.getTasks())
                .build();
    }

    public NotificationCreateRequest buildNotification(
            Long userId,
            String userType,
            boolean inMail,
            String content,
            NotificationType type,
            String subject,
            Map<String, String> templateVariables
    ) {
        EmailCreateRequest email = EmailCreateRequest.builder()
                .type(type.name())
                .subject(subject)
                .templateVariables(templateVariables)
                .build();

        return NotificationCreateRequest.builder()
                .userId(userId)
                .userType(userType)
                .inMail(inMail)
                .content(content)
                .emailCreateRequest(email)
                .build();
    }

    public static Map<String, String> buildTemplateVariables(
            NotificationType type,
            String supplierName,
            String applicantName,
            String resume,
            String detail,
            String precio,
            String inicio,
            String finalizacion,
            String url
    ) {
        Map<String, String> vars = new HashMap<>();
        switch (type) {
            case BUDGET:
                vars.put("supplierName", supplierName);
                vars.put("applicantName", applicantName);
                vars.put("resume", resume);
                vars.put("detail", detail);
                vars.put("url", url);
                break;
            case CONTRACT_EMAIL:
                vars.put("supplierName", supplierName);
                vars.put("applicantName", applicantName);
                vars.put("precio", precio);
                vars.put("inicio", inicio);
                vars.put("finalizacion", finalizacion);
                vars.put("url", url);
                break;
            case CONTRACT_FINALIZED_APPLICANT:
                vars.put("applicantName", applicantName);
                vars.put("supplierName", supplierName);
                vars.put("detail", detail);
                vars.put("url", url);
                break;
            case CONTRACT_FINALIZED_SUPPLIER:
                vars.put("supplierName", supplierName);
                vars.put("detail", detail);
                vars.put("url", url);
                break;
        }
        return vars;
    }

}
