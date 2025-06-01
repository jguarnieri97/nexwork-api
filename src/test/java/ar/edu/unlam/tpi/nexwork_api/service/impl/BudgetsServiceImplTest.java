package ar.edu.unlam.tpi.nexwork_api.service.impl;

import ar.edu.unlam.tpi.nexwork_api.client.AccountsClient;
import ar.edu.unlam.tpi.nexwork_api.client.BudgetsClient;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetDetailResponse;
import ar.edu.unlam.tpi.nexwork_api.dto.response.BudgetResponseDetail;
import ar.edu.unlam.tpi.nexwork_api.dto.response.UserResponse;
import ar.edu.unlam.tpi.nexwork_api.utils.AccountDataHelper;
import ar.edu.unlam.tpi.nexwork_api.utils.BudgetDataHelper;
import ar.edu.unlam.tpi.nexwork_api.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static ar.edu.unlam.tpi.nexwork_api.utils.TestUtils.APPLICANT_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetsServiceImplTest {

    @Mock
    private BudgetsClient budgetsClient;

    @Mock
    private AccountsClient accountsClient;

    @InjectMocks
    private BudgetsServiceImpl budgetsService;

    @Test
    void givenApplicantIdWhenGetBudgetsThenReturnBudgetsList() {
        var mockBudgets = List.of(TestUtils.buildBudgetsResponse());
        when(budgetsClient.getApplicantBudgets(APPLICANT_ID)).thenReturn(mockBudgets);

        var result = budgetsService.getBudgets(APPLICANT_ID, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(budgetsClient).getApplicantBudgets(APPLICANT_ID);
    }

    @Test
    void givenNoBudgetsWhenGetBudgetsThenReturnEmptyList() {
        when(budgetsClient.getApplicantBudgets(APPLICANT_ID)).thenReturn(List.of());

        var result = budgetsService.getBudgets(APPLICANT_ID, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(budgetsClient).getApplicantBudgets(APPLICANT_ID);
    }

    @Test
    void givenValidIdWhenGetBudgetThenReturnBudgetDetailResponse() {
        // Given
        String budgetId = "123";
        BudgetResponseDetail mockBudget = BudgetDataHelper.createBudgetResponseDetail(budgetId);
        UserResponse mockUserResponse = AccountDataHelper.createUserResponse();

        when(budgetsClient.getBudgetDetail(budgetId)).thenReturn(mockBudget);
        when(accountsClient.getAccountById(anyList())).thenReturn(mockUserResponse);

        // When
        BudgetDetailResponse result = budgetsService.getBudget(budgetId);

        // Then
        assertNotNull(result);
        verify(budgetsClient).getBudgetDetail(budgetId);
        verify(accountsClient).getAccountById(anyList());
    }

    @Test
    void givenBudgetRequestWhenCreateBudgetThenCallClient() {
        var budgetRequest = TestUtils.buildBudgetRequest();

        budgetsService.createBudget(budgetRequest);

        verify(budgetsClient).createBudget(budgetRequest);
    }

}