package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.client.BudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.request.AccountDetailRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.nexwork_api.dto.response.*;
import ar.edu.unlam.tpi.nexwork_api.exceptions.BudgetsClientException;
import ar.edu.unlam.tpi.nexwork_api.service.BudgetsService;
import ar.edu.unlam.tpi.nexwork_api.utils.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetsServiceImpl implements BudgetsService {

    private final BudgetsClient budgetsClient;
    private final AccountsClient accountsClient;

    @Override
public List<Object> getBudgets(Long applicantId, Long supplierId) {
    if (Objects.nonNull(applicantId)) {
        log.info("Obteniendo presupuestos para el solicitante: {}", applicantId);
        return new ArrayList<>(budgetsClient.getApplicantBudgets(applicantId));
    } else if (Objects.nonNull(supplierId)) {
        log.info("Obteniendo presupuestos para el proveedor: {}", supplierId);
        return new ArrayList<>(budgetsClient.getSupplierBudgets(supplierId));
    } else {
        throw new IllegalArgumentException("Debe proporcionar applicantId o supplierId");
    }
}


    @Override
    public BudgetResponseDetail getBudget(String id) {
        log.info("Obteniendo presupuesto con id: {}", id);

        BudgetResponseDetail budget = budgetsClient.getBudgetDetail(id);

        List<AccountDetailRequest> requests = Collections.singletonList(
                AccountDetailRequest.builder()
                        .userId(budget.getApplicantId())
                        .type("applicant")
                        .build()
        );

        UserResponse accountDetail = accountsClient.getAccountById(requests);

        log.info("Presupuesto obtenido: {}", Converter.convertToString(budget));

            return Converter.toBudgetResponseDetail(budget, accountDetail.getApplicants());
    }

    @Override
    public void createBudget(BudgetRequest budgetRequest) {
        log.info("Request recibido: {}", Converter.convertToString(budgetRequest));

        budgetsClient.createBudget(budgetRequest);

        log.info("Presupuesto creado con éxito");
    }

 

    @Override
    public void finalizeBudget(String id, BudgetFinalizeRequest request) {
        log.info("Finalizando presupuesto con id {} - detalle: {}", id, request.getSupplierHired());


        try {
            budgetsClient.finalizeBudget(id, request);
            log.info("Presupuesto finalizado con éxito");
        } catch (Exception ex) {
            log.error("Error al finalizar presupuesto    con id {}: {}", id, ex.getMessage(), ex);
            throw new BudgetsClientException(
                    ErrorResponse.builder()
                            .code(500)
                            .message("BUDGET_FINALIZATION_ERROR")
                            .detail("Error al finalizar presupuesto con ID: " + id)
                            .build()
            );
        }
    }
    @Override
    public void updateBudget(String budgetId, Long supplierId, BudgetUpdateDataRequestDto budgetRequest) {
        log.info("Actualizando presupuesto con id: {} para el proveedor: {}", budgetId, supplierId);

        budgetsClient.updateBudget(budgetId, supplierId, budgetRequest);

        log.info("Presupuesto actualizado con éxito");
    }
    
}
