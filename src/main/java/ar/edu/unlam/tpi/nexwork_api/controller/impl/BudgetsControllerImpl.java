package ar.edu.unlam.tpi.nexwork_api.controller.impl;

import ar.edu.unlam.tpi.nexwork_api.controller.BudgetsController;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetFinalizeRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRejectedRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetUpdateDataRequestDto;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.service.BudgetsService;
import ar.edu.unlam.tpi.nexwork_api.utils.Constants;
import ar.edu.unlam.tpi.nexwork_api.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BudgetsControllerImpl implements BudgetsController {

    private final BudgetsService budgetsService;

    @Override
    public GenericResponse<List<Object>> getBudgets(Long applicantId, Long supplierId) {
        Validator.validateBudgetsUserIdNotNull(applicantId, supplierId);
        var budgets = budgetsService.getBudgets(applicantId, supplierId);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                budgets
        );
    }

    @Override
    public GenericResponse<BudgetResponseDetail> getBudgetDetail(String id) {
        BudgetResponseDetail budget = budgetsService.getBudget(id);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                budget);
    }

    @Override
    public GenericResponse<Void> createBudget(BudgetRequest budgetRequest) {
        budgetsService.createBudget(budgetRequest);
        return new GenericResponse<>(
                Constants.STATUS_CREATED,
                Constants.CREATED_MESSAGE,
                null);
    }

    @Override
    public GenericResponse<Void> finalizeBudget(String id, BudgetFinalizeRequest budgetFinalizeRequest) {
        budgetsService.finalizeBudget(id, budgetFinalizeRequest);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                null);
    }

    @Override
    public GenericResponse<Void> updateBudget(String budgetId, Long supplierId, BudgetUpdateDataRequestDto request) {
        budgetsService.updateBudget(budgetId,supplierId,request);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.UPDATED_MESSAGE,
                null);
    }

    @Override
    public GenericResponse<Void> rejectBudget(Long id, BudgetRejectedRequest budgetRejectedRequest) {
        budgetsService.rejectBudget(id, budgetRejectedRequest);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                null);
    }

}
