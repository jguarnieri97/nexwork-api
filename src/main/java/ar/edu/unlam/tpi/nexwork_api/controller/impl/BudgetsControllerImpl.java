package ar.edu.unlam.tpi.nexwork_api.controller.impl;

import ar.edu.unlam.tpi.nexwork_api.controller.BudgetsController;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.service.BudgetsService;
import ar.edu.unlam.tpi.nexwork_api.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BudgetsControllerImpl implements BudgetsController {

    private final BudgetsService budgetsService;

    @Override
    public GenericResponse<List<BudgetResponse>> getBudgets(Long applicantId) {
        var budgets = budgetsService.getBudgets(applicantId);
        return new GenericResponse<>(
                Constants.STATUS_OK,
                Constants.SUCCESS_MESSAGE,
                budgets
        );
    }

    @Override
    public GenericResponse<BudgetResponseDetail> getBudgetDetail(Long id) {
        return null;
    }
}
