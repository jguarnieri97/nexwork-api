package ar.edu.unlam.tpi.nexwork_api.controller.impl;

import ar.edu.unlam.tpi.nexwork_api.controller.BudgetsController;
import ar.edu.unlam.tpi.nexwork_api.dto.request.BudgetRequest;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponse;
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
    public GenericResponse<List<BudgetResponse>> getBudgets(Long applicantId, Long supplierId) {
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
        var budget = budgetsService.getBudget(id);
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
}
