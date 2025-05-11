package ar.edu.unlam.tpi.nexwork_api.controller.impl;

import ar.edu.unlam.tpi.nexwork_api.controller.BudgetsController;
import ar.edu.unlam.tpi.nexwork_api.service.BudgetsService;
import ar.edu.unlam.tpi.nexwork_api.utils.Constants;
import ar.edu.unlam.tpi.nexwork_api.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ar.edu.unlam.tpi.nexwork_api.utils.TestUtils.APPLICANT_ID;
import static ar.edu.unlam.tpi.nexwork_api.utils.TestUtils.BUDGET_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BudgetsControllerImplTest {

    private BudgetsService budgetsService;

    private BudgetsController budgetsController;

    @BeforeEach
    void setUp() {
        budgetsService = mock(BudgetsService.class);
        budgetsController = new BudgetsControllerImpl(budgetsService);
    }

    @Test
    void getBudgetsReturnsGenericResponseWithBudgetsWhenBudgetsExist() {
        var mockBudgets = List.of(TestUtils.buildBudgetsResponse());
        when(budgetsService.getBudgets(APPLICANT_ID)).thenReturn(mockBudgets);

        var response = budgetsController.getBudgets(APPLICANT_ID);

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertEquals(1, response.getData().size());
        verify(budgetsService).getBudgets(APPLICANT_ID);
    }

    @Test
    void getBudgetsReturnsGenericResponseWithEmptyListWhenNoBudgetsExist() {
        when(budgetsService.getBudgets(APPLICANT_ID)).thenReturn(List.of());

        var response = budgetsController.getBudgets(APPLICANT_ID);

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertTrue(response.getData().isEmpty());
        verify(budgetsService).getBudgets(APPLICANT_ID);
    }

    @Test
    void getBudgetDetailReturnsGenericResponseWithBudgetDetailWhenBudgetExists() {
        var mockBudgetDetail = TestUtils.buildBudgetResponseDetail();
        when(budgetsService.getBudget(BUDGET_ID)).thenReturn(mockBudgetDetail);

        var response = budgetsController.getBudgetDetail(BUDGET_ID);

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertNotNull(response.getData());
        verify(budgetsService).getBudget(BUDGET_ID);
    }

    @Test
    void createBudgetReturnsGenericResponseWithCreatedStatus() {
        var budgetRequest = TestUtils.buildBudgetRequest();

        var response = budgetsController.createBudget(budgetRequest);

        assertNotNull(response);
        assertEquals(Constants.STATUS_CREATED, response.getCode());
        assertEquals(Constants.CREATED_MESSAGE, response.getMessage());
        assertNull(response.getData());
        verify(budgetsService).createBudget(budgetRequest);
    }
}