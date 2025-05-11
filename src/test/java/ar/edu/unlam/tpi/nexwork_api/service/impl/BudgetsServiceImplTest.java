package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.BudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.BudgetResponse;
import ar.edu.unlam.tpi.nexwork_api.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ar.edu.unlam.tpi.nexwork_api.utils.TestUtils.APPLICANT_ID;
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
        when(budgetsClient.getBudgets(APPLICANT_ID)).thenReturn(mockBudgets);

        List<BudgetResponse> result = budgetsService.getBudgets(APPLICANT_ID);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(budgetsClient).getBudgets(APPLICANT_ID);
    }

    @Test
    void getBudgetsReturnsEmptyListWhenNoBudgetsExist() {
        when(budgetsClient.getBudgets(APPLICANT_ID)).thenReturn(List.of());

        List<BudgetResponse> result = budgetsService.getBudgets(APPLICANT_ID);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(budgetsClient).getBudgets(APPLICANT_ID);
    }

    @Test
    void getBudgetsThrowsExceptionWhenClientFails() {
        when(budgetsClient.getBudgets(APPLICANT_ID)).thenThrow(new RuntimeException("Client error"));

        assertThrows(RuntimeException.class, () -> budgetsService.getBudgets(APPLICANT_ID));
        verify(budgetsClient).getBudgets(APPLICANT_ID);
    }
}