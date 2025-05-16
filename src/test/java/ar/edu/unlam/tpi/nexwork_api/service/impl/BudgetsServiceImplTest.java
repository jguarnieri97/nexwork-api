package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.BudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ar.edu.unlam.tpi.nexwork_api.utils.TestUtils.APPLICANT_ID;
import static ar.edu.unlam.tpi.nexwork_api.utils.TestUtils.BUDGET_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BudgetsServiceImplTest {

    private BudgetsClient budgetsClient;
    private BudgetsServiceImpl budgetsService;


    @BeforeEach
    void setUp() {
        budgetsClient = mock(BudgetsClient.class);
        budgetsService = new BudgetsServiceImpl(budgetsClient);
    }

    @Test
    void getBudgetsReturnsListWhenBudgetsExist() {
        var mockBudgets = List.of(TestUtils.buildBudgetsResponse());
        when(budgetsClient.getApplicantBudgets(APPLICANT_ID)).thenReturn(mockBudgets);

        var result = budgetsService.getBudgets(APPLICANT_ID, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(budgetsClient).getApplicantBudgets(APPLICANT_ID);
    }

    @Test
    void getBudgetsReturnsEmptyListWhenNoBudgetsExist() {
        when(budgetsClient.getApplicantBudgets(APPLICANT_ID)).thenReturn(List.of());

        var result = budgetsService.getBudgets(APPLICANT_ID, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(budgetsClient).getApplicantBudgets(APPLICANT_ID);
    }

    @Test
    void getBudgetsThrowsExceptionWhenClientFails() {
        when(budgetsClient.getApplicantBudgets(APPLICANT_ID)).thenThrow(new RuntimeException("Client error"));

        assertThrows(RuntimeException.class, () -> budgetsService.getBudgets(APPLICANT_ID, null));
        verify(budgetsClient).getApplicantBudgets(APPLICANT_ID);
    }

    @Test
    void getBudgetReturnsBudgetDetailWhenBudgetExists() {
        var mockBudgetDetail = TestUtils.buildBudgetResponseDetail();
        when(budgetsClient.getBudgetDetail(BUDGET_ID)).thenReturn(mockBudgetDetail);

        var result = budgetsService.getBudget(BUDGET_ID);

        assertNotNull(result);
        assertEquals(mockBudgetDetail, result);
        verify(budgetsClient).getBudgetDetail(BUDGET_ID);
    }

    @Test
    void getBudgetThrowsExceptionWhenClientFails() {
        when(budgetsClient.getBudgetDetail(BUDGET_ID)).thenThrow(new RuntimeException("Client error"));

        assertThrows(RuntimeException.class, () -> budgetsService.getBudget(BUDGET_ID));
        verify(budgetsClient).getBudgetDetail(BUDGET_ID);
    }

    @Test
    void createBudgetCallsClientSuccessfully() {
        var budgetRequest = TestUtils.buildBudgetRequest();

        budgetsService.createBudget(budgetRequest);

        verify(budgetsClient).createBudget(budgetRequest);
    }

    @Test
    void createBudgetThrowsExceptionWhenClientFails() {
        var budgetRequest = TestUtils.buildBudgetRequest();
        doThrow(new RuntimeException("Client error")).when(budgetsClient).createBudget(budgetRequest);

        assertThrows(RuntimeException.class, () -> budgetsService.createBudget(budgetRequest));
        verify(budgetsClient).createBudget(budgetRequest);
    }
}