package ar.edu.unlam.tpi.nexwork_api.controller.impl;

import ar.edu.unlam.tpi.nexwork_api.controller.BudgetsController;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.nexwork_api.exceptions.ValidatorException;
import ar.edu.unlam.tpi.nexwork_api.service.BudgetsService;
import ar.edu.unlam.tpi.nexwork_api.utils.BudgetsDataHelper;
import ar.edu.unlam.tpi.nexwork_api.utils.Constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BudgetsControllerImplTest {
/* 
    private BudgetsService budgetsService;
    private BudgetsController budgetsController;

    @BeforeEach
    void setUp() {
        budgetsService = mock(BudgetsService.class);
        budgetsController = new BudgetsControllerImpl(budgetsService);
    }

    @Test
    void getBudgetsReturnsGenericResponseWithBudgetsWhenBudgetsExist() {
        var budgets = BudgetsDataHelper.buildBudgetList();

        when(budgetsService.getBudgets(1L, null)).thenReturn(budgets);

        var response = budgetsController.getBudgets(1L, null);

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertEquals(2, response.getData().size());
        verify(budgetsService).getBudgets(1L, null);
    }

    @Test
    void getBudgetsThrowsValidatorExceptionWhenIdIsNull() {
        assertThrows(ValidatorException.class, () -> budgetsController.getBudgets(null, null));
    }

    @Test
    void getBudgetDetailByIdReturnsGenericResponseWhenFound() {
        var budgetDetail = BudgetsDataHelper.buildBudgetResponseDetail("budget123");

        when(budgetsService.getBudget("budget123")).thenReturn(budgetDetail);

        GenericResponse<BudgetResponseDetail> response = budgetsController.getBudgetDetail("budget123");

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals("budget123", response.getData().getId());
        verify(budgetsService).getBudget("budget123");
    }*/
}